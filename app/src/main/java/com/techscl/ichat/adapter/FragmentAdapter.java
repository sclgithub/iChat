package com.techscl.ichat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dllo on 15/7/30.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;

    public FragmentAdapter(FragmentManager fm, Fragment[] f) {
        super(fm);
        fragments = f;
    }


    @Override
    public Fragment getItem(int i) {
        if (fragments != null && fragments.length > 0)
            return fragments[i];
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null && fragments.length > 0)
            return fragments.length;
        return 0;
    }

}
