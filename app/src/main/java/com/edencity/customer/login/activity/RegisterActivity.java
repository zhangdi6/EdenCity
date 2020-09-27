package com.edencity.customer.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.UserLoginEntity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 6~16位含数字、字母
     */
    private EditText mEtPwd;
    /**
     * 6~16位含数字、字母
     */
    private EditText mEtConfimPwd;
    /**
     * 输入手机号
     */
    private EditText mEtPhone;
    /**
     * 输入短信验证码
     */
    private EditText mEtVerify;
    /**
     * 获取验证码
     */
    private TextView mGetVerify;
    private CheckBox mCb;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mEtConfimPwd = (EditText) findViewById(R.id.et_confim_pwd);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtVerify = (EditText) findViewById(R.id.et_verify);
        mGetVerify = (TextView) findViewById(R.id.getVerify);
        mGetVerify.setOnClickListener(this);
        mCb = (CheckBox) findViewById(R.id.cb);
        /**
         * 我已阅读并同意
         */
        TextView mTv = (TextView) findViewById(R.id.tv);
        /**
         * 《伊甸城隐私协议》
         *
         */
        TextView mTvAgreeement = (TextView) findViewById(R.id.tv_agreeement);
        /**
         * 完成
         */
        TextView mRegister = (TextView) findViewById(R.id.register);
        mRegister.setOnClickListener(this);
        /**
         * 去登录？
         */
        TextView mGoToLogin = (TextView) findViewById(R.id.go_to_login);
        mGoToLogin.setOnClickListener(this);

        AdiUtils.showDeleteButton(mEtPhone, 1, null);

        AdiUtils.showDeleteButton(mEtPwd, 2, null);

        AdiUtils.showDeleteButton(mEtConfimPwd, 2, null);

        AdiUtils.showDeleteButton(mEtVerify, 3, null);
    }

    private void initVerify(String type, String phone) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("type", type);
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getService().get(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        Log.e("RegisterActivity", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            AdiUtils.showToast("验证码发送成功");
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RegisterActivity", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        time();
    }


    private void initRegister(String phone, String verify, String password) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("verificationCode", verify);
        hashMap.put("password", password);
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
            Log.e("llll", hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getService().register(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserLoginEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserLoginEntity> baseResult) {

                        Log.e("lll", baseResult.toString());


                        if (baseResult != null && baseResult.getResult_code() == 0) {
                            AdiUtils.showToast("注册成功");
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {


                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("111", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.getVerify:
                if (AdiUtils.isPhone(mEtPhone.getText().toString())) {
                    initVerify("regist", mEtPhone.getText().toString());

                }

                break;
            case R.id.register:

                if (mEtPwd.getText().toString().equals(mEtConfimPwd.getText().toString())) {
                    if (AdiUtils.isPhone(mEtPhone.getText().toString())) {
                        if (AdiUtils.isPassword(mEtPwd.getText().toString())) {
                            if (AdiUtils.isVerify(mEtVerify.getText().toString())) {
                                if (mCb.isChecked()){
                                        initRegister(mEtPhone.getText().toString(),
                                                mEtVerify.getText().toString(), mEtPwd.getText().toString());

                                }else{
                                    AdiUtils.showToast("请先阅读并勾选伊甸城隐私协议");
                                }

                            }
                        }
                    }
                } else {
                    AdiUtils.showToast("两次密码输入不一致");
                }

                break;
            case R.id.go_to_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void time(){
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mGetVerify.setText("重新发送("+millisUntilFinished/1000+"秒)");
                mGetVerify.setEnabled(false);
            }

            @Override
            public void onFinish() {
                mGetVerify.setText("重新发送");
                mGetVerify.setEnabled(true);
            }
        };
        countDownTimer.start();
    }
}
