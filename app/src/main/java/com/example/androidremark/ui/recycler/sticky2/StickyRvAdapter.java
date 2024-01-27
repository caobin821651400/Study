package com.example.androidremark.ui.recycler.sticky2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidremark.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017/5/8.
 */

public class StickyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MData> MDataList;

    public StickyRvAdapter() {
        MDataList = new ArrayList<>();
    }

    public void addAll(List<MData> beans) {
        if (MDataList.size() > 0) {
            MDataList.clear();
        }
        MDataList.addAll(beans);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == StickyRvActivity.ItemType.VIEW_TYPE_GROUP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_sticky2_group, parent, false);
            return new MyRecycleHolder2(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_sticky2, parent, false);
            return new MyRecycleHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (MDataList == null || MDataList.size() == 0 || MDataList.size() <= position)
            return;
        MData bean = MDataList.get(position);

        int viewType = getItemViewType(position);

        if (viewType == StickyRvActivity.ItemType.VIEW_TYPE_GROUP) {
            ((MyRecycleHolder2) holder).tv_name.setText(bean.getName());
        } else {
            ((MyRecycleHolder) holder).tv_name.setText(bean.getName());
        }
    }


    @Override
    public int getItemCount() {
        return MDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MData bean = MDataList.get(position);
        return bean.getItemType();
    }

    public static class MyRecycleHolder extends RecyclerView.ViewHolder {
        public final TextView tv_name;

        public MyRecycleHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public static class MyRecycleHolder2 extends RecyclerView.ViewHolder {
        public final TextView tv_name;

        public MyRecycleHolder2(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
