package com.edencity.customer.user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.edencity.customer.App;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.TwoBallRotationProgressBar;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.MsgListEntity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.user.adapter.FeedMsgAdapter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedMsgActivity extends BaseActivity {

    private RecyclerView mRlvMsg;
    private SmartRefreshLayout mSmart;
    private MyMediumTextView mChangeState;
    private ConstraintLayout mLoadFail;
    private MyNormalTextView mTag;
    /*private TwoBallRotationProgressBar loading;*/
    private int type;
    private FeedMsgAdapter adapter;
    private int page = 1;
    private boolean loadOk = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.fragment_feed_msg);
        Intent intent = getIntent();
         type = intent.getIntExtra("type", 0);
        initView();
        initRlv();
        initData(page);

    }



    private void initRlv() {
         mRlvMsg.setLayoutManager(new LinearLayoutManager(this));
         adapter = new FeedMsgAdapter();
         mRlvMsg.setAdapter(adapter);

         adapter.onItemClick(i -> {
             setItemRead(i);
             Intent intent = new Intent(FeedMsgActivity.this,FeedMsgDetailActivity.class);
            intent.putExtra("type",adapter.mList.get(i).getMessageType());
            intent.putExtra("time",adapter.mList.get(i).getCreatetime());
            intent.putExtra("content",adapter.mList.get(i).getContent());
             startActivity(intent);
         });
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
    protected void onResume() {
        super.onResume();

    }

    private void setItemRead(int i) {

        HashMap paramsMap = ParamsUtils.getParamsMap("messageId", adapter.mList.get(i).getMyMessageId());
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().setReaded(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult o) {
                        if (o.getResult_code()==0){
                            adapter.changeState(i);
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initData(int page) {

        /*loading.startAnimator();*/
        HashMap paramsMap = ParamsUtils.getParamsMap("pageNum",page);
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().getMsg(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<MsgListEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<MsgListEntity> o) {
                        Log.d("feed",page+"----"+o.toString());
                        if (o.getData()!=null && o.getData().getMessageList()!=null &&
                                o.getData().getMessageList().getList()!=null &&
                                o.getData().getMessageList().getList().size()>0){
                            mLoadFail.setVisibility(View.GONE);
                            mSmart.setVisibility(View.VISIBLE);


                                if (page==1){
                                    adapter.addData(o.getData().getMessageList().getList());
                                }else{

                                    if (o.getData().getMessageList().isHasNextPage()){
                                        if (!o.getData().getMessageList().isIsFirstPage()){
                                            adapter.addNewData(o.getData().getMessageList().getList());
                                        }
                                    }else {
                                        if (! adapter.isHadFootView()){
                                            adapter.addFootView(o.getData().getMessageList().getList());
                                        }
                                    }
                                }

                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else{
                            mLoadFail.setVisibility(View.VISIBLE);
                            mTag.setText("我是有底线的");
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
                        mTag.setText("我是有底线的");
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


                    }
                });
    }

    private void initView() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(v -> finish());
        mRlvMsg = (RecyclerView) findViewById(R.id.rlv_msg);
        mSmart = (SmartRefreshLayout) findViewById(R.id.smart);
        mLoadFail = (ConstraintLayout) findViewById(R.id.loadfail);
        /*loading = findViewById(R.id.loading);*/
        mTag = (MyNormalTextView) findViewById(R.id.tag_text);
        mChangeState = findViewById(R.id.change_read_state);


        if (type==0){
            mChangeState.setVisibility(View.GONE);
        }else{
            mChangeState.setVisibility(View.VISIBLE);
            mChangeState.setText("全部已读");
        }


        mChangeState.setOnClickListener(v -> {
            setAllRead();
        });
    }

    private void setAllRead() {
        HashMap paramsMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().setAllReaded(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult o) {
                        /*adapter.addData(objects);*/
                        Log.e("feed",o.toString());
                        if (o.getResult_code()==0){
                            adapter.changeAllState();
                            mChangeState.setVisibility(View.GONE);
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
