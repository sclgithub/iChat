package com.techscl.ichat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by songchunlin on 15/9/17.
 */
public class NearbyAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles;

    public NearbyAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments != null && fragments.size() > 0)
            return fragments.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null && fragments.size() > 0)
            return fragments.size();
        return 0;
    }

    public CharSequence getPageTitle(int position) {

        return titles[position];
    }
}
