package com.miaozi.miaozi;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
        mSwipe.setTabItemData(mItems);
        mSwipe.setTabFragmentData(getSupportFragmentManager(),mFragment);
//        mSwipe.setTabItemTextColor(ContextCompat.getColor(this,R.color.colorAccent),ContextCompat.getColor(this,R.color.colorPrimaryDark));
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
        mItems.add("科普");
        mItems.add("小视频");
        mItems.add("科技");
        mItems.add("财经");
        mItems.add("NBA");
        mItems.add("问答");
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
