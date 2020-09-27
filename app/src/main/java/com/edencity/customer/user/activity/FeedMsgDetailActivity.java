package com.edencity.customer.user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;

public class FeedMsgDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.cancelLightStatusBar(this);
        setContentView(R.layout.activity_feed_msg_detail);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String content = intent.getStringExtra("content");
        String time = intent.getStringExtra("time");
        ImageView mBack = (ImageView) findViewById(R.id.back);
        /**
         * TextView
         */
        TextView mDesc = (TextView) findViewById(R.id.desc);
        /**
         * 反馈回复
         */
        MyMediumTextView mTitle = (MyMediumTextView) findViewById(R.id.title);
        /**
         * TextView
         */
        MyNormalTextView mTime = (MyNormalTextView) findViewById(R.id.time);
        mBack.setOnClickListener(v -> finish());
        mDesc.setText(content);
        mTime.setText(time);
        mTitle.setText(type);
    }
}
