package com.edencity.customer.user.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.edencity.customer.App;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.home.activity.InviriteWebActivity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.view.ConfirmDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment2 {


    private BaseDialog baseDialog;
    private String pNum = "4006789038";
    private String mUrl2 = "";
    private WebView mWeb;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewItemClicked(View view) {
        switch (view.getId()){
            case R.id.btn_back: {
                pop();
            }break;

            //退出登录
            case R.id.btn_quit: {
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_quit)){
                    ConfirmDialog.showConfirm(getFragmentManager(),"您确定要退出系统，重新登录吗？",(dialog,btn) -> {
                        if (btn == DialogInterface.BUTTON_POSITIVE){

                            App.defaultApp().saveUserMsg(null);
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }


            }break;
            //分享给好友
            case R.id.layout_feedback:{
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_feedback)){
                   /* Intent intent = new Intent(getActivity(), InviriteWebActivity.class);
                    startActivity(intent);*/
                    UMImage image = new UMImage(getContext(), R.mipmap.ic_launcher);

                    //网络图片
                    UMWeb web = new UMWeb(AppContant.WebUrl2 + "/getActiveValue.html?"+
                            "&userId="+ App.userMsg().getCustomer().getUserId()+
                            "&giftRemain="+App.userMsg().getAccount().getGiftRemain());
                    web.setTitle("邀请好友");//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription("邀请有礼，赶快动动手指领取吧~");//描述

                    new ShareAction(getActivity())
                            .withMedia(web)
                            .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE)
                            .setCallback(shareListener).open();
                }



            }break;
           //客服电话
            case R.id.layout_phone:{
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_phone)){
                    View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_call, null);
                    MyMediumTextView phone = inflate.findViewById(R.id.phone);
                    phone.setText("400-678-9038?");
                    MyMediumTextView sure = inflate.findViewById(R.id.sure);
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + pNum);
                            intent.setData(data);
                            startActivity(intent);
                            baseDialog.dismiss();
                        }
                    });
                    MyMediumTextView cancle = inflate.findViewById(R.id.cancle);
                    cancle.setOnClickListener(v -> baseDialog.dismiss());
                    baseDialog = new BaseDialog(getContext(), inflate, Gravity.CENTER);
                    baseDialog.show();
                }

            }break;

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
            Toast.makeText(getActivity(),"成功分享",Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(),"分享失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(),"取消分享",Toast.LENGTH_LONG).show();
        }
    };

}
