package com.miaozi.baselibrary.navigationBar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * created by panshimu
 * on 2019/10/24
 * 可以直接使用的导航栏
 */
public class NavigationBar extends AbsNavigationBar{
    protected NavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbsNavigationBar.Builder<NavigationBar.Builder>{
        public Builder(Context context, int layoutId, ViewGroup parent) {
            super(context, layoutId, parent);
        }
        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }
}
