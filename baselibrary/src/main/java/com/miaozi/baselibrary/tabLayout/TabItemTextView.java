package com.miaozi.baselibrary.tabLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * created by panshimu
 * on 2019/9/17
 */
@SuppressLint("AppCompatCustomView")
public class TabItemTextView extends TextView {
    private Paint mPaint;
    public TabItemTextView(Context context) {
        this(context,null);
    }

    public TabItemTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabItemTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas,0,getWidth(),mPaint);
    }
    public void setTextColor(int color){
        mPaint.setColor(color);
        invalidate();
    }
    public void setTextSize(int size){
        mPaint.setTextSize(sp2px(size));
        invalidate();
    }
    private int sp2px(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,size,getResources().getDisplayMetrics());

    }
    private void drawText(Canvas canvas,int left,int right,Paint paint){

        canvas.save();

        String text = getText().toString();

        //获取字体的宽度

        Rect changeBounds =new Rect();

        paint.getTextBounds(text,0,text.length(),changeBounds);

        int dx = getWidth() /2 - changeBounds.width() /2;

        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();

        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;

        int baseLine = getHeight()/2 + dy;

        //绘制变色

        Rect changeRect =new Rect(left,0,right,getHeight());

        canvas.clipRect(changeRect);

        canvas.drawText(text,dx,baseLine,paint);

        canvas.restore();

    }
}
