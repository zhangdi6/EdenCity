package com.edencity.customer.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

// Created by Ardy on 2020/2/12.
public class RadioTextView extends AppCompatTextView {
    public RadioTextView(Context context) {
        super(context);
    }

    public RadioTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public RadioTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mRatio = 1;
        if (mRatio != 0) {
            float height = width / mRatio;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
