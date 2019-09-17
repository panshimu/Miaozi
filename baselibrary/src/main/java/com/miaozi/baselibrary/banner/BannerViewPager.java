package com.miaozi.baselibrary.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * created by panshimu
 * on 2019/9/4
 */
public class BannerViewPager extends ViewPager {
    private List<View> mContentViews;
    private static int MSG_SCROLL = 0x0001;
    private int mDisplayTime = 3000;
    //自己的adapter
    private BannerAdapter mAdapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MSG_SCROLL){
                setCurrentItem(getCurrentItem()+1);
                startScroll();
            }
        }
    };
    //自定义页面切换速率控制器
    private BannerScroller mBannerScroller;

    public BannerViewPager(@NonNull Context context) {
        this(context,null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContentViews = new ArrayList<>();
        //改变viewPager切换速率 duration 持续时间 它是局部变量 无法效果
        // 通过反射得到 mScroll
        try {
            Field mScroll = ViewPager.class.getDeclaredField("mScroller");
            //设置参数 第一个参数表示当前属性在哪个类 第二个参数代表 必要设置的值 也就是自己的 BannerScroller
            mBannerScroller = new BannerScroller(context);
            //强制改变私有制
            mScroll.setAccessible(true);
            mScroll.set(this,mBannerScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * 设置切换页面持续时间
     * @param duration
     */
    public void setScrollDuration(int duration){
        mBannerScroller.setScrollDuration(duration);
    }
    public void setDisplayTime(int time){
        this.mDisplayTime = time;
    }

    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        //这是系统的adapter
        setAdapter(new BannerPagerAdapter());

    }

    public void startScroll(){
        mHandler.removeMessages(MSG_SCROLL);
        mHandler.sendEmptyMessageDelayed(MSG_SCROLL,mDisplayTime);
    }

    /**
     * activity销毁回调
     */
    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeMessages(MSG_SCROLL);
        mHandler = null;
        super.onDetachedFromWindow();
    }

    /**
     * view的复用
     * 用list的原因是防止 报错
     * java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
     * @return 一般会比 count 多一个
     */
    private View getContentView(){
        for (int i = 0; i < mContentViews.size(); i++) {
            //判断是否已经被销毁
            if(mContentViews.get(i).getParent() == null){
                return mContentViews.get(i);
            }
        }
        return null;
    }
    public OnBannerClickListener mBannerClick;
    public void setOnBannerClickListener(OnBannerClickListener mBannerClick) {
        this.mBannerClick = mBannerClick;
    }

    public interface OnBannerClickListener{
        void onBannerClick(int position);
    }

    private class BannerPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
        @NonNull
        @Override //创建
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            //position 是 0 -> Integer.MAX_VALUE
            final View containView = mAdapter.getView(getContentView(),position % mAdapter.getCount());
            container.addView(containView);
            containView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mBannerClick != null)
                        mBannerClick.onBannerClick(position % mAdapter.getCount());
                }
            });
            return containView;
        }
        @Override //销毁
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            //防止重复添加
            if(!mContentViews.contains(object)) {
                mContentViews.add((View) object);
            }
        }
    }
}
