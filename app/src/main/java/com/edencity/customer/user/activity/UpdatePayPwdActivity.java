package com.edencity.customer.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.base.BaseEventMsg;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.user.fragment.UpdatePayPwdFragment1;
import com.edencity.customer.user.fragment.UpdatePayPwdFragment2;
import com.edencity.customer.user.fragment.UpdatePayPwdFragment3;

public class UpdatePayPwdActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private MyMediumTextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_update_pay_pwd);
        EventBus.getDefault().register(this);
         fragmentManager = getSupportFragmentManager();
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEventMsg eventMsg){

        if (eventMsg.getType()!=null && eventMsg.getType().equals("pay")){
            if (eventMsg.getMsg()!=null && eventMsg.getMsg().equals("1")){
                Bundle bundle = new Bundle();
                bundle.putString("verify",eventMsg.getParamStr2());
                addFragment(fragmentManager, UpdatePayPwdFragment2.class,R.id.frag,bundle);
            }else if (eventMsg.getMsg()!=null && eventMsg.getMsg().equals("2")){
                Bundle bundle = new Bundle();
                bundle.putString("verify",eventMsg.getParamStr2());
                bundle.putString("vCode",eventMsg.getParamStr());
                addFragment(fragmentManager, UpdatePayPwdFragment3.class,R.id.frag,bundle);
            }else if (eventMsg.getMsg()!=null && eventMsg.getMsg().equals("3")){
                Bundle bundle = new Bundle();
                bundle.putString("verify",eventMsg.getParamStr2());
                addFragment(fragmentManager, UpdatePayPwdFragment2.class,R.id.frag,bundle);
            }
        }

    }


    private void initView() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mTitle =  findViewById(R.id.title);
        mBack.setOnClickListener(v -> onBackPressed());
        FrameLayout mFrag = (FrameLayout) findViewById(R.id.frag);
        addFragment(fragmentManager, UpdatePayPwdFragment1.class,R.id.frag,null);

        UserMsgEntity userMsg = App.userMsg();
        if (userMsg.getCustomer().getPayPassword().equals("true")){
            mTitle.setText("修改支付密码");
        }else{
            mTitle.setText("设置支付密码");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
