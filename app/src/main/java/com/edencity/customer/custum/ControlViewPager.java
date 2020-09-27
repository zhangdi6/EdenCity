package com.edencity.customer.custum;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

// Created by Ardy on 2020/4/7.
public class ControlViewPager extends ViewPager {


    private boolean noSlide = true;
    public ControlViewPager(Context context) {
        super(context);
    }
    public ControlViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setNoSlide(boolean noSlide) {
        this.noSlide = noSlide;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noSlide)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noSlide)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

}
