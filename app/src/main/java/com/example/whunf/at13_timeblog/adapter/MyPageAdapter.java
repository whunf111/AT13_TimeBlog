package com.example.whunf.at13_timeblog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by whunf on 2015/6/11 in PC
 */
public class MyPageAdapter extends FragmentPagerAdapter {
    Fragment[] fragments;

    public MyPageAdapter(FragmentManager fm, Fragment[] f) {
        super(fm);
        fragments = f;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
