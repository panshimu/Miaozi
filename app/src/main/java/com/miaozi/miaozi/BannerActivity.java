package com.miaozi.miaozi;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miaozi.baselibrary.banner.BannerAdapter;
import com.miaozi.baselibrary.banner.BannerView;
import com.miaozi.baselibrary.banner.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        BannerView bannerView = findViewById(R.id.banner);

        final List<String> mBanners = new ArrayList<>();
        mBanners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568007453413&di=712eb84f808bcaf48df1b985f0c0dab8&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F05%2F21%2F57%2F2359dec8a616d54.jpg");
        mBanners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568007453410&di=96dc018a3daea7334c7f8100f3998574&imgtype=0&src=http%3A%2F%2Fpic30.nipic.com%2F20130629%2F7955309_094049119388_2.jpg");
        mBanners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568007453409&di=017f9b21a161d89a4959a1767a7a23a7&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01773c584918c4a8012060c8ccb24d.jpg%401280w_1l_2o_100sh.jpg");
        mBanners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568007573295&di=48228963c4054d56eca455d0a32560a8&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015bb758c78d18a801219c772b6e9e.png%401280w_1l_2o_100sh.png");
        mBanners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568007573292&di=454292fa94305cff8451867d5ddca5a0&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F016fb658316e03a801219c773ceb43.jpg");
        mBanners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568007573289&di=44cb06ebd6e32ce1dc76668fd51ba7b0&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ced559379801a8012193a3b440cb.jpg%401280w_1l_2o_100sh.jpg");

        bannerView.setDotColor(Color.RED, Color.WHITE);
        bannerView.setDisplayTime(3000);
        bannerView.setDotBottomMargin(10);
        bannerView.setDotGravity(Gravity.RIGHT);
        bannerView.setDotPadding(10);
        bannerView.setDotSize(10, 10);
        bannerView.setAutoPlay(true);
        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(View contentView,int position) {
                ImageView iv = null;
                if (contentView == null) {
                    iv = new ImageView(BannerActivity.this);
                    iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    iv = (ImageView) contentView;
                }
                Glide.with(BannerActivity.this).load(mBanners.get(position)).into(iv);
                return iv;
            }

            @Override
            public int getCount() {
                return mBanners.size();
            }
        }).setOnBannerClickListener(new BannerViewPager.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Log.d("TAG","onclick="+position);
            }
        });

        BannerView bannerView2 = findViewById(R.id.banner2);

        bannerView2.setDotResId(R.mipmap.gg,R.mipmap.hh);
        bannerView2.setDisplayTime(3000);
        bannerView2.setDotBottomMargin(10);
        bannerView2.setDotGravity(Gravity.CENTER);
        bannerView2.setDotPadding(10);
        bannerView2.setDotSize(20, 20);
        bannerView2.setAutoPlay(true);
        bannerView2.setAdapter(new BannerAdapter() {
            @Override
            public View getView(View contentView,int position) {
                ImageView iv = null;
                if (contentView == null) {
                    iv = new ImageView(BannerActivity.this);
                    iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    iv = (ImageView) contentView;
                }
                Glide.with(BannerActivity.this).load(mBanners.get(position)).into(iv);
                return iv;
            }

            @Override
            public int getCount() {
                return mBanners.size();
            }
        }).setOnBannerClickListener(new BannerViewPager.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Log.d("TAG","onclick="+position);
            }
        });
    }
}
