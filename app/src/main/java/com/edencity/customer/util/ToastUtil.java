package com.edencity.customer.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by 紫钢 on 2016/11/9.
 */

public class ToastUtil {
    public static void showToast(Context ctx,int msg) {
        if (ctx==null)
            return;
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
//        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//        v.setTextSize(30);
        toast.show();
    }

    public static void showToast(Context ctx,String msg) {
        if (ctx==null)
            return;
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
//        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//        v.setTextSize(30);
        toast.show();
    }

    public static void showToastTop(Context ctx,String msg) {
        if (ctx==null)
            return;
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
//        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//        v.setTextSize(30);
        toast.show();
    }

    public static void showToastLong(Context ctx,int msg) {
        if (ctx==null)
            return;
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
//        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//        v.setTextSize(30);
        toast.show();
    }

    public static void showToastLong(Context ctx,String msg) {
        if (ctx==null)
            return;
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
//        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//        v.setTextSize(30);
        toast.show();
    }
}
