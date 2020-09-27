package com.edencity.customer.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.custum.TwoBallRotationProgressBar;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.TabEntity;
import com.edencity.customer.entity.TabItemEntity;
import com.edencity.customer.home.adapter.MyShuangChuangAdapter;
import com.edencity.customer.home.adapter.TeamItemAdapter;
import com.edencity.customer.user.adapter.MyCertVpAdapter;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyShungChuangFragment extends BaseFragment2 {

    private View view;
    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart;

    private String tag;
    private MyShuangChuangAdapter adapter;
    private TwoBallRotationProgressBar mLoading;

    public MyShungChuangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_my_shung_chuang, container, false);
        initView(inflate);

        Bundle arguments = getArguments();
         tag = arguments.getString("tag");
        initData();
        return inflate;
    }

    private void initData() {
        mLoading.startAnimator();
        HashMap paramsMap = ParamsUtils.getParamsMapWithNoId("categoryId",tag);
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getXiaofeiRlvList(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<TabItemEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<TabItemEntity> o) {

                        if (o.getResult_code()==0 && o.getData()!=null){
                            Log.e("ccccc",o.toString());
                            if (o.getData().getItemizeList()!=null && o.getData().getItemizeList().size()>0){
                                adapter.addData(o.getData().getItemizeList());
                            }

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
                        mSmart.finishRefresh();
                        mLoading.stopAnimator();
                    }
                });
           /* ArrayList<BaseDebug> objects = new ArrayList<>();
            mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));

            MyShuangChuangAdapter adapter = new MyShuangChuangAdapter();
            mRlv.setAdapter(adapter);
            adapter.addData(objects);
            mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));*/

    }

    private void initView(View inflate) {

        mRlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);
        mLoading =  inflate.findViewById(R.id.loading);
        mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));
         adapter = new MyShuangChuangAdapter();
        mRlv.setAdapter(adapter);
        mSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();;
            }
        });

    }
}
