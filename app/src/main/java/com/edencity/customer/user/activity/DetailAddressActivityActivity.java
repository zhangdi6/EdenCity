package com.edencity.customer.user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.AliPayEntity;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailAddressActivityActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 保存
     */
    private TextView mSave;
    /**
     * 请输入您的地址
     */
    private EditText mEditContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_address_activity);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mSave = (TextView) findViewById(R.id.save);
        mSave.setOnClickListener(this);
        mEditContent = (EditText) findViewById(R.id.edit_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.save:
                initAddress();
                break;
        }
    }



    private void initAddress() {
        if (mEditContent.getText().toString().trim().length()==0){
            AdiUtils.showToast("详细地址不可以为空");
            return;
        }

        HashMap hashMap = ParamsUtils.getParamsMap("detailAddress", mEditContent.getText().toString().trim());
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            Log.e("card",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().editUserDesc(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<AliPayEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<AliPayEntity> baseResult) {

                        Log.e("card", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            AdiUtils.showToast("修改成功");
                            Intent intent = new Intent();
                            intent.putExtra("address",mEditContent.getText().toString().trim());
                            setResult(300,intent);
                            finish();
                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AdiUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
