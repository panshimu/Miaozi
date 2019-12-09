package com.miaozi.baselibrary.navigationBar;

import android.view.View;
import android.view.ViewGroup;

/**
 * created by panshimu
 * on 2019/10/24
 */
public interface INavigation {
    /**
     * 创建NavigationBar
     */
    void createNavigationBar();
    /**
     * 把NavigationBar 添加到父布局
     * @param view
     * @param parent
     */
    void attachParent(View view, ViewGroup parent);
    /**
     * 添加设置 NavigationBar 的参数
     */
    void attachNavigationParams();
}
