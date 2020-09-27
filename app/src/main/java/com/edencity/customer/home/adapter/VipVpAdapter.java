package com.edencity.customer.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


// Created by Ardy on 2020/4/7.
public class VipVpAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> objects;

    public VipVpAdapter(FragmentManager fm, ArrayList<Fragment> objects) {
        super(fm);
        this.objects = objects;
    }

    @Override
    public Fragment getItem(int i) {
        return objects.get(i);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

}
