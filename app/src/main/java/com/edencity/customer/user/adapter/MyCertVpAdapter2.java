package com.edencity.customer.user.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


/* Created by AdScar
    on 2020/5/27.
      */

public class MyCertVpAdapter2 extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mList;

    public MyCertVpAdapter2(FragmentManager supportFragmentManager, ArrayList<Fragment> objects) {
        super(supportFragmentManager);

        this.mList = objects;
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
        return super.getPageTitle(position);
    }
}
