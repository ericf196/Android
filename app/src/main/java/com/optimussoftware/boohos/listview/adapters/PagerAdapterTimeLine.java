package com.optimussoftware.boohos.listview.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.optimussoftware.boohos.listview.fragment.AllInterestFragment;
import com.optimussoftware.boohos.listview.fragment.ByInterestFragment;

public class PagerAdapterTimeLine extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterTimeLine(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllInterestFragment tab1 = new AllInterestFragment();
                return tab1;
            case 1:

                ByInterestFragment tab2 = new ByInterestFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}