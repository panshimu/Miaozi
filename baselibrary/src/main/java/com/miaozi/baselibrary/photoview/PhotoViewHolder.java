package com.miaozi.baselibrary.photoview;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * created by panshimu
 * on 2019/12/10
 */
public class PhotoViewHolder implements View.OnTouchListener, View.OnLayoutChangeListener {
    private ImageView mImageView;
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    private GestureDetector mGestureDetector;
    private CustomGestureDetector mCustomGestureDetector;
    private static float DEFAULT_MAX_SCALE = 3.0f;
    private static float DEFAULT_MID_SCALE = 1.75f;
    private static int DEFAULT_ZOOM_DURATION = 200;
    private static float DEFAULT_MIN_SCALE = 1.0f;
    private float mMaxScale = DEFAULT_MAX_SCALE;
    private float mMidScale = DEFAULT_MID_SCALE;
    private float mMinScale = DEFAULT_MIN_SCALE;
    private int mZoomDuration = DEFAULT_ZOOM_DURATION;
    private final Matrix mSuppMatrix = new Matrix();
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private final RectF mDisplayRect = new RectF();
    private final float[] mMatrixValues = new float[9];
    private boolean mZoomEnabled = true;
    private float mBaseRotation;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private OnGestureListener mOnGestureListener = new OnGestureListener() {
        @Override
        public void onDrag(float dx, float dy) {

        }

        @Override
        public void onFling(float startX, float startY, float velocityX, float velocityY) {

        }

        @Override
        public void onScale(float scaleFactor, float focusX, float focusY) {
            Log.e("TAG","onScale->" +
                    " scaleFactor="+scaleFactor +
                    " focusX="+focusX +
                    " focusY=" + focusY
            );
            if(getScale() < mMaxScale || scaleFactor < 1f){
                mSuppMatrix.postScale(scaleFactor,scaleFactor,focusX,focusY);
                checkAndDisplayMatrix();
            }
        }
    };
    public PhotoViewHolder(ImageView imageView) {
        this.mImageView = imageView;
        imageView.setOnTouchListener(this);
        imageView.addOnLayoutChangeListener(this);
        mBaseRotation = 0.0f;
        mCustomGestureDetector = new CustomGestureDetector(imageView.getContext(),mOnGestureListener);
        mGestureDetector = new GestureDetector(imageView.getContext(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                try {
                    float scale = getScale();
                    float x = e.getX();
                    float y = e.getY();
                    if(scale < getMediumScale()){
                        setScale(getMediumScale(),x,y,true);
                    } else if (scale >= getMediumScale() && scale < getMaximumScale()) {
                        setScale(getMaximumScale(), x, y, true);
                    } else {
                        setScale(getMinimumScale(), x, y, true);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
    }

    private float getMinimumScale() {
        return mMinScale;
    }

    private float getMaximumScale() {
        return mMaxScale;
    }

    private void setScale(float scale, float focusX, float focusY, boolean animate) {
        //小于最小 或大于最大
        if(scale < mMidScale || scale > mMaxScale){
            throw new IllegalArgumentException("Scale must be within the range of minScale and maxScale");
        }
        if(animate){
            mImageView.post(new AnimatedZoomRunnable(getScale(),scale,focusX,focusY));
        }
    }

    public float getMediumScale() {
        return mMidScale;
    }
    public float getScale() {
        //Math.pow(x,y)返回 x 的 y 次幂的值
        //y = Math.sqrt(x)返回一个数的平方根 如 y的平方 = x
        //Matrix.MSCALE_X x 抽的缩放比例  Matrix.MSKEW_Y y抽旋转角度
        float x = (float)Math.pow(getValue(mSuppMatrix, Matrix.MSCALE_X), 2);
        float y = (float)Math.pow(getValue(mSuppMatrix, Matrix.MSKEW_Y), 2);

        return (float) Math.sqrt( x + y);
    }
    private float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(mMatrixValues);
        return mMatrixValues[whichValue];
    }
    public ScaleType getScaleType(){
        return mScaleType;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean handled = false;
        if(mZoomEnabled && Util.hasDrawable((ImageView)v)){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    ViewParent parent = v.getParent();
                    if(parent != null){
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(getScale() < mMinScale){
                        RectF rectF = getDisplayRectF();
                        if(rectF != null){
                            v.post(new AnimatedZoomRunnable(getScale(),mMinScale,rectF.centerX(),rectF.centerY()));
                            handled = true;
                        }
                    }else if(getScale() > mMaxScale){
                        RectF rectF = getDisplayRectF();
                        if(rectF != null){
                            v.post(new AnimatedZoomRunnable(getScale(),mMaxScale,rectF.centerX(),rectF.centerY()));
                        }
                    }
                    break;
            }
            if(mCustomGestureDetector != null){
                handled = mCustomGestureDetector.onTouchEvent(event);
            }
            if(mGestureDetector != null && mGestureDetector.onTouchEvent(event)){
                handled = true;
            }
        }
        return handled;
    }

    private RectF getDisplayRectF() {
        return getDisplayRect(getDrawMatrix());
    }
    private RectF getDisplayRect(Matrix matrix){
        Drawable drawable = mImageView.getDrawable();
        if(drawable != null){
            mDisplayRect.set(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            matrix.mapRect(mDisplayRect);
            return mDisplayRect;
        }
        return null;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

    }

    public void setScaleType(ScaleType scaleType) {
        if(Util.isSupportedScaleType(scaleType) && scaleType != mScaleType) {
            this.mScaleType = scaleType;
        }
    }

    public void update() {
        if(mZoomEnabled){
            updateBaseMatrix();
        }
    }

    private void updateBaseMatrix() {
        if(mImageView == null) {
            return;
        }
        Drawable drawable = mImageView.getDrawable();
        if(drawable == null) {
            return;
        }
        //imageView控件宽高
        final int viewWidth = getImageViewWidth();
        final int viewHeight = getImageViewHeight();
        //图片宽和高
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();

        //经过测试发现基本都是： viewWidth == drawableWidth viewHeight == drawableHeight
        mBaseMatrix.reset();
        //计算控件和图片大小比列
        final float widthScale = viewWidth / drawableWidth;
        final float heightScale = viewHeight / drawableHeight;

        float centerX = (viewWidth - drawableWidth) / 2f;
        float centerY = (viewHeight - drawableHeight) / 2f;

        if(mScaleType == ScaleType.CENTER){//按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示
            //把图片平移到中心点
//            mBaseMatrix.setTranslate(centerX,centerY);
//            mBaseMatrix.postTranslate(
//                    (viewWidth - drawableWidth) / 2f ,
//                    (viewHeight - drawableHeight) / 2f );
        }else if(mScaleType == ScaleType.CENTER_CROP){//按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)
            //先把图片放大或缩小
//            float scale = Math.max(widthScale,heightScale);
//            mBaseMatrix.setScale(scale,scale);
//            mBaseMatrix.setTranslate((viewWidth - drawableWidth * scale) / 2F,
//                    (viewHeight - drawableHeight * scale) / 2F);
        }else if(mScaleType == ScaleType.CENTER_INSIDE){

        }else {
            RectF mTempSrc = new RectF(0, 0, drawableWidth, drawableHeight);
            RectF mTempDst = new RectF(0, 0, viewWidth, viewHeight);
            if ((int) mBaseRotation % 180 != 0) {
                mTempSrc = new RectF(0, 0, drawableHeight, drawableWidth);
            }
            switch (mScaleType) {
                case FIT_CENTER:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.CENTER);
                    break;
                case FIT_START:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.START);
                    break;
                case FIT_END:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.END);
                    break;
                case FIT_XY:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.FILL);
                    break;
                default:
                    break;
            }
        }
        resetMatrix();
    }

