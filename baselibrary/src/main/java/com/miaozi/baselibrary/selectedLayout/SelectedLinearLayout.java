package com.miaozi.baselibrary.selectedLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * created by panshimu
 * on 2019/12/6
 */
public class SelectedLinearLayout extends LinearLayout {
    private Paint mPaint;
    //外部线圈的宽度
    private int mBorderWidth = 2;
    //线圈颜色
    private int mBorderColor = Color.GRAY;
    //item集合
    private List<SelectedTextView> mTextViews;
    //选中字体颜色
    private int mSelectedTextColor = Color.WHITE;
    //默认字体颜色
    private int mDefaultTextColor = Color.GRAY;
    //选中字体背景色
    private int mSelectedBackgroundColor = Color.BLUE;
    //默认字体背景色
    private int mDefaultBackgroundColor = Color.LTGRAY;
    //默认字体大小
    private int mTextSize = 15;
    //默认选中下标
    private int mSelectedPosition = 0;
    //数据集
    private String[] mItems;
    //分割线宽度
    private int mDivideLineWidth = 2;
    //分割线颜色
    private int mDivideLineColor = Color.GRAY;
    public SelectedLinearLayout(Context context) {
        this(context,null);
    }
    public SelectedLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SelectedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        initPaint();
    }
    public void setItemData(String[] mItems){
        if(mItems.length < 2)
            throw new RuntimeException("this view only support data length more than two number");
       this.mItems = mItems;
       updateData();
    }
    private void updateData(){
        if(mItems == null) return;
        if(mTextViews == null) {
            mTextViews = new ArrayList<>(mItems.length);
            removeAllViews();
        }else {
            mTextViews.clear();
            removeAllViews();
        }

        for (int i = 0; i < mItems.length; i++) {
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            final SelectedTextView textView = new SelectedTextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setText(mItems[i]);
            textView.setTextSize(mTextSize);
            if(i == 0) {
                textView.setRoundDirection(1);
            }else if(i == mItems.length - 1){
                textView.setRoundDirection(2);
            }else {
                textView.setRoundDirection(4);
            }

            if(i == mSelectedPosition){
                textView.setTextColor(mSelectedTextColor);
                textView.setBackgroundColor(mSelectedBackgroundColor);
            }else {
                textView.setTextColor(mDefaultTextColor);
                textView.setBackgroundColor(mDefaultBackgroundColor);
            }
            addView(textView,params);
            if(mDivideLineWidth > 0 && i != mItems.length - 1 && mItems.length > 1){
                View line = new View(getContext());
                line.setBackgroundColor(mDivideLineColor);
                line.setLayoutParams(new ViewGroup.LayoutParams(mDivideLineWidth, ViewGroup.LayoutParams.MATCH_PARENT));
                addView(line);
            }
            mTextViews.add(textView);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSelected(finalI);
                }
            });
        }
        invalidate();
    }

    private void doSelected(int position){
        if(mListener != null) {
            mListener.onItemClick(position);
        }
        for (int i = 0; i < mTextViews.size(); i++) {
            SelectedTextView item = mTextViews.get(i);
            if(i == position){
                item.setTextColor(mSelectedTextColor);
                item.setBackgroundColor(mSelectedBackgroundColor);
            }else {
                item.setTextColor(mDefaultTextColor);
                item.setBackgroundColor(mDefaultBackgroundColor);
            }
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
        Path path = new Path();
        //圆的半径
        int r = getWidth() > getHeight() ? getHeight() : getWidth();
        //画左半圆
        RectF oval = new RectF(mBorderWidth / 2, mBorderWidth / 2, r - mBorderWidth / 2, getHeight() - mBorderWidth / 2);
        path.arcTo(oval, 90, 180);
        //画右半圆
        RectF ovalRight = new RectF(getWidth() - r + mBorderWidth / 2,mBorderWidth / 2,getWidth() - mBorderWidth / 2,getHeight() - mBorderWidth / 2);
        path.arcTo(ovalRight, 270, 180);
        //回到起点
        path.close();
        canvas.drawPath(path, mPaint);
    }
    public void setBorderWidth(int mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }
    public void setBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
        invalidate();
    }
    public void setSelectedTextColor(int mSelectedTextColor) {
        this.mSelectedTextColor = mSelectedTextColor;
        updateData();
    }
    public void setDefaultTextColor(int mDefaultTextColor) {
        this.mDefaultTextColor = mDefaultTextColor;
        updateData();
    }
    public void setSelectedBackgroundColor(int mSelectedBackgroundColor) {
        this.mSelectedBackgroundColor = mSelectedBackgroundColor;
        updateData();
    }
    public void setDefaultBackgroundColor(int mDefaultBackgroundColor) {
        this.mDefaultBackgroundColor = mDefaultBackgroundColor;
        updateData();
    }
    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        updateData();
    }
    public void setDivideLineWidth(int mDivideLineWidth) {
        this.mDivideLineWidth = mDivideLineWidth;
    }
    public void setDivideLineColor(int mDivideLineColor) {
        this.mDivideLineColor = mDivideLineColor;
    }
    public void setSelectedPosition(int mSelectedPosition) {
        this.mSelectedPosition = mSelectedPosition;
        updateData();
    }
    private OnItemOnclickListener mListener;
    public void setOnItemOnclickListener(OnItemOnclickListener listener) {
        this.mListener = listener;
    }
    public interface OnItemOnclickListener{
        void onItemClick(int position);
    }
}
