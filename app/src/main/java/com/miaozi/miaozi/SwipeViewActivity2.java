package com.miaozi.miaozi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.miaozi.baselibrary.views.SwipeLayout;
import com.miaozi.baselibrary.views.SwipeLayout2;

/**
 * created by panshimu
 * on 2019/9/10
 */
public class SwipeViewActivity2 extends AppCompatActivity {

    private ListView mLv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_view_1);
        mLv = findViewById(R.id.lv);
        MyAdapter2 mAdapter = new MyAdapter2();
        mLv.setAdapter(mAdapter);
    }

    class MyAdapter2 extends BaseAdapter{

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(SwipeViewActivity2.this).inflate(R.layout.swipe_item, parent, false);
            final SwipeLayout2 swipeLayout = view.findViewById(R.id.swipe);
            swipeLayout.setItemOnclickListener(new SwipeLayout.ItemOnclickListener() {
                @Override
                public void itemOnclick() {
                    Toast.makeText(SwipeViewActivity2.this,"position="+position,Toast.LENGTH_SHORT).show();
                }
            });
            view.findViewById(R.id.bt_11).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SwipeViewActivity2.this,"删除-position="+position,Toast.LENGTH_SHORT).show();
                    swipeLayout.closeMenu();
                }
            });
            return view;
        }
    }
}
