package com.miaozi.baselibrary.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * created by panshimu
 * on 2019/9/9
 */
public class SwipeView2 extends RelativeLayout{
    private static final String TAG = "SwipeView";
    private SwipeView2 mViewCache;
    private float finallyDistanceX;
    private float mFraction = 0.3f;
    private int mScaledTouchSlop;
    private boolean mCanLeftSwipe = true;
    private boolean mCanRightSwipe = true;
    private View mLeftView;
    private View mRightView;
    private View mContentView;
    private int mFinallyTranslationX;
    private int mLeftViewWidth;
    private int mRightViewWidth;
    private float mDownX;
    private float mDownY;

    public SwipeView2(Context context) {
        this(context,null);
    }

    public SwipeView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeView2(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mContentView.setClickable(true);

        //强制更改为 WRAP_CONTENT 保证 宽度是包裹内容
        LayoutParams leftParams = (LayoutParams) mLeftView.getLayoutParams();
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftParams.width = LayoutParams.WRAP_CONTENT;
        LayoutParams rightParams = (LayoutParams) mRightView.getLayoutParams();
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightParams.width = LayoutParams.WRAP_CONTENT;

        mLeftViewWidth = mLeftView.getMeasuredWidth();
        mRightViewWidth = mRightView.getMeasuredWidth();

        mContentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.contentViewOnclick();
                }
            }
        });
    }
    private int mState;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = ev.getRawX() - mDownX;
                float distanceY = ev.getRawY() - mDownY;
                if (Math.abs(distanceY) > mScaledTouchSlop  && Math.abs(distanceY) > Math.abs(distanceX)) {
                    break;
                }
//                Log.d(TAG,"mState="+mState+" distanceX="+distanceX);
                if(mState == 1){
                    if(distanceX >=0) {
                        mContentView.setTranslationX(distanceX + mFinallyTranslationX);
                    }
                }else if(mState == 2){
                    if(distanceX <= 0) {
                        mContentView.setTranslationX(distanceX + mFinallyTranslationX);
                    }
                }else {
                    if(distanceX < 0 && distanceX >= -mRightView.getMeasuredWidth()) {
                        mContentView.setTranslationX(distanceX);
                    }else if(distanceX < 0 && distanceX < -mRightView.getMeasuredWidth()){
                        mContentView.setTranslationX(-mRightView.getMeasuredWidth());
                    }else if(distanceX > 0 && distanceX <= mLeftView.getMeasuredWidth()){
                        mContentView.setTranslationX(distanceX);
                    }else if(distanceX > 0 && distanceX > mLeftView.getMeasuredWidth()){
                        mContentView.setTranslationX(mLeftView.getMeasuredWidth());
                    }
                }
                if(mContentView.getTranslationX() > 0){
                    mLeftView.setVisibility(VISIBLE);
                    mRightView.setVisibility(GONE);
                }else {
                    mLeftView.setVisibility(GONE);
                    mRightView.setVisibility(VISIBLE);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(Math.abs(ev.getRawX() - mDownX) < mScaledTouchSlop && mState != 0){
                    setContentViewTranslationX(mContentView.getTranslationX(),0);
                    break;
                }
                float direction = ev.getRawX() - mDownX;
                float translationX = mContentView.getTranslationX();
                //还没有打开的情况下
                if(mState == 0) {
                    if (direction < 0) {//向左滑动
                        //当滑动距离大于2/3的时候自动展开
                        if (translationX <= -mRightView.getMeasuredWidth() * 2 / 3) {
                            mFinallyTranslationX = -mRightView.getMeasuredWidth();
                            setContentViewTranslationX(translationX, mFinallyTranslationX);
                        }else {//否则关闭
                            mFinallyTranslationX = 0;
                            setContentViewTranslationX(translationX,0);
                        }
                    }else if(direction > 0){//向右滑动
                        if (translationX >= mLeftView.getMeasuredWidth() * 2 / 3) {
                            mFinallyTranslationX = mLeftView.getMeasuredWidth();
                            setContentViewTranslationX(translationX, mFinallyTranslationX);
                        }else {//否则关闭
                            mFinallyTranslationX = 0;
                            setContentViewTranslationX(translationX,0);
                        }
                    }else if(direction == 0){
                        Log.d(TAG,"sssssssssss");
                    }
                }else if(mState == 1){//向左完全打开
                    if(direction < 0){//向左滑动
                        //不做处理
                    }else if(direction > 0){//向右滑动
                        //执行关闭
                        mFinallyTranslationX = 0;
                        setContentViewTranslationX(translationX,0);
                    }else if(direction == 0){
                        Log.d(TAG,"aaaaaaaaaaa");
                    }
                }else if(mState == 2){
                    if(direction < 0){
                        //执行关闭
                        mFinallyTranslationX = 0;
                        setContentViewTranslationX(translationX,0);
                    }else if(direction > 0){//向右滑动

                    }else if(direction == 0){
                        Log.d(TAG,"bbbbbbbbbbb");
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
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
                if(mContentView.getTranslationX() == mLeftViewWidth){
                    mState = 2;
                }else if(mContentView.getTranslationX() == -mRightViewWidth){
                    mState = 1;
                }else if(mContentView.getTranslationX() == 0){
                    mState = 0;
                }
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
                //滑动时拦截点击事件
                float distanceX = event.getRawX() - mDownX;
                if (Math.abs(distanceX) > mScaledTouchSlop) {
                    // 当手指拖动值大于mScaledTouchSlop值时，认为应该进行滚动，拦截子控件的事件
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    public ContentViewOnclickListener mClickListener;

    public void setContentViewOnclickListener(ContentViewOnclickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface ContentViewOnclickListener{
        void contentViewOnclick();
    }
}
