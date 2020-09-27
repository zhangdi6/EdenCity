package com.edencity.customer.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.edencity.customer.R;
import razerdp.basepopup.BasePopupWindow;


// Created by Ardy on 2020/2/21.
public class BasePop extends BasePopupWindow {

    public BasePop(Context context) {
        super(context);
    }

    public BasePop(Context context, boolean delayInit) {
        super(context, delayInit);
    }

    public BasePop(Context context, int width, int height) {
        super(context, width, height);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.pop_bill);
    }

    @Override
    protected Animation onCreateShowAnimation() {


        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0.5f);
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 1.0f, 0);
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

}
