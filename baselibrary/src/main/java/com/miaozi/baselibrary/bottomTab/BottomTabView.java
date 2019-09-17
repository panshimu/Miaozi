package com.miaozi.baselibrary.bottomTab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaozi.baselibrary.banner.BannerView;

import java.util.List;

/**
 * created by panshimu
 * on 2019/9/2
 */
public class BottomTabView extends LinearLayout {
    private List<BottomItem> mBottomItemList;
    private int mUnselectedTextColor = Color.GRAY;
    private int mSelectedTextColor = Color.BLACK;
    private int mCurrentPosition = 0;
    private int mTextSize = 15;
    private int mBackGroupColor = Color.WHITE;
    private int mImageViewWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mMargin = 10;
    private int mTopLineColor = Color.GRAY;
    private int mTopLineWidth = 1;
    private Paint mPaint;
    private int mImageViewHeight;

    public BottomTabView(Context context) {
        this(context,null);
    }

    public BottomTabView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dip2px(mTopLineWidth));
        mPaint.setColor(mTopLineColor);
        mPaint.setDither(true);
        mMargin = dip2px(mMargin);
        mTopLineWidth = dip2px(mTopLineWidth);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawLine(0,0,getMeasuredWidth(),0,mPaint);
    }

    private void initLayout() {
        if(mBottomItemList == null || mBottomItemList.size() == 0)
            return;
        removeAllViews();
        for (int i = 0 ; i < mBottomItemList.size() ; i++ ){
            initItem(i,mBottomItemList.get(i));
        }
    }

    public BottomTabView setTopLineWidth(int width){
        this.mTopLineWidth = dip2px(width);
        mPaint.setStrokeWidth(mTopLineWidth);
        invalidate();
        return this;
    }

    public BottomTabView setTopLineColor(int color){
        this.mTopLineColor = color;
        mPaint.setColor(mTopLineColor);
        invalidate();
        return this;
    }

    public BottomTabView setUnselectedTextColor(int color){
         this.mUnselectedTextColor = color;
         for (int i = 0; i < mBottomItemList.size(); i++) {
             if(i != mCurrentPosition){
                 mBottomItemList.get(i).getTextView().setTextColor(color);
             }
         }
         return this;
    }

    public BottomTabView setSelectedTextColor(int color){
        this.mSelectedTextColor = color;
        if(mBottomItemList.size() > 0 ){
            mBottomItemList.get(mCurrentPosition).getTextView().setTextColor(color);
        }
        return this;
    }

    public BottomTabView setTextSize(int size){
        this.mTextSize = size;
        for(BottomItem item : mBottomItemList){
            item.getTextView().setTextSize(size);
        }
        return this;
    }

    public BottomTabView setImageViewSize(int width,int height){
        this.mImageViewWidth = dp2px(width);
        this.mImageViewHeight = dip2px(height);
        for(BottomItem item : mBottomItemList){
            ViewGroup.LayoutParams lp = item.getImageView().getLayoutParams();
            lp.width = mImageViewWidth;
            lp.height = mImageViewHeight;
            item.getImageView().setLayoutParams(lp);
        }
        return this;
    }

    public BottomTabView setBackGroupColor(int color){
        this.mBackGroupColor = color;
        for(BottomItem item : mBottomItemList){
            item.getContentView().setBackgroundColor(color);
        }
        return this;
    }

    private void initItem(final int position, BottomItem item){

        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(VERTICAL);
        ll.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        ll.setLayoutParams(params);

        TextView tv = new TextView(getContext());
        LayoutParams lpTv = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpTv.bottomMargin = mMargin;
        tv.setLayoutParams(lpTv);
        tv.setText(item.getText());
        if(mCurrentPosition == position){
            tv.setTextColor(mSelectedTextColor);
        }else {
            tv.setTextColor(mUnselectedTextColor);
        }
        tv.setTextSize(mTextSize);

        ImageView iv = new ImageView(getContext());
        LayoutParams lpIv = new LayoutParams(mImageViewWidth,mImageViewWidth);
        lpIv.topMargin = mMargin;
        iv.setLayoutParams(lpIv);
        if(mCurrentPosition == position){
            iv.setImageResource(item.getSelectedResId());
        }else {
            iv.setImageResource(item.getUnselectedResId());
        }

        item.setTextView(tv);
        item.setImageView(iv);

        ll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectPosition(position);
            }
        });

        item.setContentView(ll);

        ll.addView(iv);
        ll.addView(tv);

        ll.setBackgroundColor(mBackGroupColor);

        addView(ll);

    }

    private int dip2px(int mMargin) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mMargin,getContext().getResources().getDisplayMetrics());
    }

    public BottomTabView setSelectPosition(int position){

        if( position < 0 || position > mBottomItemList.size() || position == mCurrentPosition){
            return this;
        }

        //恢复当前
        BottomItem bottomItem = mBottomItemList.get(mCurrentPosition);
        bottomItem.getImageView().setImageResource(mBottomItemList.get(mCurrentPosition).getUnselectedResId());
        bottomItem.getTextView().setTextColor(mUnselectedTextColor);

        //设置选中
        BottomItem bi = mBottomItemList.get(position);
        bi.getImageView().setImageResource(mBottomItemList.get(position).getSelectedResId());
        bi.getTextView().setTextColor(mSelectedTextColor);

        mCurrentPosition = position;

        if(mItemClick != null){
            mItemClick.itemClick(bi,position);
        }

        return this;

    }

    private int dp2px(int mImageViewWidth) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mImageViewWidth,getContext().getResources().getDisplayMetrics());
    }

    public BottomTabView addBottomItem(List<BottomItem> list){
        if(mBottomItemList != null){
           mBottomItemList.clear();
        }
        mBottomItemList = list;
        initLayout();
        return this;
    }
    private OnClickItemListener mItemClick;
    public BottomTabView setItemOnClickListener(OnClickItemListener clickListener){
        this.mItemClick = clickListener;
        return this;
    }
    public interface OnClickItemListener{
        void itemClick(BottomItem item, int position);
    }
}
