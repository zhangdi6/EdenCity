package com.edencity.customer.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.user.adapter.MyCertVpAdapter;
import com.edencity.customer.user.adapter.MyCertVpAdapter2;
import com.edencity.customer.user.fragment.MyCertFragment;
import com.edencity.customer.util.ResUtils;
import com.umeng.message.PushAgent;

import me.yokeyword.fragmentation.SupportActivity;

public class MyCertificateActivity extends SupportActivity {

    private TabLayout mTabLayout;
    private ViewPager mVp;
    private ArrayList<String> mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_my_certificate);
        initView();
        initLogic();
    }

    private void initLogic() {



        mTitle = new ArrayList<>();
        mTitle.add("全部");
        mTitle.add("待使用");
        mTitle.add("已使用");
        mTitle.add("已过期");
        ArrayList<Fragment> objects = new ArrayList<>();

        for (int i = -1; i < 3; i++) {
            MyCertFragment myCertFragment = new MyCertFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("tag",i);
            myCertFragment.setArguments(bundle);
            objects.add(myCertFragment);
        }

        MyCertVpAdapter2 vpAdapter = new MyCertVpAdapter2(getSupportFragmentManager(), objects);
        mVp.setAdapter(vpAdapter);
        mTabLayout.setupWithViewPager(mVp);
        for (int i = 0; i < objects.size(); i++) {
            if (i==1){
                mTabLayout.getTabAt(i).setCustomView(updatePoint(1,"2",View.VISIBLE));
            }else{
                mTabLayout.getTabAt(i).setCustomView(getTabView(i));
            }

        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                MyMediumTextView title = customView.findViewById(R.id.title);
                TextView point = customView.findViewById(R.id.point);
                title.setTextColor(ResUtils.getColor(MyCertificateActivity.this,R.color.blue_nomal));
                point.setVisibility(View.GONE);
                tab.setCustomView(customView);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                MyMediumTextView title = customView.findViewById(R.id.title);
                title.setTextColor(Color.parseColor("#333333"));
                tab.setCustomView(customView);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mVp.setCurrentItem(1);
    }
    public View getTabView(int position) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.item_tablayout, null);
        MyMediumTextView tabTitle =  tabView.findViewById(R.id.title);
        TextView point =  tabView.findViewById(R.id.point);
        point.setVisibility(View.GONE);
        tabTitle.setText(mTitle.get(position));
        return tabView;
    }

    public View updatePoint( int position,String text , int visibility) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.item_tablayout, null);
        MyMediumTextView tabTitle =  tabView.findViewById(R.id.title);
        TextView point =  tabView.findViewById(R.id.point);
        point.setText(text);
        point.setVisibility(visibility);
        tabTitle.setText(mTitle.get(position));
        return tabView;
    }

    private void initView() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(v -> finish());
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mVp = (ViewPager) findViewById(R.id.vp);
    }
}
