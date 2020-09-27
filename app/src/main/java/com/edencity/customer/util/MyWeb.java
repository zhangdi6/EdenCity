package com.edencity.customer.util;

import android.webkit.JavascriptInterface;

import org.greenrobot.eventbus.EventBus;

import com.edencity.customer.pojo.EventMessage;

// Created by Ardy on 2020/2/24.

public class MyWeb extends Object{

    public MyWeb() {
        super();
    }

    @JavascriptInterface//一定要写，不然H5调不到这个方法
    public void confirmSq(String msg) {
        EventBus.getDefault().post(new EventMessage(404,"go"));
    }
}
