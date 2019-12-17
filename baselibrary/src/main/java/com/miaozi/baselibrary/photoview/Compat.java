package com.miaozi.baselibrary.photoview;

import android.os.Build;
import android.view.View;

/**
 * created by panshimu
 * on 2019/12/11
 */
public class Compat {
    public static final int SIXTY_FPS_INTERVAL = 1000 / 60;
    public static void postAnimation(View view,Runnable runnable){
        //大于16
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            view.postOnAnimation(runnable);
        }else {
            view.postDelayed(runnable,SIXTY_FPS_INTERVAL);
        }
    }
}
