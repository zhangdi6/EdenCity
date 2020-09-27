package com.edencity.customer.home.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.ProductDetailEntity;
import com.edencity.customer.entity.TabEntity;
import com.edencity.customer.home.adapter.ActiveDetailAdapter;
import com.edencity.customer.home.fragment.MyShungChuangFragment;
import com.edencity.customer.user.adapter.MyCertVpAdapter;
import com.edencity.customer.user.fragment.MyCertFragment;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.ResUtils;
import com.edencity.customer.util.SHA1Utils;
import com.umeng.message.PushAgent;
import com.xw.banner.BannerConfig;
import com.xw.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;

public class MyBaseAreaActivity extends SupportActivity {

    private TabLayout mTabLayout;
    private ViewPager mVp;
    private ArrayList<String> mTitle;
    private MyMediumTextView mTitl;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
         /*type = getIntent().getIntExtra("type", 0);*/
        setContentView(R.layout.activty_my_base_area);
        initView();
        initLogic();
    }

    private void initLogic() {

            initXiaofei();


       /* for (int i = 0; i < objects.size(); i++) {
            if (i==1){
                mTabLayout.getTabAt(i).setCustomView(updatePoint(1,"2",View.VISIBLE));
            }else{
                mTabLayout.getTabAt(i).setCustomView(getTabView(i));
            }

        }

        mTabLayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                View customView = tab.getCustomView();
                MyMediumTextView title = customView.findViewById(R.id.title);
                TextView point = customView.findViewById(R.id.point);
                title.setTextColor(ResUtils.getColor(MyBaseAreaActivity.this,R.color.blue_nomal));
                point.setVisibility(View.GONE);
                tab.setCustomView(customView);
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {
                View customView = tab.getCustomView();
                MyMediumTextView title = customView.findViewById(R.id.title);
                title.setTextColor(Color.parseColor("#333333"));
                tab.setCustomView(customView);
            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });
*/

    }

    private void initXiaofei() {
        HashMap paramsMap = ParamsUtils.getParamsMapWithNoId(null,null);
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getXiaofeiTabList(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<TabEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<TabEntity> o) {

                        if (o.getResult_code()==0 && o.getData()!=null){
                            Log.e("ccccc",o.toString());
                            List<TabEntity.CategoryListBean> categoryList = o.getData().getCategoryList();
                            mTitle = new ArrayList<>();

                            ArrayList<Fragment> objects = new ArrayList<>();

                            for (int i = 0; i < categoryList.size(); i++) {
                                mTitle.add(categoryList.get(i).getCategoryValue());
                                MyShungChuangFragment myCertFragment = new MyShungChuangFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("tag",categoryList.get(i).getCategoryId());
                                myCertFragment.setArguments(bundle);
                                objects.add(myCertFragment);

                            }
                            MyCertVpAdapter vpAdapter = new MyCertVpAdapter(getSupportFragmentManager(), objects,mTitle);
                            mVp.setAdapter(vpAdapter);
                            mTabLayout.setupWithViewPager(mVp);
                            mVp.setCurrentItem(0);
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ccccc",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

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
         mTitl =  findViewById(R.id.textView19);
          mTitl.setText("大消费业态");
        mBack.setOnClickListener(v -> finish());
        mTabLayout =  findViewById(R.id.tabLayout);
        mVp = (ViewPager) findViewById(R.id.vp);
    }
}
