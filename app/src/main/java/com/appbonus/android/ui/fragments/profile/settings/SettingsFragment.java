package com.appbonus.android.ui.fragments.profile.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.appbonus.android.R;
import com.appbonus.android.api.Api;
import com.appbonus.android.api.ApiImpl;
import com.appbonus.android.component.DialogExceptionalAsyncTask;
import com.appbonus.android.model.api.DataWrapper;
import com.appbonus.android.push.GoogleCloudMessagingUtils;
import com.appbonus.android.storage.SharedPreferencesStorage;
import com.appbonus.android.ui.fragments.profile.settings.faq.FaqListFragment;
import com.appbonus.android.ui.login.LoginActivity;
import com.dolphin.activity.fragment.BaseFragment;
import com.dolphin.helper.IntentHelper;

public class SettingsFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    protected CheckBox showPush;
    protected CheckBox pushSound;

    protected Button webCab;
    protected Button faq;
    protected Button license;
    protected Button exit;

    protected TextView enterAs;

    protected Api api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ApiImpl(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, null);
        initUI(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.settings);
        setData(getActivity());
        setDrawerIndicatorEnabled(false);
    }

    private void setData(Context context) {
        showPush.setChecked(SharedPreferencesStorage.getPushShowing(context));
        pushSound.setChecked(SharedPreferencesStorage.getPushSound(context));

        enterAs.setText(SharedPreferencesStorage.getMail(context));

        showPush.setOnCheckedChangeListener(this);
        pushSound.setOnCheckedChangeListener(this);
    }

    private void initUI(View view) {
        showPush = (CheckBox) view.findViewById(R.id.show_push);
        pushSound = (CheckBox) view.findViewById(R.id.push_sound);

        webCab = (Button) view.findViewById(R.id.web_cab);
        webCab.setOnClickListener(this);

        faq = (Button) view.findViewById(R.id.faq);
        faq.setOnClickListener(this);

        license = (Button) view.findViewById(R.id.license);
        license.setOnClickListener(this);

        exit = (Button) view.findViewById(R.id.exit);
        exit.setOnClickListener(this);

        enterAs = (TextView) view.findViewById(R.id.enter_as);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.web_cab:
                startActivity(IntentHelper.openLink(getString(R.string.my_web_cab_link)));
                break;
            case R.id.faq:
                placeProperFragment(FaqListFragment.class.getName());
                break;
            case R.id.license:
                break;
            case R.id.exit:
                exit();
                break;
        }
    }

    private void exit() {
        new DialogExceptionalAsyncTask<Void, Void, DataWrapper>(getActivity()) {
            @Override
            protected FragmentManager getFragmentManager() {
                return getActivity().getSupportFragmentManager();
            }

            @Override
            protected DataWrapper background(Void... params) throws Throwable {
                return api.unregisterDevice(context, SharedPreferencesStorage.getToken(context),
                        GoogleCloudMessagingUtils.getRegistrationId(context));
            }

            @Override
            protected void onPostExecute(DataWrapper dataWrapper) {
                super.onPostExecute(dataWrapper);
                SharedPreferencesStorage.deleteToken(getActivity());
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        }.execute();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.show_push:
                SharedPreferencesStorage.savePushShowing(getActivity(), isChecked);
                break;
            case R.id.push_sound:
                SharedPreferencesStorage.savePushSound(getActivity(), isChecked);
                break;
        }
    }
}
