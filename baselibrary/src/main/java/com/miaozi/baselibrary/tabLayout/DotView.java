package com.miaozi.baselibrary.tabLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.miaozi.baselibrary.utils.ScreenUtils;

/**
 * created by panshimu
 * on 2019/9/16
 */
public class DotView extends View {
    private Paint mPaint;
    private int mRadius;
    public DotView(Context context) {
        this(context,null);
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mRadius = 2;
    }

    public void setLeftMargin(int left){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        layoutParams.leftMargin = left;
        setLayoutParams(layoutParams);
    }
    public int getMarginLeft(){
        return ((LinearLayout.LayoutParams) getLayoutParams()).leftMargin;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0 ,0,getMeasuredWidth(),getMeasuredHeight(),mRadius,mRadius,mPaint);
        }
    }
}
