package com.miaozi.baselibrary.tabLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.miaozi.baselibrary.utils.ScreenUtils;

/**
 * created by panshimu
 * on 2019/9/16
 */
@SuppressLint("AppCompatCustomView")
public class RightImageView extends ImageView {
    private Paint mLinePaint;
    private int mLineColor;
    private int mLineWidth;
    public RightImageView(Context context) {
        this(context,null);
    }

    public RightImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RightImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLineColor = Color.RED;
        mLineWidth = ScreenUtils.dp2px(context,5);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mLineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0,0,5,getMeasuredHeight()-5,mLinePaint);
    }
}
