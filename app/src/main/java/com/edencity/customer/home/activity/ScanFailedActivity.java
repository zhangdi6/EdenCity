package com.edencity.customer.home.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.util.ParamsUtils;
import com.umeng.message.PushAgent;

import me.yokeyword.fragmentation.SupportActivity;

public class ScanFailedActivity extends SupportActivity {

    private ImageButton mBtnBack;
    /**
     *
     */
    private MyNormalTextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        ParamsUtils.sign = 0;
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_scan_failed);
        initView();
    }

    private void initView() {
        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mResult = (MyNormalTextView) findViewById(R.id.result);
        String result = getIntent().getStringExtra("result");
        if (result!=null){
            mResult.setText(result);
        }
    }
}
