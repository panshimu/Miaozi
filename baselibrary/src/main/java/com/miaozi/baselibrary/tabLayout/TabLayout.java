package com.miaozi.baselibrary.tabLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * created by panshimu
 * on 2019/9/15
 */
public class TabLayout extends LinearLayout {
    private Paint mLinePaint;
    private int mLineColor;
    private int mLineWidth;
    public TabLayout(Context context) {
        this(context,null);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.WHITE);
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLineColor = Color.GRAY;
        mLineWidth = dp2px(1);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mLineWidth);
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,i,getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0,getMeasuredHeight(),getMeasuredWidth(),getMeasuredHeight(),mLinePaint);
        super.onDraw(canvas);
    }

    public void setHeight(int mTopLayoutHeight) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTopLayoutHeight);
        setLayoutParams(params);
    }
}
