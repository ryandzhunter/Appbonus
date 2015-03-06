package com.appbonus.android.ui.fragments.friends;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appbonus.android.R;
import com.appbonus.android.model.User;
import com.appbonus.android.ui.fragments.profile.settings.faq.ReferralsInfoFragment;
import com.dolphin.ui.fragment.SimpleFragment;

public class MeetFriendsFragment extends SimpleFragment implements View.OnClickListener {
    protected View referralsInfo;
    protected View meet;
    protected View promoView;
    protected TextView promo;

    protected User user;

    protected MeetFriendsFragmentListener listener;

    public interface MeetFriendsFragmentListener {
        void sendInviteMessage();

        User getUser();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MeetFriendsFragmentListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = listener.getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meet_friends_layout, null);
        initUI(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.meet_friends);
        setDrawerIndicatorEnabled(false);
    }

    private void initUI(View view) {
        referralsInfo = view.findViewById(R.id.referrals_info);
        referralsInfo.setOnClickListener(this);
        meet = view.findViewById(R.id.meet);
        meet.setOnClickListener(this);
        promoView = view.findViewById(R.id.promo_view);
        if (!TextUtils.isEmpty(user.getInviteCode())) {
            promo = (TextView) promoView.findViewById(R.id.promo);
            promo.setText(user.getInviteCode());
        } else promoView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.referrals_info:
                placeProperFragment(ReferralsInfoFragment.class.getName());
                break;
            case R.id.meet:
                makeMeeting();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.meet_friends_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_meet_friends:
                makeMeeting();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeMeeting() {
        listener.sendInviteMessage();
    }
}
