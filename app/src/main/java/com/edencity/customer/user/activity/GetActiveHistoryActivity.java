package com.edencity.customer.user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.edencity.customer.App;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;

import com.edencity.customer.R;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.ActiveGetHistoryEntity;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.user.adapter.GetActiveListAdapter;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetActiveHistoryActivity extends BaseActivity {

    private RecyclerView mRlvActiveValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_get_active_history);
        initView();
        initData();
    }

    private void initData() {

            HashMap<String,Object>hashMap = ParamsUtils.getParamsMap();

        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
            Log.e("cert",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().getObtainRecord(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<ActiveGetHistoryEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<ActiveGetHistoryEntity> result) {

                        Log.e("cert",result.toString());
                        if (result.getResult_code()==0&&result.getData()!=null){
                            ActiveGetHistoryEntity data = result.getData();
                            List<ActiveGetHistoryEntity.ActivityObtainRecordBean> record = data.getActivityObtainRecord();

                            GetActiveListAdapter getActiveListAdapter = new GetActiveListAdapter();
                            mRlvActiveValue.setLayoutManager(new LinearLayoutManager(GetActiveHistoryActivity.this));
                            getActiveListAdapter.addData(record);
                            mRlvActiveValue.setAdapter(getActiveListAdapter);

                            getActiveListAdapter.onItemClick(postion -> {

                            });
                        }else if (result.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cert",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(v -> finish());
        mRlvActiveValue = (RecyclerView) findViewById(R.id.rlv_active_value);
        SmartRefreshLayout mSmart = (SmartRefreshLayout) findViewById(R.id.smart);
    }
}
