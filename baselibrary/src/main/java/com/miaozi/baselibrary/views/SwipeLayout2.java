package com.miaozi.baselibrary.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

/**
 * created by panshimu
 * on 2019/9/9
 */
public class SwipeLayout2 extends RelativeLayout{
    private static final String TAG = "SwipeView";
    private int mScaledTouchSlop;
    private View mLeftView;
    private View mRightView;
    private View mContentView;
    private int mLeftViewWidth;
    private int mRightViewWidth;
    private PointF mFirstPoint;
    private PointF mLastPoint;
    private int mFinalDistance;
    private boolean mIsScroll;
    private STATE mState = STATE.CLOSE;
    private STATE mOpenState = STATE.ALL;
    private static SwipeLayout2 mSwipeLayout;

    public SwipeLayout2(Context context) {
        this(context,null);
    }

    public SwipeLayout2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //创建辅助对象
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        //大于这个值才算滑动
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLeftView = getChildAt(0);
        mRightView = getChildAt(1);
        mContentView = getChildAt(2);
        if(mLeftView != null){
            LayoutParams leftParams = (LayoutParams) mLeftView.getLayoutParams();
            leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            leftParams.width = LayoutParams.WRAP_CONTENT;
            mLeftViewWidth = mLeftView.getMeasuredWidth();
        }
        if(mContentView != null) {
            mContentView.setClickable(true);
            mContentView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mState == STATE.CLOSE) {
                        if(mItemOnclickListener != null)
                            mItemOnclickListener.itemOnclick();
                    }else {
                        closeMenu();
                    }
                }
            });
        }
        if(mRightView != null) {
            LayoutParams rightParams = (LayoutParams) mRightView.getLayoutParams();
            rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightParams.width = LayoutParams.WRAP_CONTENT;
            mRightViewWidth = mRightView.getMeasuredWidth();
        }

        if(mOpenState == STATE.RIGHT){
            mLeftView = null;
        }else if(mOpenState == STATE.LEFT){
            mRightView = null;
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(mFirstPoint == null) {
                    mFirstPoint = new PointF();
                }
                if(mLastPoint == null){
                    mLastPoint = new PointF();
                }
                if(mSwipeLayout != null){
                    if(mSwipeLayout != this){
                        mSwipeLayout.closeMenu();
                    }
                }
                mFirstPoint.set(ev.getRawX(),ev.getRawY());
                mLastPoint.set(ev.getRawX(),ev.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getRawX() - mLastPoint.x;
                float dy = ev.getRawY() - mLastPoint.y;
                if(Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > mScaledTouchSlop){
                    break;
                }
                if(mContentView != null) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        mContentView.setTranslationX(mContentView.getTranslationX() + dx);
                    }
                    //边界
                    if (mContentView.getTranslationX() < 0){//向左滑动 rightView显示
                        if(mRightView != null){
                            if(mContentView.getTranslationX() < -mRightViewWidth){
                                mContentView.setTranslationX(-mRightViewWidth);
                            }
                        }else {
                            mContentView.setTranslationX(0);
                        }
                    }
                    if (mContentView.getTranslationX() > 0){//向右滑动 leftView显示
                        if(mLeftView != null){
                            if(mContentView.getTranslationX() > mLeftViewWidth){
                                mContentView.setTranslationX(mLeftViewWidth);
                            }
                        }else {
                            mContentView.setTranslationX(0);
                        }
                    }
                }
                //告诉父容器不要拦截
                if(Math.abs(dx) > mScaledTouchSlop){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastPoint.set(ev.getRawX(),ev.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                mFinalDistance = (int) (ev.getRawX() - mFirstPoint.x);
                if(Math.abs(mFinalDistance) > 0){
                    //证明滑动了
                    mIsScroll = true;
                }
                //判断是否打开或关闭菜单
                isCanOpenMenu();
                //处理结果
                dealStateResult();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void dealStateResult() {
        if(mContentView != null) {
            if (mState == STATE.RIGHT) {
                mSwipeLayout = this;
                setContentViewTranslationX(mContentView.getTranslationX(), -mRightViewWidth);
            } else if (mState == STATE.CLOSE) {
                mSwipeLayout = null;
                setContentViewTranslationX(mContentView.getTranslationX(),0);
            } else if (mState == STATE.LEFT) {
                mSwipeLayout = this;
                setContentViewTranslationX(mContentView.getTranslationX(),mLeftViewWidth);
            }
        }
    }

    private void isCanOpenMenu() {
        if(mContentView != null){
            float translationX = mContentView.getTranslationX();
            if(translationX < 0){//rightView 显示出来了
                if(mFinalDistance > 0){//打开情况下向右滑动
                    mState = STATE.CLOSE;
                }else if(mFinalDistance < 0){//打开情况下向左滑动
                    if(mRightView != null && translationX <= -mRightViewWidth/2){
                        mState = STATE.RIGHT;
                    }else {
                        mState = STATE.CLOSE;
                    }
                }
            }else if(translationX > 0){//leftView 显示出来了
                if(mFinalDistance > 0){//打开情况下向右滑动
                    if(mLeftView != null && translationX >= mLeftViewWidth/2){
                        mState = STATE.LEFT;
                    }else {
                        mState = STATE.CLOSE;
                    }
                }else if(mFinalDistance < 0){//打开情况下向左滑动
                    mState = STATE.CLOSE;
                }
            }
        }
    }
    public void setOpenState(STATE state){
        this.mOpenState = state;
        invalidate();
    }
    /**
     * 设置移动动画
     * @param min
     * @param max
     */
    private void setContentViewTranslationX(float min,float max){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(min,max);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mContentView.setTranslationX(animatedValue);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(mIsScroll) {
                    mFinalDistance = 0;
                    mIsScroll = false;
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    public SwipeLayout.ItemOnclickListener mItemOnclickListener;

    public void setItemOnclickListener(SwipeLayout.ItemOnclickListener mItemOnclickListener) {
        this.mItemOnclickListener = mItemOnclickListener;
    }

    public interface ItemOnclickListener{
        void itemOnclick();
    }

    public void closeMenu(){
        mState = STATE.CLOSE;
        dealStateResult();
    }
    public void openMenu(final SwipeLayout2.STATE state){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mState = state;
                dealStateResult();
            }
        },200);
    }
    public enum STATE{
        LEFT,RIGHT,CLOSE,ALL
    }
}
