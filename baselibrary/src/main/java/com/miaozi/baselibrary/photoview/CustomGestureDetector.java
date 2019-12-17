package com.miaozi.baselibrary.photoview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/**
 * created by panshimu
 * on 2019/12/10
 */
public class CustomGestureDetector {
    private OnGestureListener mOnGestureListener;
    private final int mScaledMinimumFlingVelocity;
    private final int mScaledTouchSlop;
    private ScaleGestureDetector mScaleGestureDetector;
    private int mActivePointerId;
    //速度监听器
    private VelocityTracker mVelocityTracker;
    private float mLastTouchX;
    private float mLastTouchY;
    private boolean mIsDragging;

    public CustomGestureDetector(Context context, OnGestureListener onGestureListener) {
        this.mOnGestureListener = onGestureListener;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        //缩放最小速度
        mScaledMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        //缩放最小比例
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                if(Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)){
                    return false;
                }
                if(scaleFactor >= 0){
                    mOnGestureListener.onScale(scaleFactor,detector.getFocusX(),detector.getFocusY());
                }
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        };
        mScaleGestureDetector = new ScaleGestureDetector(context,onScaleGestureListener);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(mScaleGestureDetector != null){
            mScaleGestureDetector.onTouchEvent(event);
            return processTouchEvent(event);
        }
        return false;
    }

    private boolean processTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        //&& 左边表达式为false 不在判断右边；& 什么时候都判断两边 同时满足在向下执行
        switch (action & event.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                mVelocityTracker = VelocityTracker.obtain();
                if(mVelocityTracker != null){
                    mVelocityTracker.addMovement(event);
                }
                mLastTouchX = getActiveX(event);
                mLastTouchY = getActiveY(event);
                mIsDragging = false;
                break;
        }
        return true;
    }
    private float getActiveX(MotionEvent motionEvent){
        try {
            return motionEvent.getX(mActivePointerId);
        }catch (Exception e){
            return motionEvent.getX();
        }
    }
    private float getActiveY(MotionEvent motionEvent){
        try {
            return motionEvent.getY(mActivePointerId);
        }catch (Exception e){
            return motionEvent.getY();
        }
    }
}
