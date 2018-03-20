package com.example.androidremark.ui.textview.verticalroll;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.androidremark.R;

import java.util.List;

/**
 * Created by wulee on 2016/5/13.
 */
public class AdItemViewAdapter {
    private List<AdverNotice> mDatas;
    public AdItemViewAdapter(List<AdverNotice> datas) {
        this.mDatas = datas;
        if (mDatas == null || mDatas.isEmpty()) {
            throw new RuntimeException("nothing to show");
        }
    }
    /**
     * 获取数据的条数
     * @return
     */
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /**
     * 获取摸个数据
     * @param position
     * @return
     */
    public AdverNotice getItem(int position) {
        return mDatas.get(position);
    }
    /**
     * 获取条目布局
     * @param parent
     * @return
     */
    public View getView(AdverItemView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.adver_view_item, null);
    }

    /**
     * 条目数据适配
     * @param view
     * @param data
     */
    public void setItem(final View view, final AdverNotice data) {
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(data.title);
        //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //比如打开url
                Toast.makeText(view.getContext(), data.url, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
