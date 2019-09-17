package com.miaozi.baselibrary.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * created by panshimu
 * on 2019/9/5
 */
public class DotView extends View {
    private Paint mPaint;
    private Drawable mDrawable;
    private int mColor;

    public DotView(Context context) {
        this(context, null);
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(mDrawable != null){
            mDrawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
            mDrawable.draw(canvas);
        }else if(mColor != 0){
            if(mPaint == null){
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
            }
            mPaint.setColor(mColor);
            canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2,mPaint);
        }
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        invalidate();
    }

    public void setColor(int color){
        if(color != 0 && this.mDrawable == null){
            this.mColor = color;
            invalidate();
        }
    }
}
