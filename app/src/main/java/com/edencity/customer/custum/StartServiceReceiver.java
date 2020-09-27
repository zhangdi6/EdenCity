package com.edencity.customer.custum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.Logger;

// Created by Ardy on 2020/4/7.
public class StartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // 监听wifi的打开与关闭，与wifi的连接无关
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            if (wifiState == WifiManager.WIFI_STATE_DISABLED) {//wifi关闭
                Logger.d ("wifi已关闭");
            } else if (wifiState == WifiManager.WIFI_STATE_ENABLED) {//wifi开启
                Logger.d("wifi已开启");
            } else if (wifiState == WifiManager.WIFI_STATE_ENABLING) {//wifi开启中
                Logger.d( "wifi开启中");
            } else if (wifiState == WifiManager.WIFI_STATE_DISABLING) {//wifi关闭中
                Logger.d( "wifi关闭中");
            }
        }
        // 监听wifi的连接状态即是否连上了一个有效无线路由
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (parcelableExtra != null) {
                Logger.d ("wifi parcelableExtra不为空");
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {//已连接网络
                    Logger.d( "wifi 已连接网络");
                    if (networkInfo.isAvailable()) {//并且网络可用
                        Logger.d("wifi 已连接网络，并且可用");

                    } else {//并且网络不可用
                        Logger.d("wifi 已连接网络，但不可用");
                    }
                } else {//网络未连接
                    Logger.d("wifi 未连接网络");
                }
            } else {
                Logger.d("wifi parcelableExtra为空");
            }
        }
        // 监听网络连接，总网络判断，即包括wifi和移动网络的监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            //连上的网络类型判断：wifi还是移动网络
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Logger.d("总网络 连接的是wifi网络");
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                Logger.d("总网络 连接的是移动网络");
            }
            //具体连接状态判断
            checkNetworkStatus(networkInfo);
        }
    }

    private void checkNetworkStatus(NetworkInfo networkInfo) {
        if (networkInfo != null) {
            Logger.d( "总网络 info非空");
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {//已连接网络
                Logger.d("总网络 已连接网络");
                if (networkInfo.isAvailable()) {//并且网络可用
                    Logger.d( "总网络 已连接网络，并且可用");
                    //网络可用，eventbus发送有网消息


                } else {//并且网络不可用
                    //网络连接但不可用，eventbus发送弱网消息
                    Logger.d("总网络 已连接网络，但不可用");
                    AdiUtils.showToast("检测到您的网络状况不太好哦~请检查网络");


                }
            } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {//网络未连接
                Logger.d("总网络 未连接网络");
                //网络无连接，eventbus发送无网络消息
                AdiUtils.showToast("检测到您未连接网络哦~请检查网络");

            }
        } else {
            Logger.d("总网络 info为空");
        }

    }
}
