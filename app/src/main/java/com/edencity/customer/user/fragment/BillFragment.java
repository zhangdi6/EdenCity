package com.edencity.customer.user.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.TwoBallRotationProgressBar;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.BillOrderEntity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.user.adapter.BillAdapter;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import com.edencity.customer.view.RecyclerLineDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;
import razerdp.widget.QuickPopup;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends BaseFragment2 {

    @BindView(R.id.pop)
    MyNormalTextView billTypeButton;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.loading)
    TwoBallRotationProgressBar loading;
    @BindView(R.id.smart)
    SmartRefreshLayout mSmart;
    @BindView(R.id.layout_noting)
    LinearLayout mLoadFail;
    @BindView(R.id.bill_list)
    RecyclerView billList;

//    private int greenColor;


    private QuickPopup show;
    private int page = 1;
    private int billType = 0;
    private BillAdapter adapter;
    private boolean isOk = false;

    public BillFragment() {
        // Required empty public constructor
    }

    public static BillFragment newInstance(){
        return new BillFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout view= (RelativeLayout)inflater.inflate(R.layout.fragment_bill, container, false);
        ButterKnife.bind(this,view);
        initView();

        mBack.setOnClickListener(v -> pop());
        return view;
    }

    private void initView() {
        adapter = new BillAdapter();
        billList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        billList.setAdapter(adapter);
        billList.addItemDecoration(new RecyclerLineDivider(getContext(),LinearLayoutManager.HORIZONTAL,1,
                ContextCompat.getColor(getContext(), R.color.bg_main)));
        mSmart.setOnRefreshListener(refreshLayout -> {
            page = 1;
            initData(page);
        });
        mSmart.setOnLoadMoreListener(refreshLayout -> {
            page ++;
            initData(page);
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.changeToLightStatusBar(getActivity());
        initData(page);
    }

    //
    private void initData(int page) {
        loading.startAnimator();
        HashMap paramsMap;
        if (billType==0){
            paramsMap = ParamsUtils.getParamsMap("pageNum", page,
                    "pageSize", 20);
        }else{
            paramsMap = ParamsUtils.getParamsMap("pageNum", page,
                    "sourceType",billType, "pageSize", 20);
        }

        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getUserService().userAccountList(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<BillOrderEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<BillOrderEntity> o) {

                        Log.e("bill",o.toString());
                        if (o.getResult_code()==0 && o.getData()!=null
                                &&o.getData().getAccountChangeRecordList()!=null &&
                                o.getData().getAccountChangeRecordList().getList()!=null
                                        && o.getData().getAccountChangeRecordList().getList().size()>0){

                            
                            mLoadFail.setVisibility(View.GONE);
                            mSmart.setVisibility(View.VISIBLE);

                                if (page==1){
                                    adapter.addData(o.getData().getAccountChangeRecordList().getList());
                                }else{
                                    if (o.getData().getAccountChangeRecordList().isHasNextPage()){
                                        adapter.addNewData(o.getData().getAccountChangeRecordList().getList());
                                    }else{
                                        if (isOk){

                                        }else {
                                            if (!o.getData().getAccountChangeRecordList().isIsFirstPage()){
                                                adapter.addNewData(o.getData().getAccountChangeRecordList().getList());
                                                isOk = true ;
                                            }
                                        }

                                    }
                                }
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else{
                            mLoadFail.setVisibility(View.VISIBLE);
                            mSmart.setVisibility(View.GONE);
                        }

                        Log.e("feed",o.toString());
                        if (mSmart.isRefreshing()){
                            mSmart.finishRefresh();
                        }
                        if (mSmart.isLoading()){
                            mSmart.finishLoadMore();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoadFail.setVisibility(View.VISIBLE);
                        mSmart.setVisibility(View.GONE);
                        if (mSmart.isRefreshing()){
                            mSmart.finishRefresh();
                        }
                        if (mSmart.isLoading()){
                            mSmart.finishLoadMore();
                        }

                    }

                    @Override
                    public void onComplete() {
                        loading.stopAnimator();
                    }
                });
    }

    @Override
    public void onViewItemClicked(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                pop();
                break;
            case R.id.pop: {

                 show = QuickPopupBuilder.with(getContext())
                        .contentView(R.layout.pop_bill)
                        .config(new QuickPopupConfig()
                                .gravity(Gravity.BOTTOM)
                                .withClick(R.id.all, v -> {
                                    billTypeButton.setText("全部");
                                    billType =0;
                                    page =1 ;
                                    isOk = false ;
                                    initData(page);
                                    show.dismiss();
                                }).withClick(R.id.rechage, v -> {
                                    billTypeButton.setText("充值");
                                    page =1 ;
                                    billType = 1;
                                    isOk = false ;
                                    initData(page);
                                    show.dismiss();
                                }).withClick(R.id.buy, v -> {
                                    billTypeButton.setText("消费");
                                    page =1 ;
                                    billType = 2;
                                    isOk = false ;
                                    initData(page);
                                    show.dismiss();
                                }).withClick(R.id.give, v -> {
                                    billTypeButton.setText("赠送");
                                    page =1 ;
                                    billType = 3;
                                    isOk = false ;
                                    initData(page);
                                    show.dismiss();
                                })
                        ).show(billTypeButton);
            }
                break;
        }
    }


}
