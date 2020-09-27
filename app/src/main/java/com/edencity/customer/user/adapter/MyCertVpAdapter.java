package com.edencity.customer.user.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


// Created by Ardy on 2020/2/12.
public class MyCertVpAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> mList;
    private final ArrayList<String> mTitle;


    public MyCertVpAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> List, ArrayList<String> Title) {
        super(supportFragmentManager);


        this.mList = List;
        this.mTitle = Title;
    }


    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }

}
