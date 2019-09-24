package com.miaozi.miaozi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.miaozi.baselibrary.bottomTab.BottomItem;
import com.miaozi.baselibrary.bottomTab.BottomTabView;
import com.miaozi.baselibrary.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] mData;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mLv = findViewById(R.id.lv);
        mData = new String[]{"banner","swipeView","tabLayout"};
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);
        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(MainActivity.this,BannerActivity.class));
                }else if(position == 1){
                    startActivity(new Intent(MainActivity.this,SwipeViewActivity.class));
                }else if(position == 2){
                    startActivity(new Intent(MainActivity.this, SwipeTabLayoutActivity.class));
                }
            }
        });

        List<BottomItem> mTabs = new ArrayList<>();
        mTabs.add(new BottomItem("首页",R.mipmap.shouye_false,R.mipmap.shouye_true));
        mTabs.add(new BottomItem("工具",R.mipmap.gongju_false,R.mipmap.gongju_true));
        mTabs.add(new BottomItem("我的",R.mipmap.my_false,R.mipmap.my_true));

        BottomTabView mTab = findViewById(R.id.bottomTab);
        mTab.addBottomItem(mTabs)
            .setSelectedTextColor(Color.BLUE)
            .setItemOnClickListener(new BottomTabView.OnClickItemListener() {
                @Override
                public void itemClick(BottomItem item, int position) {
                    Log.d("TAG","position="+position+" item="+item);
                }
            });
    }
}
