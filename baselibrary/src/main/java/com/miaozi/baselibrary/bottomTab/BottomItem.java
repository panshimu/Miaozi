package com.miaozi.baselibrary.bottomTab;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * created by panshimu
 * on 2019/9/9
 */
public class BottomItem {
    private String text;
    private int unselectedResId;
    private int selectedResId;
    private TextView textView;
    private ImageView imageView;
    private ViewGroup contentView;

    public ViewGroup getContentView() {
        return contentView;
    }

    public void setContentView(ViewGroup contentView) {
        this.contentView = contentView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public BottomItem(String text, int unselectedResId, int selectedResId) {
        this.text = text;
        this.unselectedResId = unselectedResId;
        this.selectedResId = selectedResId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUnselectedResId() {
        return unselectedResId;
    }

    public void setUnselectedResId(int unselectedResId) {
        this.unselectedResId = unselectedResId;
    }

    public int getSelectedResId() {
        return selectedResId;
    }

    public void setSelectedResId(int selectedResId) {
        this.selectedResId = selectedResId;
    }
}
