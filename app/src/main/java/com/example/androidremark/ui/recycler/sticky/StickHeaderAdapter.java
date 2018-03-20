package com.example.androidremark.ui.recycler.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidremark.R;

import java.util.List;

/**
 * 粘性reclcler
 * Created by caobin on 2017/9/11.
 */

public class StickHeaderAdapter extends RecyclerView.Adapter<StickHeaderAdapter.StickHeaderVH> {

    private LayoutInflater mInflater;
    private List<ItemInfoBean> mDatas;

    public StickHeaderAdapter(List<ItemInfoBean> datas) {
        this.mDatas = datas;
    }

    @Override
    public StickHeaderVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.item_list_sticky_recycler, parent, false);
        return new StickHeaderVH(view);
    }

    @Override
    public void onBindViewHolder(StickHeaderVH holder, int position) {
        //holder.sdvPic.setImageURI(mDatas.get(position).url);
        holder.tvTitle.setText(mDatas.get(position).name);
        generateTag(holder, position);
    }

    /**
     * @param holder
     * @param position
     */
    private void generateTag(StickHeaderVH holder, int position) {
        AllItemBean tag;
        // 没有tag的话 new 一个, 有的话 复用
        if (holder.itemView.getTag() == null) {
            tag = new AllItemBean();
        } else {
            tag = (AllItemBean) holder.itemView.getTag();
        }
        //判断当前position  的开始结束状态
        if (position == 0) {//所有item的第一项
            tag.isGroupStart = true;
            tag.isGroupEnd = !mDatas.get(position).tag.equals(mDatas.get(position + 1).tag);
        } else if (position == mDatas.size() - 1) {//所有item的最后一项
            tag.isGroupStart = !mDatas.get(position).tag.equals(mDatas.get(position - 1).tag);
            tag.isGroupEnd = true;
        } else {
            //如果和上一个item不同了，则改position该现实bar了
            tag.isGroupStart = !mDatas.get(position).tag.equals(mDatas.get(position - 1).tag);
            //如果和下一个item不同了，则分组结束了
            tag.isGroupEnd = !mDatas.get(position).tag.equals(mDatas.get(position + 1).tag);
        }
        holder.itemView.setTag(tag);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class StickHeaderVH extends RecyclerView.ViewHolder {
        ImageView sdvPic;
        TextView tvTitle;

        public StickHeaderVH(View itemView) {
            super(itemView);
            sdvPic = (ImageView) itemView.findViewById(R.id.sdvPic);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
