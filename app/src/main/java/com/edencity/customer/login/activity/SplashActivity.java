package com.edencity.customer.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.CheckUpdateEntity;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.DeeSpUtil;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    /**
     * 登录
     */
    private TextView mLogin;
    /**
     * 注册
     */
    private TextView mRegist;
    private boolean mFirstLaunch;
    private BaseDialog baseDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_splash);
        mFirstLaunch = true;
        initView();

        //检验是否登录
        if (DeeSpUtil.getInstance().getString("userId") != null && DeeSpUtil.getInstance().getString("ticket") != null
                && !DeeSpUtil.getInstance().getString("userId").equals("") && !DeeSpUtil.getInstance().getString("ticket").equals("")) {

         /*   mLogin.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);*/
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
           /* mLogin.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);*/

        }
    }



    private void syncUser(String userId, String ticket) {
        if (mFirstLaunch) {
            mFirstLaunch = false;
            DataService.getInstance().syncUser(userId, ticket, new IBaseCallBack<UserMsgEntity>() {
                @Override
                public void onSuccess(UserMsgEntity data) {
                    Log.d("splash", data.toString());
                    App.defaultApp().saveUserMsg(data);
                    mLogin.setVisibility(View.GONE);
                    mRegist.setVisibility(View.GONE);
                    mRegist.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                }

                @Override
                public void onFail(String msg) {
                    Log.d("splash", msg);
                    mLogin.setVisibility(View.VISIBLE);
                    mRegist.setVisibility(View.VISIBLE);
                    /*App.defaultApp().saveUserMsg(null);*/
                }
            });
        }
    }

    private void initView() {
        mLogin = (TextView) findViewById(R.id.login);
        mRegist = (TextView) findViewById(R.id.regist);

        mLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        mRegist.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }
}
