package com.miaozi.baselibrary.views;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.util.ArrayList;

/**
 * created by panshimu
 * on 2019/9/24
 */
public class SwipeLayout extends ViewGroup {
    private static final String TAG = "SwipeLayout";
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);
    private LinearLayout linearLayout;
    private View mLeftView;
    private View mCenterView;
    private View mRightView;
    private float mDownX;
    private float mDownY;
    private int mScaledTouchSlop;
    private int mLeftViewWidth;
    private int mRightViewWidth;
    private int mCurrentScrollX;
    private static final int OPEN_LEFT = 1;
    private static final int OPEN_RIGHT = 2;
    private STATE mOpenState = STATE.ALL;
    private STATE mState = STATE.CLOSE;
    private PointF mFirstPointF;
    private PointF mLastPointF;
    //最后移动距离
    private int mFinalDistance;
    private Scroller mScroller;
    private boolean mIsScroll;
    private static SwipeLayout mSwipeLayout;

    public SwipeLayout(Context context) {
        this(context,null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        //大于这个侧认为是滑动
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //参考frameLayout测量代码
        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        int maxWidth = 0;
        int maxHeight = 0;
        int childState = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if(childView.getVisibility() != GONE){
                measureChildWithMargins(childView,widthMeasureSpec,0,heightMeasureSpec,0);
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                maxWidth = Math.max(maxWidth,childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight,childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                childState = combineMeasuredStates(childState,childView.getMeasuredState());
                //宽度高度不是 MATCH_PARENT 或是 确切的值 而是 WRAP_CONTENT
                if(measureMatchParentChildren){
                    if(lp.width == LayoutParams.MATCH_PARENT || lp.height == LayoutParams.MATCH_PARENT){
                        mMatchParentChildren.add(childView);
                    }
                }
            }
        }
        maxWidth = Math.max(maxWidth,getSuggestedMinimumWidth());
        maxHeight = Math.max(maxHeight,getSuggestedMinimumHeight());
        setMeasuredDimension(resolveSizeAndState(maxWidth,widthMeasureSpec,childState),
                resolveSizeAndState(maxHeight,heightMeasureSpec,childState));
        int size = mMatchParentChildren.size();
        if(size > 1){
            for (int i = 0; i < size; i++) {
                View child = mMatchParentChildren.get(i);
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int childWidthMeasureSpec;
                int childHeightMeasureSpec;
                //铺满
                if(lp.width == LayoutParams.MATCH_PARENT){
                    int childWidth = Math.max(0,getMeasuredWidth() - lp.leftMargin - lp.rightMargin);
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY);
                }else {
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            lp.leftMargin + lp.rightMargin,lp.width);
                }
                if(lp.height == LayoutParams.MATCH_PARENT){
                    int childHeight = Math.max(0,getMeasuredHeight() - lp.bottomMargin - lp.topMargin);
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY);
                }else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            lp.topMargin + lp.bottomMargin,lp.height);
                }
                child.measure(childWidthMeasureSpec,childHeightMeasureSpec);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        mLeftView = getChildAt(0);
        mCenterView = getChildAt(1);
        mRightView = getChildAt(2);

        if(mLeftView != null){
            MarginLayoutParams lp = (MarginLayoutParams) mLeftView.getLayoutParams();
            int leftL = 0 - (mLeftView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
            int topL = lp.topMargin + top;
            int rightL = 0 - lp.rightMargin;
            int bottomL = topL + mLeftView.getMeasuredHeight();
            mLeftView.layout(leftL,topL,rightL,bottomL);
            mLeftViewWidth = Math.abs(rightL - leftL);
        }
        if(mCenterView != null){
            MarginLayoutParams lp = (MarginLayoutParams) mCenterView.getLayoutParams();
            int leftC = left + lp.leftMargin;
            int topC = lp.topMargin + top;
            int rightC = leftC +  lp.rightMargin + mCenterView.getMeasuredWidth();
            int bottomC = topC + mCenterView.getMeasuredHeight();
            mCenterView.layout(leftC,topC,rightC,bottomC);
            mCenterView.setOnClickListener(new OnClickListener() {
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
        if(mRightView != null){
            MarginLayoutParams lp = (MarginLayoutParams) mRightView.getLayoutParams();
            int leftR = mCenterView.getRight() + lp.leftMargin + right;
            int topR = lp.topMargin + top;
            int rightR = leftR +  lp.rightMargin + mRightView.getMeasuredWidth();
            int bottomR = topR + mRightView.getMeasuredHeight();
            mRightView.layout(leftR,topR,rightR,bottomR);
            mRightViewWidth = Math.abs(rightR - leftR);
        }

        if(mOpenState == STATE.RIGHT){
            mLeftView = null;
        }else if(mOpenState == STATE.LEFT){
            mRightView = null;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mFirstPointF == null){
                    mFirstPointF = new PointF();
                }
                if (mLastPointF == null){
                    mLastPointF = new PointF();
                }
                if(mSwipeLayout != null){
                    if(mSwipeLayout != this){
                        mSwipeLayout.closeMenu();
                    }
                }
                mLastPointF.set(event.getRawX(), event.getRawY());
                mFirstPointF.set(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getRawX() - mLastPointF.x);
                int dy = (int) (event.getRawY() - mLastPointF.y);
                if(Math.abs(dx) > Math.abs(dy)){
                    scrollBy(-dx,0);
                }
                //边界
                if(getScrollX() > 0){//向左滑动,rightView显示
                    if(mRightView != null){
                        if(getScrollX() > mRightViewWidth){
                            scrollTo(mRightViewWidth,0);
                        }
                    }else {
                        scrollTo(0,0);
                    }
                }else if(getScrollX() < 0){
                    if(mLeftView != null){
                        if(getScrollX() < -mLeftViewWidth){
                            scrollTo(-mLeftViewWidth,0);
                        }
                    }else {
                        scrollTo(0,0);
                    }
                }
                //告诉父容器不要拦截
                if(Math.abs(dx) > mScaledTouchSlop){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastPointF.set(event.getRawX(),event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                mFinalDistance = (int) (event.getRawX() - mFirstPointF.x);
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
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                //滑动过程中 禁止点击
                if(Math.abs(mFinalDistance) > 0){
//                    return true;
                }
            case MotionEvent.ACTION_UP:
                if(mIsScroll) {
                    mFinalDistance = 0;
                    mIsScroll = false;
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void dealStateResult() {
        if(mState == STATE.RIGHT){
            mSwipeLayout = this;
            mScroller.startScroll(getScrollX(),0,mRightViewWidth - getScrollX(),0);
        }else if(mState == STATE.CLOSE){
            mSwipeLayout = null;
            mScroller.startScroll(getScrollX(),0,-getScrollX(),0);
        }else if(mState == STATE.LEFT){
            mSwipeLayout = this;
            mScroller.startScroll(getScrollX(),0,-getScrollX() - mLeftViewWidth,0);
        }
        //执行这个 mScroll.startScroll()才会执行
        invalidate();
    }

    private void isCanOpenMenu() {
        if(getScrollX() > 0){//rightView 显示出来了
            if(mFinalDistance > 0){//打开情况下向右滑动
                mState = STATE.CLOSE;
            }else if(mFinalDistance < 0){//打开情况下向左滑动
                if(mRightView != null && getScrollX() >= mRightViewWidth/2){
                    mState = STATE.RIGHT;
                }else {
                    mState = STATE.CLOSE;
                }
            }
        }else if(getScrollX() < 0){//leftView 显示出来了
            if(mFinalDistance > 0){//打开情况下向右滑动
                if(mLeftView != null && getScrollX() <= -mLeftViewWidth/2){
                    mState = STATE.LEFT;
                }else {
                    mState = STATE.CLOSE;
                }
            }else if(mFinalDistance < 0){//打开情况下向左滑动
                mState = STATE.CLOSE;
            }
        }
    }

    public void closeMenu(){
        mState = STATE.CLOSE;
        dealStateResult();
    }
    public void openMenu(final STATE state){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mState = state;
                dealStateResult();
            }
        },300);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    public SwipeLayout setOpenState(STATE state){
        this.mOpenState = state;
        invalidate();
        return this;
    }
    public ItemOnclickListener mItemOnclickListener;

    public void setItemOnclickListener(ItemOnclickListener mItemOnclickListener) {
        this.mItemOnclickListener = mItemOnclickListener;
    }

    public interface ItemOnclickListener{
        void itemOnclick();
    }
    public enum STATE{
        LEFT,RIGHT,CLOSE,ALL
    }
}
