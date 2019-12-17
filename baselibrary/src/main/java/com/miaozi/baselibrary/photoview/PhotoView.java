package com.miaozi.baselibrary.photoview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * created by panshimu
 * on 2019/12/10
 */
public class PhotoView extends AppCompatImageView {
    private PhotoViewHolder mHolder;
    private ScaleType mScaleType;
    public PhotoView(Context context) {
        this(context,null);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHolder = new PhotoViewHolder(this);
        super.setScaleType(ScaleType.MATRIX);
        if(mScaleType != null){
            setScaleType(mScaleType);
            mScaleType = null;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return mHolder.getScaleType();
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if(mHolder == null){
            mScaleType = scaleType;
        }else {
            mHolder.setScaleType(scaleType);
        }
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        if(mHolder != null)
            mHolder.update();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if(mHolder != null)
            mHolder.update();
    }
}
