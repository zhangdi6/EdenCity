package com.edencity.customer.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.base.NormalWebActivity;
import com.edencity.customer.custum.TwoBallRotationProgressBar;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.TeamEntity;
import com.edencity.customer.home.adapter.TeamItemAdapter;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShuangChuangActivity extends BaseActivity {

    private ImageView mBack;
    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart;
    private TwoBallRotationProgressBar mLoading;
    private TeamItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_shuangc);
        initView();
        initData();

    }

    private void initData() {
        mLoading.startAnimator();
        //将以上参数排序，拼接keySecret
        HashMap hashMap = ParamsUtils.getParamsMapWithNoId(null,null);
        String sign = ParamsUtils.getSign(hashMap);
        try {
            //加密
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getTeamList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<TeamEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<TeamEntity> baseResult) {

                        if (baseResult.getResult_code() == 0) {
                            /*Log.e("bbbb",hashMap.toString());*/
                            if (baseResult.getData()!=null && baseResult.getData().getEntryTeams()!=null){
                                List<TeamEntity.EntryTeamsBean> entryTeams = baseResult.getData().getEntryTeams();

                                adapter.addData(entryTeams);
                                adapter.onClick(position -> {
                                    String teamDetailUrl = entryTeams.get(position).getTeamDetailUrl();
                                    Intent intent = new Intent(ShuangChuangActivity.this, NormalWebActivity.class);
                                    intent.putExtra("url",teamDetailUrl);
                                    startActivity(intent);
                                });
                            }
                            if (mSmart.isRefreshing()){
                                mSmart.finishRefresh();
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("bbbb",e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mLoading.stopAnimator();
                    }
                });
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(v -> finish());
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mSmart = (SmartRefreshLayout) findViewById(R.id.smart);
        mLoading =  findViewById(R.id.loading);
        mRlv.setLayoutManager(new LinearLayoutManager(ShuangChuangActivity.this));
        adapter = new TeamItemAdapter(2);
        mRlv.setAdapter(adapter);
        mSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });


    }
}
