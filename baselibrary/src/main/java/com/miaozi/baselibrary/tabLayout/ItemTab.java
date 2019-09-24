package com.miaozi.baselibrary.tabLayout;

import android.widget.TextView;

/**
 * created by panshimu
 * on 2019/9/16
 */
public class ItemTab {
    private TextView tv;
    private int centerX;

    public ItemTab(TextView tv, int centerX) {
        this.tv = tv;
        this.centerX = centerX;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }
}
