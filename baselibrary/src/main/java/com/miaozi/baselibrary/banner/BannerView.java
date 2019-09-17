package com.miaozi.baselibrary.banner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.viewpager.widget.ViewPager;
import java.util.List;


/**
 * created by panshimu
 * on 2019/9/5
 */
public class BannerView extends RelativeLayout {
    private int mCurrentPosition = 0;
    private int mDotMarginLeft;
    private int mDotMarginBottom;
    private int mGravity;
    private BannerViewPager mViewPager;
    private BannerAdapter mAdapter;
    private LinearLayout mDotContainView;
    private Drawable mUnselectedDot;
    private Drawable mSelectedDot;
    private int mUnselectedColor;
    private int mSelectedColor;
    private int mDotWidth;
    private int mDotHeight;
    private int mScrollDuration;
    private int mDisplayTime;
    private boolean mAutoPlay;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDotMarginLeft = dip2px(10);
        mDotMarginBottom = dip2px(10);
        mDotWidth = dip2px(10);
        mDotHeight = dip2px(10);
        mGravity = Gravity.CENTER;
        addViewViewPager();
    }

    /**
     * 设置大小
     * @param width
     * @param height
     * @return
     */
    public BannerView setBannerViewSize(int width,int height){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = dip2px(width);
        params.height = dip2px(height);
        setLayoutParams(params);
        return this;
    }

    /**
     * 设置自动滚动
     */
    public BannerView setAutoPlay(boolean autoPlay){
        this.mAutoPlay = autoPlay;
        if(autoPlay || mViewPager!=null){
            mViewPager.startScroll();
        }
        return this;
    }
    /**
     * 设置切换动画持续时间
     * @param duration
     * @return
     */
    public BannerView setScrollDuration(int duration){
        this.mScrollDuration = duration;
        if(mViewPager != null){
            mViewPager.setScrollDuration(duration);
        }
        return this;
    }

    /**
     * 设置切换时间
     * @param displayTime
     * @return
     */
    public BannerView setDisplayTime(int displayTime){
        this.mDisplayTime = displayTime;
        if(mViewPager != null){
            mViewPager.setDisplayTime(displayTime);
        }
        return this;
    }

    /**
     * 设置指示器的间隔
     * @param padding
     * @return
     */
    public BannerView setDotPadding(int padding){
        this.mDotMarginLeft = dip2px(padding);
        if(mAdapter != null && mDotContainView != null){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mDotContainView.getLayoutParams();
            params.leftMargin = mDotMarginLeft;
            params.rightMargin = mDotMarginLeft;
            mDotContainView.setLayoutParams(params);
            for (int i = 0; i < mAdapter.getCount(); i++) {
                DotView dotView = (DotView) mDotContainView.getChildAt(i);
                LayoutParams layoutParams = (LayoutParams) dotView.getLayoutParams();
                layoutParams.leftMargin = mDotMarginLeft;
                dotView.setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    /**
     * 设置指示器和底部的间距
     * @param margin
     * @return
     */
    public BannerView setDotBottomMargin(int margin){
        this.mDotMarginBottom = dip2px(margin);
        if(mDotContainView != null){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mDotContainView.getLayoutParams();
            params.bottomMargin = mDotMarginBottom;
            mDotContainView.setLayoutParams(params);
        }
        return this;
    }

    /**
     * 设置指示器的drawable
     * @param selectedResId
     * @param unSelectedResId
     * @return
     */
    public BannerView setDotResId(int selectedResId,int unSelectedResId){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mSelectedDot = getContext().getResources().getDrawable(selectedResId, getContext().getTheme());
            mUnselectedDot = getContext().getResources().getDrawable(unSelectedResId, getContext().getTheme());
            if(mAdapter != null && mDotContainView != null){
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    DotView dotView = (DotView) mDotContainView.getChildAt(i);
                    if(i == mCurrentPosition){
                        dotView.setDrawable(mSelectedDot);
                    }else {
                        dotView.setDrawable(mUnselectedDot);
                    }
                }
            }
        }
        return this;
    }

    /**
     * 设置指示器的大小
     * @param width
     * @param height
     * @return
     */
    public BannerView setDotSize(int width,int height){
        this.mDotWidth = dip2px(width);
        this.mDotHeight = dip2px(height);
        if(mAdapter != null && mDotContainView != null){
            for (int i = 0; i < mAdapter.getCount(); i++) {
                DotView dotView = (DotView) mDotContainView.getChildAt(i);
                ViewGroup.LayoutParams params = dotView.getLayoutParams();
                params.height = mDotHeight;
                params.width = mDotWidth;
                dotView.setLayoutParams(params);
            }
        }
        return this;
    }

    /**
     * 设置指示器的颜色 如果设置了 drawable 则color无效
     * @param selectedColor
     * @param unSelectedColor
     * @return
     */
    public BannerView setDotColor(int selectedColor,int unSelectedColor){
        this.mSelectedColor = selectedColor;
        this.mUnselectedColor = unSelectedColor;
        if(mUnselectedDot == null && mSelectedDot == null && mAdapter != null && mDotContainView != null){
            for (int i = 0; i < mAdapter.getCount(); i++) {
                DotView dotView = (DotView) mDotContainView.getChildAt(i);
                if(i == mCurrentPosition){
                    dotView.setColor(mSelectedColor);
                }else {
                    dotView.setColor(mUnselectedColor);
                }
            }
        }
        return this;
    }

    /**
     * 设置指示器的显示位置
     * @param gravity
     * @return
     */
    public BannerView setDotGravity(int gravity){
        this.mGravity = gravity;
        if(mDotContainView != null){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mDotContainView.getLayoutParams();
            params.gravity = mGravity;
            mDotContainView.setLayoutParams(params);
        }
        return this;
    }

    /**
     * dip转px
     * @param size
     * @return
     */
    private int dip2px(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getContext().getResources().getDisplayMetrics());
    }

    /**
     * 添加底部指示器
     */
    private void initDot() {
        if(mDotContainView == null) {
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = mDotMarginBottom;
            layoutParams.leftMargin = mDotMarginLeft;
            layoutParams.rightMargin = mDotMarginLeft;
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            //底部指示器容器
            mDotContainView = new LinearLayout(getContext());
            mDotContainView.setLayoutParams(layoutParams);
            mDotContainView.setGravity(mGravity);
            addView(mDotContainView);
        }

        mDotContainView.removeAllViews();

        if( mAdapter == null ) return;

        //获取数量
        int count = mAdapter.getCount();

        for (int i = 0; i < count; i++) {
            DotView dotView = new DotView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotWidth,mDotHeight);
            params.leftMargin = mDotMarginLeft;
            dotView.setLayoutParams(params);
            if(i == mCurrentPosition){
                dotView.setDrawable(mSelectedDot);
                dotView.setColor(mSelectedColor);
            }else {
                dotView.setDrawable(mUnselectedDot);
                dotView.setColor(mUnselectedColor);
            }
            mDotContainView.addView(dotView);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setDotSelect( position % mAdapter.getCount() );
            }
        });
    }

    public BannerView setOnBannerClickListener(BannerViewPager.OnBannerClickListener mBannerClick) {
        if(mViewPager!=null) {
            mViewPager.setOnBannerClickListener(mBannerClick);
        }
        return this;
    }
    /**
     * 设置指示器选中点
     * @param position
     */
    private void setDotSelect(int position) {
        DotView currentDot = (DotView) mDotContainView.getChildAt(mCurrentPosition);
        DotView nextView = (DotView) mDotContainView.getChildAt(position);
        currentDot.setDrawable(mUnselectedDot);
        nextView.setDrawable(mSelectedDot);
        currentDot.setColor(mUnselectedColor);
        nextView.setColor(mSelectedColor);
        mCurrentPosition = position;
    }

    /**
     * 添加轮播界面
     */
    private void addViewViewPager() {
        mViewPager = new BannerViewPager(getContext());
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mViewPager);
        if(mScrollDuration != 0) {
            mViewPager.setScrollDuration(mScrollDuration);
        }
        if(mDisplayTime != 0) {
            mViewPager.setDisplayTime(mDisplayTime);
        }
    }



    /**
     * 设置 adapter
     * @param adapter
     * @return
     */
    public BannerView setAdapter(BannerAdapter adapter){

        mAdapter = adapter;

        mViewPager.setAdapter(adapter);

        initDot();

        if(mViewPager != null && mAutoPlay){
            mViewPager.startScroll();
        }

        return this;
    }
}

