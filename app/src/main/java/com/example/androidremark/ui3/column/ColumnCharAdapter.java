package com.example.androidremark.ui3.column;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.androidremark.R;

import java.util.List;

/**
 * 树状图适配
 * Created by caobin on 2017/9/8.
 */

public class ColumnCharAdapter extends RecyclerView.Adapter<ColumnCharAdapter.ViewHolder> {

    private Context mContext;
    private List<ColumnValueBean> mList;

    public ColumnCharAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<ColumnValueBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_column_char, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ColumnValueBean item = mList.get(position);
        holder.tvTopView.setText(item.getTopValue() + "%");
        holder.tvColumnName.setText(item.getBottomName());
        holder.columnProgress.setProgress(item.getScale());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvColumnName;
        private ProgressBar columnProgress;
        private TextView tvTopView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvColumnName = (TextView) itemView.findViewById(R.id.tv_column_name);
            columnProgress = (ProgressBar) itemView.findViewById(R.id.column_progress);
            tvTopView = (TextView) itemView.findViewById(R.id.tv_top_view);
        }
    }
}
