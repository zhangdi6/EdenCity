package com.edencity.customer.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edencity.customer.App;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.BuyHistoryEntity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.home.adapter.OrderHistoryAdapter;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyHistoryFragment extends BaseFragment2 {


    private RecyclerView rlv_order;
    private SmartRefreshLayout smart;
    private ConstraintLayout loadfail;
    private MyNormalTextView tag_text;


    public BuyHistoryFragment() {
        // Required empty public constructor
    }

    public static BuyHistoryFragment getInstance(){
        return new BuyHistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_buy_history, container, false);
       initView(inflate);
        initOrderList();
        return inflate;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.changeToLightStatusBar(getActivity());
    }

    private void initOrderList() {

        smart.setVisibility(View.VISIBLE);
        loadfail.setVisibility(View.GONE);
        HashMap paramsMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getUserService().vipBuyHistory(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<BuyHistoryEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<BuyHistoryEntity> o) {
                        Log.e("vip",o.toString());
                        if (o.getResult_code()==0 && o.getData().getMembershipRecordList()!=null && o.getData().getMembershipRecordList().size()>0){

                            smart.setVisibility(View.VISIBLE);
                            loadfail.setVisibility(View.GONE);
                            OrderHistoryAdapter adapter = new OrderHistoryAdapter();
                            rlv_order.setLayoutManager(new LinearLayoutManager(getContext()));
                            rlv_order.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            adapter.addData(o.getData().getMembershipRecordList());
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else{
                            smart.setVisibility(View.GONE);
                            loadfail.setVisibility(View.VISIBLE);
                            tag_text.setText("我是有底线的~");
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("vip",e.getMessage());
                        smart.setVisibility(View.GONE);
                        loadfail.setVisibility(View.VISIBLE);
                        tag_text.setText("服务器出了点状况哦~");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView(View inflate) {
        rlv_order = inflate.findViewById(R.id.rlv_order);
        ImageView back = inflate.findViewById(R.id.back);
        smart = inflate.findViewById(R.id.smart);
        loadfail = inflate.findViewById(R.id.loadfail);
        tag_text = inflate.findViewById(R.id.tag_text);
        back.setOnClickListener(v -> pop());
    }

    }
