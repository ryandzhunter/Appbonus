package com.dolphin.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.dolphin.ui.SimpleActivity;
import com.google.android.gms.maps.SupportMapFragment;

public abstract class SimpleMapFragment extends SupportMapFragment implements StandardFragment {
    @Override
    public Fragment placeProperFragment(String fragmentTag) {
        return placeProperFragment(fragmentTag, null, true, this);
    }

    @Override
    public Fragment placeProperFragment(String fragmentTag, Bundle args, boolean addToBackStackCustom, Fragment targetFragment) {
        return ((SimpleActivity) getActivity()).placeProperFragment(fragmentTag, args, addToBackStackCustom, targetFragment, false);
    }

    @Override
    public boolean closeCurrentFragment() {
        if (getActivity() != null) {
            ((SimpleActivity) getActivity()).closeCurrentFragment(this);
        }
        return true;
    }

    @Override
    public Fragment placeProperFragment(String fragmentTag, Bundle args) {
        return placeProperFragment(fragmentTag, args, true, this);
    }

    public void setTitle(int resId) {
        if (getActivity() != null) {
            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().setTitle(resId);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                return closeCurrentFragment();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
