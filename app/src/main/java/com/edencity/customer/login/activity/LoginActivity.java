package com.edencity.customer.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.BaseEventMsg;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.CheckUpdateEntity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.login.fragment.AmoutAndVerifyLoginFragment;
import com.edencity.customer.login.fragment.ForgetPwdFragment;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.DownloadUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private FragmentManager manager;

    private int bar_type = 1;
    private BaseDialog baseDialog;
    private String isInstall;
    private ProgressBar mProcess;
    private MyNormalTextView mProce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
         manager = getSupportFragmentManager();
        initView();
        askUpdate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (bar_type==2){
            StatusBarCompat.setStatusBarColor(this, Color.WHITE);
            StatusBarCompat.changeToLightStatusBar(this);
        }else if (bar_type==1){

        }

    }

    private void askUpdate() {
        Log.e("vip","我老了");
        HashMap hashMap = ParamsUtils.getParamsMapWithNoId("type", "2");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("vip", hashMap.toString());
        DataService.getService().checkUpdate(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<CheckUpdateEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<CheckUpdateEntity> o) {

                        Log.e("vip", o.toString());
                        Log.e("vip", AdiUtils.getAppVersionName(getApplicationContext()));

                        if (o.getResult_code() == 0 && o.getData() != null) {
                             isInstall = o.getData().getIsInstall();
                            String version = o.getData().getVersion();
                            String versionDetail = o.getData().getVersionDetail();
                            if (!version.equals(AdiUtils.getAppVersionName(getApplicationContext()))) {
                                initUpdateDialog(version, versionDetail);
                            }else{

                            }
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e("vip",e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initUpdateDialog(String version, String versionDetail) {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_update, null);
        MyNormalTextView mTvVersion = inflate.findViewById(R.id.tv_version);
        TextView mTvDetail = inflate.findViewById(R.id.textView41);
        MyNormalTextView mUpdateLater = inflate.findViewById(R.id.update_later);
        MyNormalTextView update_now= inflate.findViewById(R.id.update_now);
        LinearLayout mUpdateLayout = inflate.findViewById(R.id.update_layout);
         mProcess = inflate.findViewById(R.id.process);
         mProce = inflate.findViewById(R.id.proce);
        RelativeLayout mProcessLayout = inflate.findViewById(R.id.process_layout);
        RelativeLayout mLayout = inflate.findViewById(R.id.layout);

        mTvVersion.setText("V"+version);

        mTvDetail.setText(versionDetail);

        mUpdateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog.dismiss();
                baseDialog.setCancelable(true);
                baseDialog.dismiss();
            }
        });

        update_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateLayout.setVisibility(View.GONE);
                mProcessLayout.setVisibility(View.VISIBLE);
                downLoadFile(AppContant.APKURL);
            }
        });
        baseDialog = new BaseDialog(this, inflate, Gravity.CENTER);
        if (isInstall.equals("1")){
            mUpdateLater.setVisibility(View.GONE);
            baseDialog.setCanceledOnTouchOutside(false);
            baseDialog.setCancelable(false);
        }else{
                baseDialog.setCanceledOnTouchOutside(true);
                baseDialog.setCancelable(true);
        }
        baseDialog.show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEventMsg eventMsg){
        String type = eventMsg.getType();
        if (type.equals("forget")){
            addFragment(manager, ForgetPwdFragment.class,R.id.frag,null);
            bar_type = 2 ;
        }else if (type.equals("login")){
            bar_type = 1;
            StatusBarCompat.setStatusBarColor(this, Color.WHITE);
            StatusBarCompat.changeToLightStatusBar(this);
            addFragment(manager, AmoutAndVerifyLoginFragment.class,R.id.frag,null);
        }else{

        }
    }
    private void initView() {
        FrameLayout mFrag = (FrameLayout) findViewById(R.id.frag);
        bar_type = 1 ;
        addFragment(manager, AmoutAndVerifyLoginFragment.class,R.id.frag,null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void downLoadFile(String httpUrl) {


        DownloadUtils.download(httpUrl, new DownloadUtils.DownloadListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onDownloadLength(int length) {

            }

            @Override
            public void onProgressUpdate(int progress) {
                Log.e("update",progress+"");
                mProcess.setProgress(progress);
                mProce.setText(progress+"%");
            }

            @Override
            public void onPostExecute(File apk) {
                DownloadUtils.installApk(LoginActivity.this,apk);
            }
        });



    }

    private void openApk(String absolutePath, String apkName){
        File apkFile = new File(absolutePath, apkName);//"版本名称"
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            String[] command = {"chmod", "", absolutePath};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, App.defaultApp().getPackageName() + ".fileprovider", apkFile);//必须要用 自己包下面的fileprovider
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        startActivity(intent);
    }
}
