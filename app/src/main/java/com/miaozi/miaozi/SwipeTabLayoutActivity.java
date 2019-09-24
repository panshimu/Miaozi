package com.miaozi.miaozi;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.miaozi.baselibrary.tabLayout.SwipeTabLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * created by panshimu
 * on 2019/9/15
 */
public class SwipeTabLayoutActivity extends AppCompatActivity {

    private List<String> mItems;
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_tab_layout);
        SwipeTabLayout mSwipe = findViewById(R.id.swipe);
        initData();
        mSwipe.setTabItemData(mItems)
                .setTabItemTextSize(20)
                .setTabFragmentData(getSupportFragmentManager(),mFragment)
                .setTabItemTextColor(Color.GRAY,Color.RED)
                .showTabBottomDot(false)
                .setSwipeChangeListener(new SwipeTabLayout.SwipeChangeListener() {
                    @Override
                    public void onTabItemClick(int position) {
                        Log.d("TAG","onTabItemClick="+position);
                    }

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                })
                .showTabRightMenu(true)
                .setTabRightOnclickListener(new SwipeTabLayout.TabRightOnclickListener() {
                    @Override
                    public void onTabRightClick() {
                        Log.d("TAG","onTabRightClick");
                    }
                });
    }

    private void initData() {
        mItems = new ArrayList<>();
        mItems.add("关注");
        mItems.add("推荐");
        mItems.add("视频");
        mItems.add("体育");
        mItems.add("热点");
        mItems.add("游戏");
        mItems.add("娱乐");
        mItems.add("真人");
        mItems.add("视频");
        mItems.add("科技");
        mItems.add("财经");
        mItems.add("NBA");
        mItems.add("知识");
        mItems.add("彩票");
        mItems.add("数码");
        mItems.add("军事");
        mItems.add("情感");

        mFragment = new ArrayList<>();
        for (int i = 0; i < mItems.size(); i++) {
            MyFragment myFragment = new MyFragment(mItems.get(i));
            mFragment.add(myFragment);
        }
    }

}
