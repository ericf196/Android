package com.optimussoftware.boohos.viewDetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guerra on 05/07/16.
 * viewPagerAdapter
 */
public class FragPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    List<String> titles;

    public FragPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    public void addTitle(String titles) {
        this.titles.add(titles);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (this.titles.get(position) != null) {
            return this.titles.get(position);
        } else {
            Log.e("FragPagerAdapter", "title null ");
            return super.getPageTitle(position);
        }
        /*if (fragments.get(position).getArguments() != null) {
            return (CharSequence) fragments.get(position).getArguments().get("name");
        } else {
            return super.getPageTitle(position);
        }*/
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
