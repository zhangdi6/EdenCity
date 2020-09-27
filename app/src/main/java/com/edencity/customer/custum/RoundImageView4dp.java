package com.edencity.customer.custum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;


import com.scwang.smartrefresh.layout.util.DensityUtil;

public class RoundImageView4dp extends AppCompatImageView {
    float width,height;
    private int mRadiusPx;

    public RoundImageView4dp(Context context) {
        this(context, null);
    }

    public RoundImageView4dp(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView4dp(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadiusPx = DensityUtil.dp2px(4);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //这里的目的是将画布设置成一个顶部边缘是圆角的矩形
        if (width > mRadiusPx && height > mRadiusPx) {
            Path path = new Path();

            path.moveTo(mRadiusPx, 0);
            path.lineTo(width - mRadiusPx, 0);
            path.quadTo(width, 0, width, mRadiusPx);
            path.lineTo(width, height - mRadiusPx);
            path.quadTo(width, height, width - mRadiusPx, height);
            path.lineTo(mRadiusPx, height);
            path.quadTo(0, height, 0, height - mRadiusPx);
            path.lineTo(0, mRadiusPx);
            path.quadTo(0, 0, mRadiusPx, 0);


            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }
}
