/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dolphin.component.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dolphin.R;
import com.dolphin.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public abstract class SlidingTabsBasicFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    protected SlidingTabLayout mSlidingTabLayout;
    protected ViewPager mViewPager;
    protected PagerAdapter adapter;
    protected List<Fragment> shownFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        List<String> fragments = getFragments();
        shownFragments = new ArrayList<>(fragments.size());
        adapter = new SamplePagerAdapter(getChildFragmentManager(), fragments, getTitles());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(this);
        onPageSelected(mViewPager.getCurrentItem());
    }

    protected abstract List<String> getFragments();

    protected abstract List<String> getTitles();

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class SamplePagerAdapter extends FragmentPagerAdapter {
        List<String> fragments;
        List<String> titles;

        SamplePagerAdapter(FragmentManager fm, List<String> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = Fragment.instantiate(getContext(), fragments.get(position));
            shownFragments.add(fragment);
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
