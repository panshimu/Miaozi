package com.miaozi.miaozi;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.miaozi.baselibrary.selectedLayout.SelectedLinearLayout;

/**
 * created by panshimu
 * on 2019/12/9
 */
public class SelectedLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_layout_activity);

        SelectedLinearLayout mSelectedLayout =  findViewById(R.id.ll);
        mSelectedLayout.setItemData(new String[]{"关注","粉丝","礼物"});
        //设置一些属性
        mSelectedLayout.setSelectedPosition(1);
        mSelectedLayout.setBorderWidth(5);
        mSelectedLayout.setTextSize(20);
        mSelectedLayout.setBorderColor(ContextCompat.getColor(this,R.color.border));
        mSelectedLayout.setDivideLineColor(ContextCompat.getColor(this,R.color.border));
        mSelectedLayout.setDivideLineWidth(5);
        mSelectedLayout.setSelectedBackgroundColor(ContextCompat.getColor(this,R.color.border));
        mSelectedLayout.setOnItemOnclickListener(new SelectedLinearLayout.OnItemOnclickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(SelectedLayoutActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
