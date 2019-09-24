package com.miaozi.baselibrary.tabLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.miaozi.baselibrary.R;
import com.miaozi.baselibrary.utils.ColorUtil;
import com.miaozi.baselibrary.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * created by panshimu
 * on 2019/9/15
 */
public class SwipeTabLayout extends LinearLayout {
    //导航栏的高度
    private int mTabItemSelectedTextColor;
    private int mTabItemUnSelectedTextColor;
    private int mTabItemTextSize = 15;
    private DotView mDotView;
    private int mCurrentPosition;
    private List<ItemTab> mItemList;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPage;
    private TabContentAdapter mTabContentAdapter;
    private List<Fragment> mFragmentList;
    private boolean mShowDot = true;
    private HorizontalScrollView mHorizontalScrollView;
    private boolean isStop;
    //放大比例
    private float mTabTextViewScale = 0.3f;
    //平移和放大缩小执行动画时间
    private int mDuration = 300;
    private boolean isShowTabRightMenu = true;
    private ImageView mIvRight;
    private int mTextViewMargin;
    private int mMaxPosition;

    public SwipeTabLayout(Context context) {
        this(context,null);
    }

    public SwipeTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mCurrentPosition = 0;
        mTextViewMargin = ScreenUtils.dp2px(context,20);
        initTopLayout();
        initContentLayout();
    }

    /**
     * 设置tab字体大小
     * @param size
     * @return
     */
    public SwipeTabLayout setTabItemTextSize(int size) {
        this.mTabItemTextSize = size;
        if(mItemList!=null && mItemList.size() > 0){
            for (ItemTab tv : mItemList){
                tv.getTv().setTextSize(size);
            }
        }
        return this;
    }

    /**
     * 设置默认选中
     * @param position
     * @return
     */
    public SwipeTabLayout setSelectedPosition(int position){
        this.mCurrentPosition = position;
        setTabFragmentSelect(position);
        return this;
    }

    /**
     * 设置是否显示右边按钮菜单
     * @param show
     * @return
     */
    public SwipeTabLayout showTabRightMenu(boolean show){
        this.isShowTabRightMenu = show;
        if(mIvRight != null){
            if(isShowTabRightMenu){
                mIvRight.setVisibility(VISIBLE);
            }else {
                mIvRight.setVisibility(GONE);
            }
        }
        return this;
    }

    /**
     * 设置tab字体颜色
     * @param unselectedColor
     * @param selectedColor
     * @return
     */
    public SwipeTabLayout setTabItemTextColor(int unselectedColor,int selectedColor){
        this.mTabItemUnSelectedTextColor = unselectedColor;
        this.mTabItemSelectedTextColor = selectedColor;
        if(mItemList!=null && mItemList.size()>0){
            for (int i = 0; i < mItemList.size(); i++) {
                if(mCurrentPosition == i) {
                    mItemList.get(i).getTv().setTextColor(mTabItemSelectedTextColor);
                }else {
                    mItemList.get(i).getTv().setTextColor(mTabItemUnSelectedTextColor);
                }
            }
        }
        return this;
    }

    private void initContentLayout() {
        mViewPage = findViewById(R.id.view_page);
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //向右滑动的过程中 position 会减 1
                //position == mCurrentPosition == 0 第一步
                //position + 1 == mCurrentPosition 向右滑动
                //position == mCurrentPosition > 0 向左滑动
                TextView currentTextView = null;
                TextView nextTextView = null;
                //初始化会执行一次
                if(mItemList!=null && mItemList.size()>0 && mCurrentPosition == 0){
                    if(mItemList.get(mCurrentPosition).getCenterX() == 0) {
                        setItemLocation();
                    }
                }
                //快速滑动的时候 onPageSelected() 执行了 这里还会执行 所以加一个标志
                if(!isStop && mItemList.size() > 0) {
                    if (mCurrentPosition == position && mCurrentPosition >= 0 && position >= 0) {
                        ItemTab currentTab = mItemList.get(mCurrentPosition);
                        currentTextView = currentTab.getTv();
                        currentTextView.setTextColor(ColorUtil.gradualChanged(positionOffset, mTabItemSelectedTextColor, mTabItemUnSelectedTextColor));
                        currentTextView.setScaleX(1.0f + mTabTextViewScale - mTabTextViewScale * positionOffset);
                        currentTextView.setScaleY(1.0f + mTabTextViewScale - mTabTextViewScale * positionOffset);
                        if (position + 1 < mItemList.size()) {
                            ItemTab nextTab = mItemList.get(position + 1);
                            nextTextView = nextTab.getTv();
                            nextTextView.setTextColor(ColorUtil.gradualChanged(positionOffset, mTabItemUnSelectedTextColor, mTabItemSelectedTextColor));
                            nextTextView.setScaleX(1.0f + mTabTextViewScale * positionOffset);
                            nextTextView.setScaleY(1.0f + mTabTextViewScale * positionOffset);

                            int centerX = nextTab.getCenterX() - currentTab.getCenterX();

                            int currentMarginLeft = currentTab.getCenterX() - mDotView.getMeasuredWidth()/2;

                            int marginLeft = (int) ( currentMarginLeft + centerX * positionOffset);

                            mDotView.setLeftMargin(marginLeft);
                        }
                    } else if (position + 1 == mCurrentPosition) {
                        //向右
                        ItemTab currentTab = mItemList.get(mCurrentPosition);
                        currentTextView = currentTab.getTv();
                        currentTextView.setTextColor(ColorUtil.gradualChanged(1 - positionOffset, mTabItemSelectedTextColor, mTabItemUnSelectedTextColor));
                        currentTextView.setScaleX(1.0f + mTabTextViewScale - mTabTextViewScale * (1 - positionOffset));
                        currentTextView.setScaleY(1.0f + mTabTextViewScale - mTabTextViewScale * (1 - positionOffset));

                        ItemTab lastTab = mItemList.get(position);
                        nextTextView = lastTab.getTv();
                        nextTextView.setTextColor(ColorUtil.gradualChanged(1 - positionOffset, mTabItemUnSelectedTextColor, mTabItemSelectedTextColor));
                        nextTextView.setScaleX(1.0f + mTabTextViewScale * (1 - positionOffset));
                        nextTextView.setScaleY(1.0f + mTabTextViewScale * (1 - positionOffset));

                        int centerX = currentTab.getCenterX() - lastTab.getCenterX();
                        int currentMarginLeft = currentTab.getCenterX() - mDotView.getMeasuredWidth()/2;
                        int marginLeft = (int) ( currentMarginLeft - centerX * (  1 - positionOffset ));

                        mDotView.setLeftMargin(marginLeft);

                    }
                }
                if(mSwipeListener!=null)
                    mSwipeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                //如果快速滑动 onPageSelected执行完之后 onPageScrolled 可能还在执行 所以要停止它
                isStop = true;
                tabItemSelect(position);
                if(mSwipeListener!=null)
                    mSwipeListener.onPageSelected(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if(mSwipeListener!=null)
                    mSwipeListener.onPageScrollStateChanged(state);
            }
        });
    }

    /**
     * 计算当前点左右两点的坐标系
     */
    private void setItemLocation(){
        if(mItemList!=null && mItemList.size()>0){
            int[] location;
            ItemTab itemTab;
            //更行点的坐标 因为在发生位移之后座标有变化
            for (int i = 0; i < mItemList.size() ; i++) {
                itemTab = mItemList.get(i);
                location = new int[2];
                if(mCurrentPosition == i){
                    //当前放大之后座标会改变 所以加判断只有是第一次执行
                    if (itemTab != null && itemTab.getCenterX()==0) {
                        itemTab.getTv().getLocationOnScreen(location);
                        itemTab.setCenterX(location[0] + itemTab.getTv().getMeasuredWidth() / 2);;
                    }
                }else{
                    if (itemTab != null) {
                        location = new int[2];
                        itemTab.getTv().getLocationOnScreen(location);
                        itemTab.setCenterX(location[0] + itemTab.getTv().getMeasuredWidth() / 2);
                    }
                }
            }
            //找前后一个点计算中心点的坐标系
            if(mCurrentPosition == 0){
                //设置dotView的位移
                int marginLeft = mItemList.get(mCurrentPosition).getCenterX() - mDotView.getMeasuredWidth()/2;
                mDotView.setLeftMargin(marginLeft);
            }else{
                //放大的情况下 location会发生变化 这里是通过前后两点来计算
                if(mCurrentPosition + 1 < mItemList.size()) {
                    int centerX = (mItemList.get(mCurrentPosition + 1).getCenterX() + mItemList.get(mCurrentPosition - 1).getCenterX()) / 2;
                    int marginLeft = centerX - mDotView.getMeasuredWidth() / 2;
                    mDotView.setLeftMargin(marginLeft);
                    mItemList.get(mCurrentPosition).setCenterX(centerX);
                }else if(mCurrentPosition == mItemList.size()-1){
                    //最后一个
                    //设置dotView的位移
                    int marginLeft = mItemList.get(mCurrentPosition).getCenterX() - mDotView.getMeasuredWidth() / 2;
                    mDotView.setLeftMargin(marginLeft);
                }
            }
        }
    }

    /**
     * 设置fragment数据
     * @param fm
     * @param list
     * @return
     */
    public SwipeTabLayout setTabFragmentData(FragmentManager fm,List<Fragment> list){
        if(list == null) return this;
        if(mFragmentList == null){
            mFragmentList = new ArrayList<>();
        }
        mFragmentList.clear();
        mFragmentList.addAll(list);
        if(mTabContentAdapter == null){
            mTabContentAdapter = new TabContentAdapter(fm,mFragmentList);
            mViewPage.setAdapter(mTabContentAdapter);
        }
        mTabContentAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 设置是否显示底部指示点
     * @param show
     * @return
     */
    public SwipeTabLayout showTabBottomDot(boolean show){
        this.mShowDot = show;
        if(mDotView != null){
            if(mShowDot){
                mDotView.setVisibility(VISIBLE);
            }else {
                mDotView.setVisibility(GONE);
            }
        }
        return this;
    }

    /**
     * 初始化控件
     */
    private void initTopLayout() {

        mTabItemSelectedTextColor = Color.RED;
        mTabItemUnSelectedTextColor = Color.GRAY;

        //添加头部
        inflate(getContext(),R.layout.swipe_tab_item,this);
        //添加内容部
        inflate(getContext(),R.layout.swipe_content,this);

        mLinearLayout = findViewById(R.id.ll);

        mDotView = findViewById(R.id.dot);

        if(mShowDot){
            mDotView.setVisibility(VISIBLE);
        }else {
            mDotView.setVisibility(GONE);
        }

        mHorizontalScrollView = findViewById(R.id.hs);

        mIvRight = findViewById(R.id.iv_right);
        mIvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTabRightClick!=null)
                    mTabRightClick.onTabRightClick();
            }
        });

        if(isShowTabRightMenu){
            mIvRight.setVisibility(GONE);
        }else {
            mIvRight.setVisibility(VISIBLE);
        }

    }

    /**
     * 设置Tab数据
   @param data
     * @return
     */
    public SwipeTabLayout setTabItemData(List<String> data){
        if(data == null){
            return this;
        }
        mLinearLayout.removeAllViews();
        if(mItemList == null){
            mItemList = new ArrayList<>();
        }
        mItemList.clear();
        for (int i = 0; i < data.size(); i++) {
            TextView itemTab = new TextView(getContext());
            itemTab.setTextSize(mTabItemTextSize);
            itemTab.setText(data.get(i));
            itemTab.setGravity(Gravity.CENTER);
            if(mCurrentPosition == i) {
                itemTab.setTextColor(mTabItemSelectedTextColor);
            }else {
                itemTab.setTextColor(mTabItemUnSelectedTextColor);
            }
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = mTextViewMargin;
            params.rightMargin = mTextViewMargin;
            itemTab.setLayoutParams(params);
            mItemList.add(new ItemTab(itemTab,0));
            mLinearLayout.addView(itemTab);
            final int finalI = i;
            itemTab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mSwipeListener!=null){
                        mSwipeListener.onTabItemClick(finalI);
                    }
                    setTabFragmentSelect(finalI);
                }
            });
        }

        return this;
    }


    /**
     * 设置导航栏选中
     * @param position
     */
    private void tabItemSelect(int position){
        //缩小动画
        TextView tv = mItemList.get(mCurrentPosition).getTv();
        tv.setTextColor(mTabItemUnSelectedTextColor);
        if( tv.getScaleX() != 1.0f) {
            startScaleAnimation(tv, tv.getScaleX(), 1.0f);
        }

        //放大动画
        TextView tv2 = mItemList.get(position).getTv();
        tv2.setTextColor(mTabItemSelectedTextColor);
        if(tv2.getScaleX() != (1.0f+mTabTextViewScale)) {
            startScaleAnimation(tv2, tv2.getScaleX(),1.0f + mTabTextViewScale);
        }

        //计算屏幕中心点座标
        int screenCenterX = ScreenUtils.getScreenWidth(getContext()) / 2;

        int textViewCenterX = mItemList.get(position).getCenterX();

        int scrollX = textViewCenterX - screenCenterX;

        //设置滚动
        startScrollByAnimation(scrollX);

        mCurrentPosition = position;

    }

    /**
     * 设置fragment选中
     * @param position
     */
    private void setTabFragmentSelect(int position){
        if(mViewPage!=null){
            mViewPage.setCurrentItem(position,true);
        }
    }

    /**
     * 放大缩小动画
     * @param textView
     * @param start
     * @param end
     */
    private void startScaleAnimation(final TextView textView, float start, float end){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView,"scaleX",start,end);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView,"scaleY",start,end);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY); //同时执行
        animatorSet.setDuration(mDuration);
        scaleX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if(animatedValue == 1.0f){
                    int[] location1 = new int[2];
                    textView.getLocationOnScreen(location1);
                    //获取当前的view在屏幕中的中心点座标
                }
            }
        });
        animatorSet.start();
    }

    /**
     * 平移动画滑动
     * @param scrollX
     */
    private void startScrollByAnimation(int scrollX){
        ValueAnimator animator = ValueAnimator.ofInt(scrollX);
        final int[] scroll = {0};
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mHorizontalScrollView.scrollBy(animatedValue - scroll[0], 0);
                scroll[0] = animatedValue;
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isStop = false;
                setItemLocation();
            }
        });
        animator.setDuration(mDuration);
        animator.start();
    }
    
    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,i,getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mItemList!=null && mItemList.size()>0){
            for (int i = 0; i < mItemList.size(); i++) {
                //找到最大不需要滚动的下标
                if(mItemList.get(i).getTv().getLeft() >= ScreenUtils.getScreenWidth(getContext())/2){
                    mMaxPosition = i;
                    break;
                }
            }
        }

    }
    public SwipeChangeListener mSwipeListener;
    public SwipeTabLayout setSwipeChangeListener(SwipeChangeListener mSwipeListener){
        this.mSwipeListener = mSwipeListener;
        return this;
    }

    public interface SwipeChangeListener{
        void onTabItemClick(int position);
        void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }
    public TabRightOnclickListener mTabRightClick;
    public SwipeTabLayout setTabRightOnclickListener(TabRightOnclickListener mTabRightClick){
        this.mTabRightClick = mTabRightClick;
        return this;
    }
    public interface TabRightOnclickListener{
        void onTabRightClick();
    }
}
