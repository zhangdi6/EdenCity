package com.edencity.customer.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.dtr.zxing.activity.CaptureActivity;

import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.StartServiceReceiver;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.CheckUpdateEntity;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.DeeSpUtil;
import com.edencity.customer.util.DownloadUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import com.luck.picture.lib.config.PictureConfig;
import com.umeng.message.PushAgent;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.home.fragment.IndexFragment;

import com.edencity.customer.pojo.EventMessage;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends SupportActivity implements EasyPermissions.PermissionCallbacks{

    private static final int REQUEST_CODE_APP_INSTALL = 494;
    //照片和剪裁
    private boolean cropAfterCapture;
    private int cropRatioX=0,cropRatioY=0;
    private int cropMaxWidth=0,cropMaxHeight=0;
    private File cropedImageFile;
    private BaseDialog baseDialog;
    private StartServiceReceiver startServiceReceiver;
    private String isInstall;
    private ProgressBar mProcess;
    private MyNormalTextView mProce;
    private String version;
    private String versionDetail;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        setContentView(R.layout.activity_main);
        startServiceReceiver = new StartServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(startServiceReceiver, filter);

        boolean mFirstLaunch = true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        RelativeLayout mainContainer = findViewById(R.id.main_container);

        //设置状态栏透明
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
        }else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        askUpdate();
        loadRootFragment(R.id.main_container, IndexFragment.newInstance());
        if(Build.VERSION.SDK_INT>=23){
            if (EasyPermissions.hasPermissions(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE
                    )){
            }else {

                EasyPermissions.requestPermissions(this,"",123,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                );
            }
          /*  String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,mPermissionList,123);*/
        }

       DataService.getInstance().syncUser(DeeSpUtil.getInstance().getString("userId"),
               DeeSpUtil.getInstance().getString("ticket"), new IBaseCallBack<UserMsgEntity>() {
           @Override
           public void onSuccess(UserMsgEntity data) {
               Log.d("bbbbb",data.toString());
               App.defaultApp().saveUserMsg(data);
           }

           @Override
           public void onFail(String msg) {
               /*App.defaultApp().saveUserMsg(null);*/
               Log.d("bbbbb","null");
           }
       });

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
    /**
     * 响应界面的点击事件
     * @param view 点击的视图
     */
    public void onViewItemClicked(View view) {
        try {
            ISupportFragment fragment = getTopFragment();
            if (fragment!=null){
                ((BaseFragment2)fragment).onViewItemClicked(view);
            }
        }catch (Exception e){
            Log.e("edencity",e.getMessage(),e);
        }
    }

    /**
     * 软键盘管理
     */
    public void showSoftKeyboard(View targetView){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (null != imm) {
            imm.showSoftInput(targetView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void askUpdate() {
        HashMap hashMap = ParamsUtils.getParamsMapWithNoId("type", "2");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("update",hashMap.toString());
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
                        if (o.getResult_code() == 0 && o.getData() != null) {
                             isInstall = o.getData().getIsInstall();
                             version = o.getData().getVersion();
                             versionDetail = o.getData().getVersionDetail();
                            if (!version.equals(AdiUtils.getAppVersionName(getApplicationContext()))) {
                                if (Build.VERSION.SDK_INT >= 26) {
                                    boolean hasInstallPermission = isHasInstallPermissionWithO(MainActivity.this);
                                    if (!hasInstallPermission) {
                                        startInstallPermissionSettingActivity(MainActivity.this);
                                        return;
                                    }else {
                                        initUpdateDialog(version,versionDetail);
                                    }
                                }else {
                                    initUpdateDialog(version,versionDetail);
                                }

                            }
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isHasInstallPermissionWithO(Context context){
        if (context == null){
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }
    /**
     * 开启设置安装未知来源应用权限界面
     * @param context
     */
    @RequiresApi (api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Context context) {
        if (context == null){
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        ((Activity)context).startActivityForResult(intent,REQUEST_CODE_APP_INSTALL);
    }

    public void initUpdateDialog( String version, String versionDetail) {
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
                baseDialog.setCancelable(false);
                baseDialog.setCanceledOnTouchOutside(false);
                downLoadFile(AppContant.APKURL);
                //执行下载操作


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


    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


   /* @Override
    public void onBackPressedSupport() {
        if (baseDialog.isShowing()){
            onDestroy();
        }else{
            super.onBackPressedSupport();
        }
    }
*/
    /**
     * 从相册/拍照获取图片
     */
    public void getPhoto(int cropRatioX,int cropRatioY, int maxWidth, int maxHeight){
        this.cropRatioX = cropRatioX;
        this.cropRatioY = cropRatioY;
        this.cropMaxWidth=maxWidth;
        this.cropMaxHeight=maxHeight;
        cropAfterCapture = cropRatioX + cropRatioY + maxWidth + maxHeight > 0;
        if (EasyPermissions.hasPermissions(getApplicationContext(),Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            onPermissionsGranted(100,null);
        }else {
            EasyPermissions.requestPermissions(this,"应用需要获取您的身份证照片以完成实名认证",
                    100,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 扫二维码
     */
    public void doQrcodeScan(){
        if (EasyPermissions.hasPermissions(getApplicationContext(),Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            Intent it=new Intent(MainActivity.this,CaptureActivity.class);
            startActivityForResult(it,1101);

        }else {
            EasyPermissions.requestPermissions(this,"应用需要使用相机扫码",101,
                    Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(startServiceReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == 100){
            if (EventBus.getDefault().hasSubscriberForEvent(EventMessage.class)){
                Matisse.from(this)
                        //选择视频和图片

                        .choose(MimeType.ofImage())
                        //自定义选择选择的类型
                        //.choose(MimeType.of(MimeType.JPEG,MimeType.AVI))
                        //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                        .showSingleMediaType(true)
                        //有序选择图片 123456...
                        /*.countable(true)*/
                        .capture(true)
                        //最大选择数量为9
                        //.maxSelectable(9)
                        //蓝色主题
                        //.theme(R.style.Matisse_Zhihu)
                        //黑色主题
                        .theme(R.style.Matisse_Dracula)
                        //Glide加载方式
                        //.imageEngine(new GlideEngine())
                        //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                        .countable(true)
                        .captureStrategy(new CaptureStrategy(true,"com.edencity.customer.fileprovider"))
                        //选择方向
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        //界面中缩略图的质量
                        .thumbnailScale(0.85f)
                        //Picasso加载方式
                        .imageEngine(new PicassoEngine())
                        //请求码
                        .forResult(1000);
            }
        }else if (requestCode==101){
            Intent it=new Intent(MainActivity.this,CaptureActivity.class);
            startActivityForResult(it,1101);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            /*new AppSettingsDialog.Builder(this).build().show();*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            if (resultCode == Activity.RESULT_OK){
                if (cropAfterCapture){
                    List<Uri> uris=Matisse.obtainResult(data);
                    if (uris!=null && uris.size()>0){
                        cropImage(uris.get(0));
                        return;
                    }
                }else {
                    List<String> uris=Matisse.obtainPathResult(data);
                    if (uris!=null && uris.size()>0){
                        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_PHOTO,uris.get(0)));
                    }
                }
            }
        }else if (requestCode == UCrop.REQUEST_CROP){
            if (resultCode == Activity.RESULT_OK){
                if (cropedImageFile !=null && cropedImageFile.exists()){
                    EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_PHOTO,cropedImageFile.getPath()));
                }
            }
            cropedImageFile=null;
        }else if (requestCode==1101){
            if (resultCode == Activity.RESULT_OK){
                EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_QRCODE,data.getStringExtra("result")));
            }
        }else if (requestCode== PictureConfig.CHOOSE_REQUEST){
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        if (data!=null){
                            EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_FEEDBACK,data));
                        }
                        break;
                }

        }else if (resultCode== Activity.RESULT_OK ){
            switch (requestCode){
                case REQUEST_CODE_APP_INSTALL : {
                    initUpdateDialog( version, versionDetail);
                }
            }
        }
    }

    private void cropImage(Uri uri){
        UCrop.Options options = new UCrop.Options();
        //裁剪后图片保存在文件夹中
        cropedImageFile=new File(getCacheDir(), "snap"+System.currentTimeMillis()+".jpg");
        Uri destinationUri = Uri.fromFile(cropedImageFile);
        UCrop uCrop = UCrop.of(uri, destinationUri);//第一个参数是裁剪前的uri,第二个参数是裁剪后的uri
        if (cropRatioX>0 && cropRatioY>0){
            uCrop.withAspectRatio(cropRatioX,cropRatioY);
        }
        if (cropMaxWidth>0 && cropMaxHeight>0){
            uCrop.withMaxResultSize(cropMaxWidth,cropMaxHeight);
        }
        //下面参数分别是缩放,旋转,裁剪框的比例
        options.setAllowedGestures(UCropActivity.ALL,UCropActivity.NONE,UCropActivity.ALL);
        options.setToolbarTitle("图片裁剪");//设置标题栏文字
        options.setMaxScaleMultiplier(3);//设置最大缩放比例
        options.setHideBottomControls(false);//隐藏下边控制栏
        options.setShowCropGrid(false);  //设置是否显示裁剪网格

        options.setToolbarWidgetColor(Color.parseColor("#000000"));//标题字的颜色以及按钮颜色
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
        options.setToolbarColor(Color.parseColor("#ffffff")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#ffffff"));//设置状态栏颜色
        options.setCropFrameColor(Color.YELLOW);//设置裁剪框的颜色
        uCrop.withOptions(options);
        uCrop.start(this);
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
                baseDialog.setCancelable(true);
                baseDialog.setCanceledOnTouchOutside(true);
                baseDialog.dismiss();
                DownloadUtils.installApk(MainActivity.this,apk);
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