    private void resetMatrix() {
        mSuppMatrix.reset();
        setImageViewMatrix(mBaseMatrix);
//        setRotationBy(mBaseRotation);
//        setImageViewMatrix(getDrawMatrix());
//        checkMatrixBounds();
    }

    /**
     * 检查矩阵边缘
     */
    private boolean checkMatrixBounds() {
        return true;
    }

    private Matrix getDrawMatrix() {
        //mDrawMatrix = mBaseMatrix
        mDrawMatrix.set(mBaseMatrix);
        //mDrawMatrix = mSuppMatrix * mDrawMatrix
        mDrawMatrix.postConcat(mSuppMatrix);
        return mDrawMatrix;
    }

    private void setImageViewMatrix(Matrix matrix) {
        mImageView.setImageMatrix(matrix);
    }


    private void setRotationBy(float rotation) {
        mSuppMatrix.postRotate(rotation % 360);
        checkAndDisplayMatrix();
    }

    private void checkAndDisplayMatrix() {
        if(checkMatrixBounds()){
            setImageViewMatrix(getDrawMatrix());
        }
    }

    private int getImageViewWidth() {
        return mImageView.getWidth() - mImageView.getPaddingLeft() - mImageView.getPaddingRight();
    }
    private int getImageViewHeight(){
        return mImageView.getHeight() - mImageView.getPaddingTop() - mImageView.getPaddingBottom();
    }

    private class AnimatedZoomRunnable implements Runnable {
        private final float mFocusX;
        private final float mFocusY;
        private final long mStartTime;
        private final float mZoomStart;
        private final float mZoomEnd;
        public AnimatedZoomRunnable(float currentZoom, float targetZoom, float focusX, float focusY) {
            this.mZoomStart = currentZoom;
            this.mZoomEnd = targetZoom;
            this.mFocusX = focusX;
            this.mFocusY = focusY;
            this.mStartTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            float t = interpolator();
            float scale = mZoomStart + t * (mZoomEnd - mZoomStart);
            float deltaScale = scale / getScale();
            mOnGestureListener.onScale(deltaScale,mFocusX,mFocusY);
            if( t < 1f){
                Compat.postAnimation(mImageView,this);
            }
        }

        private float interpolator() {
            float t = 1f * (System.currentTimeMillis() - mStartTime) / mZoomDuration;
            t = Math.min(1f,t);
            t = mInterpolator.getInterpolation(t);
            return t;
        }
    }
}
