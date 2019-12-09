package com.miaozi.baselibrary.navigationBar;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * created by panshimu
 * on 2019/10/24
 * 导航栏基类
 */
public class AbsNavigationBar<T extends AbsNavigationBar.Builder> implements INavigation{
    private T mBuilder;
    private View mNavigationBar;
    protected AbsNavigationBar(T builder){
        this.mBuilder = builder;
        createNavigationBar();
    }
    @Override
    public void createNavigationBar() {
        mNavigationBar = LayoutInflater.from(mBuilder.mContext)
                .inflate(mBuilder.mLayoutId,mBuilder.mParent,false);
        //添加到父容器
        attachParent(mNavigationBar,mBuilder.mParent);
        //设置参数
        attachNavigationParams();
    }
    @Override
    public void attachParent(View view, ViewGroup parent) {
        parent.addView(view,0);
    }

    /**
     * 绑定参数
     */
    @Override
    public void attachNavigationParams() {
        //设置文本
        Map<Integer,CharSequence>  textMaps = mBuilder.mTextMaps;
        for (Map.Entry<Integer, CharSequence> entry : textMaps.entrySet()) {
            TextView textView = findViewById(entry.getKey());
            if(textView != null) {
                if (TextUtils.isEmpty(entry.getValue())) {
                    textView.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(entry.getValue());
                }
            }
        }
        //设置点击事件
        Map<Integer,View.OnClickListener>  clickMaps = mBuilder.mClickMaps;
        for (Map.Entry<Integer, View.OnClickListener> entry : clickMaps.entrySet()) {
            View view = findViewById(entry.getKey());
            if(view != null) {
                view.setOnClickListener(entry.getValue());
            }
        }
    }

    public <T extends View> T findViewById(int viewId){
        return (T)mNavigationBar.findViewById(viewId);
    }

    /**
     * 返回Builder
     * @return
     */
    public T getBuilder(){
        return mBuilder;
    }
    /**
     *  Builder构建类
     * 构建NavigationBar 并且设置参数
     * 使用Builder泛型的原因是因为 公共参数设置在这里了 外部调用的时候会报错
     */
    public abstract static class Builder<Bar extends Builder>{
        public Context mContext;
        public int mLayoutId;
        public ViewGroup mParent;
        public Map<Integer,CharSequence> mTextMaps;
        public Map<Integer,View.OnClickListener> mClickMaps;

        public Builder(Context context, int layoutId, ViewGroup parent) {
            this.mContext = context;
            this.mLayoutId = layoutId;
            this.mParent = parent;
            this.mTextMaps = new HashMap<>();
            this.mClickMaps = new HashMap<>();
        }

        /**
         * 用来构建NavigationBar
         * @return
         */
        public abstract AbsNavigationBar create();

        /**
         * 设置文本
         * @param viewId
         * @param text
         * @return
         */
        public Bar setText(int viewId,CharSequence text){
            mTextMaps.put(viewId,text);
            return (Bar) this;
        }

        /**
         * 设置点击事件
         * @param viewId
         * @param onClickListener
         * @return
         */
        public Bar setOnClick(int viewId, View.OnClickListener onClickListener){
            mClickMaps.put(viewId,onClickListener);
            return (Bar) this;
        }
    }
}
