package com.miaozi.baselibrary.selectedLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * created by panshimu
 * on 2019/12/6
 */
public class SelectedTextView extends AppCompatTextView {
    private Paint mPaint;
    // 1->left 2->right 3->all
    private int mRoundDirection = 4;
    public void setRoundDirection(int roundDirection) {
        this.mRoundDirection = roundDirection;
        invalidate();
    }
    private int mBackgroundColor = Color.BLUE;
    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
        invalidate();
    }
    public SelectedTextView(Context context) {
        this(context,null);
    }
    public SelectedTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SelectedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mBackgroundColor);
        Path path = new Path();
        if(mRoundDirection == 1) {
            RectF oval = new RectF(0, 0, getHeight(), getHeight());
            path.arcTo(oval, 90, 180);
            path.lineTo(getWidth(), 0);
            path.lineTo(getWidth(), getHeight());
            path.close();
            canvas.drawPath(path, mPaint);
        }else if(mRoundDirection == 2){
            path.moveTo(0,0);
            path.lineTo(getWidth() - getHeight(),0);
            RectF oval = new RectF(getWidth() - getHeight(),0,getWidth(),getHeight());
            path.arcTo(oval, 270, 180);
            path.lineTo(0,getHeight());
            path.close();
            canvas.drawPath(path, mPaint);
        }else if(mRoundDirection == 3){
            RectF oval = new RectF(0, 0, getHeight(), getHeight());
            path.arcTo(oval, 90, 180);
            RectF ovalRight = new RectF(getWidth() - getHeight(),0,getWidth(),getHeight());
            path.arcTo(ovalRight, 270, 180);
            path.close();
            canvas.drawPath(path, mPaint);
        }else {
            canvas.drawRoundRect(0,0,getWidth(),getHeight(),0,0,mPaint);
        }
        super.onDraw(canvas);
    }
}
