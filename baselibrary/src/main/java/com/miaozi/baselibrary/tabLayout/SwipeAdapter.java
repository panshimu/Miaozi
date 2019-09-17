package com.miaozi.baselibrary.tabLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaozi.baselibrary.R;

import java.util.List;

/**
 * created by panshimu
 * on 2019/9/16
 */
public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.MyViewHolder> {
    private List<ItemTab> mData;
    private int mCurrentPosition = 0;
    private int mLastPosition = -1;
    private float mScale = 1.3f;

    public SwipeAdapter(List<ItemTab> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item_tv,parent,false);
        return new MyViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ItemTab itemTab = mData.get(position);
        holder.mTextView.setText(itemTab.getText());
        holder.mTextView.setTextSize(itemTab.getTextSize());
        if(itemTab.isSelected()){
            holder.mTextView.setTextColor(itemTab.getSelectedColor());
        }else {
            holder.mTextView.setTextColor(itemTab.getUnSelectedColor());
        }

        //放大
        if(mCurrentPosition == position){
            startAnimation(holder.mTextView,1.0f,mScale);
        }
        //缩小
        if(mLastPosition == position){
            startAnimation(holder.mTextView,mScale,1.0f);
        }

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mLastPosition = mCurrentPosition;
                    mCurrentPosition = position;
                    mListener.itemClick(holder.mTextView,position);
                }
            }
        });
    }

    /**
     * 放大缩小动画
     * @param textView
     * @param start
     * @param end
     */
    private void startAnimation(TextView textView,float start,float end){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView,"scaleX",start, end);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView,"scaleY",start, end);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);  //同时执行
        animatorSet.setDuration(350);
        animatorSet.start();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;

        }
    }
    public ItemClickListener mListener;
    public void setItemClickListener(ItemClickListener listener){
        mListener = listener;
    }
    public interface ItemClickListener{
        void itemClick(TextView tv,int position);
    }
}
