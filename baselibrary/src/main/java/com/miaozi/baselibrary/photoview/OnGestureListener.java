package com.miaozi.baselibrary.photoview;

/**
 * created by panshimu
 * on 2019/12/10
 */
public interface OnGestureListener {
    //拖拽
    void onDrag(float dx,float dy);
    //惯性移动
    void onFling(float startX,float startY,float velocityX,float velocityY);
    //缩放
    void onScale(float scaleFactor,float focusX,float focusY);
}
