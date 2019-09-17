package com.miaozi.baselibrary.tabLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
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
    private int mTopLayoutHeight;
    private int mTabItemSelectedTextColor;
    private int mTabItemUnSelectedTextColor;
    private int mTabItemTextSize = 15;
    private int mTopLayoutBackgroundColor = Color.GREEN;
    private DotView mDotView;
    private int mCurrentPosition;
    private List<ItemTab> mItemData;
    private List<TabItemTextView> mItemList;
    private SwipeAdapter mSwipeAdapter;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPage;
    private TabContentAdapter mTabContentAdapter;
    private List<Fragment> mFragmentList;
    private boolean mShowDot;
    private HorizontalScrollView mHorizontalScrollView;
    private TabItemTextView mTabItemTextView;

    public SwipeTabLayout(Context context) {
        this(context,null);
    }

    public SwipeTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mItemData = new ArrayList<>();
        mCurrentPosition = 0;
        initTopLayout();
        initContentLayout();
    }
    public SwipeTabLayout setTabItemTextColor(int unselectedColor,int selectedColor){
        this.mTabItemUnSelectedTextColor = unselectedColor;
        this.mTabItemSelectedTextColor = selectedColor;
        if(mItemList!=null && mItemList.size()>0){
            for (int i = 0; i < mItemList.size(); i++) {
                if(mCurrentPosition == i) {
                    mItemList.get(i).setTextColor(mTabItemSelectedTextColor);
                }else {
                    mItemList.get(i).setTextColor(mTabItemUnSelectedTextColor);
                }
            }
        }
        return this;
    }

    private void initContentLayout() {
        mViewPage = findViewById(R.id.view_page);
    }
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
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("TAG", "position=" + position + " positionOffset=" + positionOffset + " positionOffsetPixels=" + positionOffsetPixels);
                if (mCurrentPosition == position) {
                    mItemList.get(mCurrentPosition).setTextColor(ColorUtil.gradualChanged(positionOffset, mTabItemSelectedTextColor, mTabItemUnSelectedTextColor));
                    if (position+1 < mItemList.size()) {
                        mItemList.get(position+1).setTextColor(ColorUtil.gradualChanged(positionOffset, mTabItemUnSelectedTextColor, mTabItemSelectedTextColor));
                    }
                }
            }
            @Override
            public void onPageSelected(int position) {
                tabItemSelect(position);

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return this;
    }
    private SwipeTabLayout showDot(boolean show){
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

    private void initTopLayout() {

        mTopLayoutHeight = dp2px(100);
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

        mTabItemTextView = findViewById(R.id.tv1);
        mTabItemTextView.setTextSize(50);
        mTabItemTextView.setText("我是苗子");
        mTabItemTextView.setTextColor(mTabItemUnSelectedTextColor);

    }

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
            TabItemTextView itemTab = new TabItemTextView(getContext());
            itemTab.setTextSize(mTabItemTextSize);
            itemTab.setText(data.get(i));
            if(mCurrentPosition == i) {
                itemTab.setTextColor(mTabItemSelectedTextColor);
                startScaleAnimation(itemTab,1.0f,1.5f);
            }else {
                itemTab.setTextColor(mTabItemUnSelectedTextColor);
            }
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 20;
            params.rightMargin = 20;
            itemTab.setLayoutParams(params);
            mItemList.add(itemTab);
            mLinearLayout.addView(itemTab);
            final int finalI = i;
            itemTab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    tabItemSelect(finalI);
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

        if(position == mCurrentPosition) return;

        //缩小动画
        TabItemTextView tv = mItemList.get(mCurrentPosition);
        tv.setTextColor(mTabItemUnSelectedTextColor);
        startScaleAnimation(tv,1.5f,1.0f);

        //放大动画
        TabItemTextView tv2 = mItemList.get(position);
        tv2.setTextColor(mTabItemSelectedTextColor);
        startScaleAnimation(tv2,1.0f,1.5f);

        //计算屏幕中心点座标
        int screenCenterX = ScreenUtils.getScreenWidth(getContext()) / 2;
        TabItemTextView TabItemTextView = mItemList.get(position);
        int[] location = new int[2];
        TabItemTextView.getLocationOnScreen(location);
        //获取当前的view在屏幕中的中心点座标
        int TabItemTextViewCenterX = location[0] + TabItemTextView.getMeasuredWidth()/2;
        //设置滚动
        mHorizontalScrollView.scrollBy(TabItemTextViewCenterX-screenCenterX, 0);
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
     * @param TabItemTextView
     * @param start
     * @param end
     */
    private void startScaleAnimation(TabItemTextView TabItemTextView,float start,float end){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(TabItemTextView,"scaleX",start, end);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(TabItemTextView,"scaleY",start, end);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);  //同时执行
        animatorSet.setDuration(350);
        animatorSet.start();
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,i,getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(mItemList!=null && mItemList.size()>0){
            TabItemTextView TabItemTextView = mItemList.get(0);
            int right = TabItemTextView.getRight();
            Log.d("TAG","right="+right);
            if(right > 0 && mDotView!=null){
                int s = right/2;
                LayoutParams params = new LayoutParams(right / 2, right / 5);
                params.bottomMargin = 20;
                params.leftMargin = s;
                mDotView.setLayoutParams(params);
            }
        }
    }
}
