package com.miaozi.miaozi;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.miaozi.baselibrary.navigationBar.DefaultNavigationBar;
import com.miaozi.baselibrary.navigationBar.NavigationBar;

/**
 * created by panshimu
 * on 2019/10/24
 */
public class NavigationBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_bar_activity);
        ViewGroup parent = findViewById(R.id.container);
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this,parent)
                .setCenterTitle("标题miaozi")
                .setLeftText(null)
                .setLeftIconSize(30,30)
                .setLeftOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .create();
    }
}
