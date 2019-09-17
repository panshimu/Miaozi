package com.miaozi.miaozi;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.miaozi.baselibrary.views.SwipeView;
import com.miaozi.baselibrary.views.SwipeView2;

/**
 * created by panshimu
 * on 2019/9/10
 */
public class SwipeViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_view);
        SwipeView2 swipeView = findViewById(R.id.sp);
        swipeView.setContentViewOnclickListener(new SwipeView2.ContentViewOnclickListener() {
            @Override
            public void contentViewOnclick() {
                Log.d("TAG","9090");
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","bt_1");
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","bt_2");
            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","bt_3");
            }
        });
        findViewById(R.id.bt_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","bt_4");
            }
        });

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","tv");
            }
        });


        findViewById(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","content");
            }
        });
        findViewById(R.id.tv_fx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","tv_fx");
            }
        });
        findViewById(R.id.tv_cc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","tv_cc");
            }
        });

    }
}
