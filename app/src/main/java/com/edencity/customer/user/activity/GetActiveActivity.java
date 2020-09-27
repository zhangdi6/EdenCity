package com.edencity.customer.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.edencity.customer.App;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.pojo.EventMessage;
import com.edencity.customer.user.fragment.GetActiveFragment;
import com.luck.picture.lib.config.PictureConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.home.activity.CardRechageActivity;
import com.edencity.customer.home.activity.SignInActivity;
import com.edencity.customer.home.fragment.ToBeVipFragment;
import com.edencity.customer.user.fragment.FeedbackFragment;
import com.umeng.message.PushAgent;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

public class GetActiveActivity extends SupportActivity implements View.OnClickListener {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_get_active);
        initView();
    }

    private void initView() {
        loadRootFragment(R.id.frag, GetActiveFragment.getInstance());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        if (data != null) {
                            EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_FEEDBACK, data));
                        }
                        break;
                }
            }
    }

}
