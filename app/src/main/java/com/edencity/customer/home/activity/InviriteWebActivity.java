package com.edencity.customer.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.edencity.customer.base.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.pojo.EventMessage;

import com.edencity.customer.util.MyWeb;

public class InviriteWebActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.web)
    WebView     mWeb;
    @BindView(R.id.title)
    MyMediumTextView mTitle;
    @BindView(R.id.close)
    MyNormalTextView mClose;
    @BindView(R.id.process)
    ProgressBar mProcess;
    private RelativeLayout outrl;
    private UserMsgEntity userMsg;
    private String mUrl;
    private String mUrl2;
    private String BaseUrl = "http://h5.edencitybrand.com";
    private WebSettings settings;
    /*private String BaseUrl = "http://192.168.31.170:8888";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this,Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_invirite_web);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    //让webview可以回退
    @Override
    public void onBackPressed() {
        if (mWeb!=null&&mWeb.canGoBack()) {
            mWeb.goBack();
        }else{
            finish();
        }
    }


    private void initData() {

         userMsg = App.userMsg();

         mUrl =  BaseUrl+"/InviteFriends.html?"+
                "&userId="+ userMsg.getCustomer().getUserId()+
                "&memberType="+userMsg.getCustomer().getUserVipLevel()+
                "&giftRemain="+userMsg.getAccount().getGiftRemain()+
                "&activityAmount="+userMsg.getCustomer().getActivityAmount();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecerver(EventMessage eventMessage){

        Log.d("sss",eventMessage.toString());
        if (eventMessage.getType()==404){
            UMImage image = new UMImage(InviriteWebActivity.this, R.mipmap.ic_launcher);
           /* mUrl2 = BaseUrl + "getActiveValue.html?"+
                    "&userId="+ userMsg.getCustomer().getUserId()+
                    "&giftRemain="+userMsg.getAccount().getGiftRemain();
            mWeb.loadUrl(mUrl2);*/

            //网络图片
            UMWeb web = new UMWeb(BaseUrl + "/getActiveValue.html?"+
                    "&userId="+ userMsg.getCustomer().getUserId()+
                    "&giftRemain="+userMsg.getAccount().getGiftRemain());
            web.setTitle("邀请好友");//标题
            web.setThumb(image);  //缩略图
            web.setDescription("邀请有礼，赶快动动手指领取吧~");//描述

            new ShareAction(InviriteWebActivity.this)
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE)
                    .setCallback(shareListener).open();
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            /*Toast.makeText(InviriteWebActivity.this,"成功分享",Toast.LENGTH_LONG).show();*/
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(InviriteWebActivity.this,"分享失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(InviriteWebActivity.this,"取消分享",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {

        mClose.setVisibility(View.GONE);
        mImgBack.setOnClickListener(v -> onBackPressed());

        //webview设置
        mWeb.loadUrl(mUrl);
        mWeb.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //如果网页可以回退,则说明现在不处于web页最外层,这时候显示关闭按钮
                settings.setBlockNetworkImage(false);
                if (mWeb.canGoBack()){
                    mClose.setVisibility(View.VISIBLE);
                    mClose.setOnClickListener(v -> finish());
                }else{
                    mClose.setVisibility(View.GONE);
                }
                super.onPageFinished(view, url);
            }
        });

        mWeb.canGoBack();
         settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom (false);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setTextZoom(100);
        settings.setLoadWithOverviewMode(true);
        settings.setBlockNetworkImage(true);

        settings .setJavaScriptCanOpenWindowsAutomatically(true);
        mWeb.addJavascriptInterface(new MyWeb(), "test");
        mWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTitle.setText(title);
                super.onReceivedTitle(view, title);
            }




            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //加载进度
                mProcess.setProgress(newProgress);
                mProcess.setVisibility(View.VISIBLE);
                if (newProgress==100){
                    mProcess.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWeb.clearCache(true);
        mWeb.destroy();
        EventBus.getDefault().unregister(this);
    }
}
