package com.edencity.customer.home.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.custum.TwoBallRotationProgressBar;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.home.fragment.FaceCollectFragment;
import com.edencity.customer.home.fragment.NameCardFragment;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.pojo.FuncResult;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ToastUtil;


import com.edencity.customer.view.LoadingDialog;
import com.icbc.bas.face.acitivity.AliveActivity;
import com.icbc.bas.face.acitivity.BASFaceActivity;
import com.icbc.bas.face.base.BASFaceConfig;
import com.icbc.bas.face.base.Result;
import com.icbc.bas.face.defines.ILiveCheckCallback;
import com.icbc.bas.face.defines.LiveCheckError;
import com.icbc.bas.face.utils.BASFaceHelper;

import java.io.File;

public class AuthenticationActivity extends BaseActivity {

    private FragmentManager manager;
    private String mName;
    private String isCardNo;
    private TwoBallRotationProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_authentication);

        manager = getSupportFragmentManager();
        initView();
    }

    private void initView() {
        FrameLayout mFrag = (FrameLayout) findViewById(R.id.frag);
        ImageView btn_back = (ImageView) findViewById(R.id.back);
        loading= findViewById(R.id.loading);
        addFragment(manager, NameCardFragment.class,R.id.frag,null);
        btn_back.setOnClickListener(v -> onBackPressed());
    }

    public void changePage(int type , String name ,String cardNo){
        if (type==1){

        }else if(type==2){
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("card",cardNo);
            addFragment(manager, FaceCollectFragment.class,R.id.frag,bundle);
        }else if (type==3){
            mName = name ;
            isCardNo = cardNo;
            startLiveness1();
        }
    }

    //默认
    private void startLiveness()
    {
        String config = "{\"motionCount\":3, " +
                "\"motionWeight\": [1,2,3,4] ," +
                " \"motionTimeout\":8," +
//                "\"isRecording\":1," +
//                "\"recordDuration\":24," +
                "\"tipsEnable\":1," +
                //"\"faceTipsEnable\":0," +
                "\"alignCountDownEnable\": 1, " +
                "\"soundEnable\": 1, " +
                "\"cameraFacing\":1, " +
                //"\"orientation\":0, " +
                "\"language\": 0"  + "}";



        Log.i("fushui", " config = " + config);

        BASFaceHelper.getInstance().startLiveCheckService(this, BASFaceActivity.class,
                new BASFaceConfig(config), 1, new ILiveCheckCallback() {
                    @Override
                    public boolean onCaptureSuccess(Context context, final Result result) {
                        boolean showDialog = true;
                        if(showDialog){
                            String imgPath = result.getImagePath();


                            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);

                            ImageView iv = new ImageView(context);
                            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv.setPadding(0, dip2px(10), 0, 0);
                            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            iv.setImageBitmap(bitmap);

                            DialogInterface.OnClickListener pl = (dialogInterface, i) -> {
                                result.success();
                                dialogInterface.dismiss();
                            };

                            DialogInterface.OnClickListener nl = (dialogInterface, i) -> {
                                result.fail();
                                dialogInterface.dismiss();
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("采集结果")
                                    .setView(iv)
                                    .setCancelable(false)
                                    .setPositiveButton(android.R.string.ok, pl)
                                    .setNegativeButton(android.R.string.cancel, nl)
                                    .show();
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });
    }

    private void updateFace(String imgPath) {

        loading.startAnimator();
        App.execute(()->{
            final FuncResult fr=App.webService().updateUserApproval(
                    App.getSp().getString("userId"),
                    App.getSp().getString("ticket"),
                    mName,
                    isCardNo,
                    new File(imgPath));
            runOnUiThread(()->{
               loading.stopAnimator();
                if (fr.code==0){
                    App.userMsg().getCustomer().setUserApproval("1010011402");

                }else if (fr.code == -3){
                    AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                    Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.defaultApp().startActivity(intent);
                }else {
                    ToastUtil.showToast(this,fr.msg);
                }
            });
        });
    }


    //自定义
    private void startLiveness1(){
        String config = "{\"motionCount\":1, " +
                "\"motionWeight\": [1,1,1,1] ," +
                " \"motionTimeout\":8," +
//                "\"isRecording\":1," +
//                "\"recordDuration\":24," +
                "\"tipsEnable\":1," +
                //"\"faceTipsEnable\":0," +
                "\"alignCountDownEnable\": 1, " +
                "\"soundEnable\": 1, " +
                "\"cameraFacing\":1, " +
                //"\"orientation\":0, " +
                "\"language\": 0"  + "}";



        Log.i("fushui", " config = " + config);

        BASFaceHelper.getInstance().startLiveCheckService(this, MyBASFaceActivity.class,
                new BASFaceConfig(config), 1, new ILiveCheckCallback() {
                    @Override
                    public boolean onCaptureSuccess(Context context, final Result result) {
                        boolean showDialog = true;
                        if(showDialog){

                            String imgPath = result.getImagePath();
                            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);

                            ImageView iv = new ImageView(context);
                            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv.setPadding(0, dip2px(10), 0, 0);
                            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            iv.setImageBitmap(bitmap);

                            DialogInterface.OnClickListener pl = (dialogInterface, i) -> {
                                result.success();
                                dialogInterface.dismiss();
                            };

                            DialogInterface.OnClickListener nl = (dialogInterface, i) -> {
                                result.fail();
                                dialogInterface.dismiss();
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("采集结果")
                                    .setView(iv)
                                    .setCancelable(false)
                                    .setPositiveButton(android.R.string.ok, pl)
                                    .setNegativeButton(android.R.string.cancel, nl)
                                    .show();
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });
    }

    //默认2
    private void startLiveness2(){
        Intent intent = new Intent(this, AliveActivity.class);
        startActivityForResult(intent, 1);
        //Intent intent = new Intent(MainActivity.this, MyBASFaceActivity.class);
        //startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                boolean isSuccess = data.getBooleanExtra(Result.BAS_RESULT_SUCCESS, false);
                if (isSuccess) {
                    String imgPath = data.getStringExtra(Result.BAS_RESULT_IMAGE_PATH);
                    /*String videoPath = data.getStringExtra(Result.BAS_RESULT_VIDEO_DATA);*/
                    Log.i("fushui","imgPath = " +  imgPath);
                   /* Log.i("fushui", "videoPath = "+ videoPath);
                    Toast.makeText(this, "采集成功, 图片路径请看日志.", Toast.LENGTH_SHORT).show();*/
                    updateFace(imgPath);
                    AdiUtils.showToast("您的实名信息已提交成功~");
                    finish();
                    return;
                } else {
                    int errorCode = data.getIntExtra(Result.BAS_RESULT_ERROR_CODE, LiveCheckError.BAS_ERROR_UNKNOWN);
                    String errorMessage = data.getStringExtra(Result.BAS_RESULT_ERROR_MESSAGE);
                    Log.i("fushui", "result =  " + LiveCheckError.getErrorDesc(errorCode));
                    AdiUtils.showToast("检测出错，请重试");
/*
                    Toast.makeText(this, String.format("错误信息: %s", LiveCheckError.getErrorDesc(errorCode)), Toast.LENGTH_SHORT).show();
*/
                    return;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
