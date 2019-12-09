package com.miaozi.baselibrary.navigationBar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaozi.baselibrary.R;

/**
 * created by panshimu
 * on 2019/10/24
 * 默认导航栏
 */
public class DefaultNavigationBar  extends AbsNavigationBar<DefaultNavigationBar.Builder> {
    protected DefaultNavigationBar(DefaultNavigationBar.Builder builder) {
        super(builder);
    }
    @Override
    public void attachNavigationParams() {
        super.attachNavigationParams();
        ImageView ivBack = findViewById(R.id.iv_back);
        if(ivBack != null) {
            if (getBuilder().mLeftIconId != 0) {
                ivBack.setImageResource(getBuilder().mLeftIconId);
            }
            if (getBuilder().mLeftIconWidth != 0 && getBuilder().mLeftIconHeight != 0) {
                ViewGroup.LayoutParams layoutParams = ivBack.getLayoutParams();
                layoutParams.width = getBuilder().mLeftIconWidth;
                layoutParams.height = getBuilder().mLeftIconHeight;
                ivBack.setLayoutParams(layoutParams);
            }
        }
        TextView tvBack = findViewById(R.id.tv_back);
        if(tvBack != null && tvBack.getVisibility() == View.VISIBLE) {
            if (getBuilder().mLeftTextSize != 0) {
                tvBack.setTextSize(getBuilder().mLeftTextSize);
            }
            if (getBuilder().mLeftTextColor != 0) {
                tvBack.setTextColor(getBuilder().mLeftTextColor);
            }
        }
        TextView tvCenter = findViewById(R.id.tv_back);
        if(tvCenter != null && tvCenter.getVisibility() == View.VISIBLE) {
            if (getBuilder().mCenterTextSize != 0) {
                tvCenter.setTextSize(getBuilder().mCenterTextSize);
            }
            if (getBuilder().mCenterTextColor != 0) {
                tvCenter.setTextColor(getBuilder().mCenterTextColor);
            }
        }
        View llBack = findViewById(R.id.ll_back);
        llBack.setVisibility(getBuilder().mHideLeftView);
    }

    public static class Builder extends AbsNavigationBar.Builder<NavigationBar.Builder> {
        private int mLeftIconId;
        private int mLeftTextSize;
        private int mLeftTextColor;
        private int mCenterTextSize;
        private int mCenterTextColor;
        private int mLeftIconWidth;
        private int mLeftIconHeight;
        private int mHideLeftView = View.VISIBLE;

        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.navigation_bar, parent);
        }

        @Override
        public DefaultNavigationBar create() {
            return new DefaultNavigationBar(this);
        }

        public Builder setLeftText(CharSequence sequence){
            setText(R.id.tv_back,sequence);
            return this;
        }

        public Builder setLeftIcon(int mipmapId) {
            this.mLeftIconId = mipmapId;
            return this;
        }

        public Builder setCenterTitle(CharSequence sequence){
            setText(R.id.tv_title,sequence);
            return this;
        }
        public Builder setLeftOnClickListener(View.OnClickListener clickListener){
            setOnClick(R.id.ll_back,clickListener);
            return this;
        }
        public Builder setLeftTextSize(int size){
            this.mLeftTextSize = size;
            return this;
        }
        public Builder setLeftTextColor(int color){
            this.mLeftTextColor = color;
            return this;
        }
        public Builder setCenterTextSize(int size){
            this.mCenterTextSize = size;
            return this;
        }
        public Builder setCenterTextColor(int color){
            this.mCenterTextColor = color;
            return this;
        }
        public Builder setLeftIconSize(int width,int height){
            this.mLeftIconWidth = width;
            this.mLeftIconHeight = height;
            return this;
        }
        public Builder hideLeftView(){
            this.mHideLeftView = View.GONE;
            return this;
        }
    }
}