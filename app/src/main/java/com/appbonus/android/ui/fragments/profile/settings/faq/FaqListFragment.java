package com.appbonus.android.ui.fragments.profile.settings.faq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.appbonus.android.R;
import com.appbonus.android.api.Api;
import com.appbonus.android.api.ApiImpl;
import com.appbonus.android.loaders.FaqLoader;
import com.appbonus.android.model.Question;
import com.dolphin.helper.IntentHelper;
import com.dolphin.loader.AbstractLoader;
import com.dolphin.ui.fragment.BaseListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaqListFragment extends BaseListFragment<ListView, SimpleAdapter>
        implements LoaderManager.LoaderCallbacks<List<Question>>, AdapterView.OnItemClickListener, View.OnClickListener {
    public static final int LOADER_ID = 1;
    public static final String QUESTION_PARAMETER = "question";

    protected Api api;
    protected List<Question> questions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ApiImpl(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        View footer = inflater.inflate(R.layout.faq_list_footer, null);
        footer.setOnClickListener(this);
        listView.addFooterView(footer);
        return view;
    }

    @Override
    protected int layout() {
        return R.layout.faq_list_layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.faq);
        setDrawerIndicatorEnabled(false);
        if (getLoaderManager().getLoader(LOADER_ID) == null) {
            Loader loader = getLoaderManager().initLoader(LOADER_ID, null, this);
            loader.forceLoad();
        }
    }

    @Override
    public Loader<List<Question>> onCreateLoader(int id, Bundle args) {
        return new FaqLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Question>> loader, List<Question> data) {
        if (((AbstractLoader) loader).isSuccess()) {
            questions = data;
            setListAdapter(new SimpleAdapter(getActivity(), processData(data), R.layout.faq_question_item,
                    new String[]{QUESTION_PARAMETER}, new int[]{android.R.id.text1}));
        }
    }

    private List<? extends Map<String, ?>> processData(List<Question> data) {
        List<Map<String, String>> list = new ArrayList<>(data.size());
        for (Question question : data) {
            Map<String, String> map = new HashMap<>();
            map.put(QUESTION_PARAMETER, question.getText().trim());
            list.add(map);
        }
        return list;
    }

    @Override
    public void onLoaderReset(Loader<List<Question>> loader) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question question = questions.get(position);
        Bundle args = new Bundle();
        args.putSerializable(QUESTION_PARAMETER, question);
        placeProperFragment(FaqAnswerFragment.class.getName(), args);
    }

    @Override
    public void onClick(View v) {
        makeQuestion();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.faq_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_question:
                makeQuestion();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeQuestion() {
        startActivity(IntentHelper.sendTextEmail(null, getString(R.string.app_bonus_user_question),
                new String[]{getString(R.string.app_bonus_mail)}));
    }
}
