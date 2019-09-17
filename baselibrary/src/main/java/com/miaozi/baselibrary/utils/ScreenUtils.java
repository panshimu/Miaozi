package com.miaozi.baselibrary.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * created by panshimu
 * on 2019/9/16
 */
public class ScreenUtils {
    public static int dp2px(Context context, int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,i,context.getResources().getDisplayMetrics());
    }
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
