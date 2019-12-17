package com.miaozi.miaozi;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.miaozi.baselibrary.photoview.PhotoView;

/**
 * created by panshimu
 * on 2019/12/11
 */
public class PhotoViewActivity extends AppCompatActivity {
    public static final String IMAGE_PATH = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575952910844&di=43c5ffd93fb931a6a342bdd56f18533c&imgtype=0&src=http%3A%2F%2Fwww.goumin.com%2Fattachments%2Fphoto%2F0%2F0%2F61%2F15705%2F4020605o2.jpg";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view_activity);
        PhotoView mIv = findViewById(R.id.pv);
        Glide.with(this).load(IMAGE_PATH).into(mIv);
    }
}
