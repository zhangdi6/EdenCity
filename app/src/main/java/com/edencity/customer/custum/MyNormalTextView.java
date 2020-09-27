package com.edencity.customer.custum;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.edencity.customer.App;


// Created by Ardy on 2020/1/15.

//自定义思源字体
public class MyNormalTextView extends AppCompatTextView {

    public MyNormalTextView(Context context) {
        super(context);
        initView();
    }



    public MyNormalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyNormalTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView( ) {
        setTypeface(App.normal);
        setIncludeFontPadding(false);
    }

}
