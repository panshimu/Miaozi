package com.miaozi.baselibrary.banner;

import android.view.View;

/**
 * created by panshimu
 * on 2019/9/4
 */
public abstract class BannerAdapter{
    //根据位置获取viewPager的view
    public abstract View getView(View contentView,int position);
    //获取轮播数量
    public abstract int getCount();
}
