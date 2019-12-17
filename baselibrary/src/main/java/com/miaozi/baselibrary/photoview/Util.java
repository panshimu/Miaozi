package com.miaozi.baselibrary.photoview;

import android.widget.ImageView;

/**
 * created by panshimu
 * on 2019/12/11
 */
public class Util {
    public static boolean isSupportedScaleType(ImageView.ScaleType scaleType){
        if(scaleType == null){
            return false;
        }
        switch (scaleType){
            case MATRIX:
                throw new IllegalStateException("Matrix scale type is not supported");
        }
        return true;
    }

    public static boolean hasDrawable(ImageView v) {
        return v.getDrawable() != null;
    }
}
