package com.miaozi.miaozi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * created by panshimu
 * on 2019/9/17
 */
public class MyFragment extends Fragment {
    private TextView mTv;
    private View mView;
    private String tv;
    public MyFragment(String tv) {
        this.tv = tv;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_fragment, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTv = mView.findViewById(R.id.tv);
        if(mTv != null) {
            mTv.setText(tv);
        }
    }
}
