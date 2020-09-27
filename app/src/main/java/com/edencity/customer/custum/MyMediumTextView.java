package com.edencity.customer.custum;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.edencity.customer.App;


// Created by Ardy on 2020/1/15.

//自定义思源字体
public class MyMediumTextView extends AppCompatTextView {

    public MyMediumTextView(Context context) {
        super(context);
        initView();
    }

    public MyMediumTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyMediumTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setTypeface(App.medium);
        setIncludeFontPadding(false);
    }

}
