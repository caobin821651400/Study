package com.example.androidremark.ui.imageview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidremark.R;

import java.util.List;

/**
 * 可拖拽的gridView
 * Created by Mr.ye on 2017/7/28.
 */

public class AppDragAdapter extends BaseAdapter {

    private List<String> mList;
    private int hidePosition = AdapterView.INVALID_POSITION;
    private int state = -1;
    private Context mContext;

    public AppDragAdapter(Context activity) {
        this.mContext = activity;
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = convertView;
        if (convertView == null) {
            holder = new ViewHolder();
            view = View.inflate(parent.getContext(), R.layout.item_list_drag_menu, null);
            holder.name = (TextView) view.findViewById(R.id.tv_name);
            holder.icon = (ImageView) view.findViewById(R.id.iv_logo);
            holder.state = (ImageView) view.findViewById(R.id.iv_state);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(mList.get(position));
        return view;
    }

    class ViewHolder {
        TextView name;
        ImageView icon;
        ImageView state;
    }

    public void hideView(int pos) {
        hidePosition = pos;
        notifyDataSetChanged();
    }

    /**
     * @param state 0显示减号  1不显示
     */
    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }


    public void showHideView() {
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }

    public void removeView(int pos) {
        mList.remove(pos);
        notifyDataSetChanged();
    }

    //更新拖动时的gridView
    public void swapView(int draggedPos, int destPos) {
        //从前向后拖动，其他item依次前移
        if (draggedPos < destPos) {
            mList.add(destPos + 1, getItem(draggedPos));
            mList.remove(draggedPos);
        }
        //从后向前拖动，其他item依次后移
        else if (draggedPos > destPos) {
            mList.add(destPos, getItem(draggedPos));
            mList.remove(draggedPos + 1);
        }
        hidePosition = destPos;
        notifyDataSetChanged();
    }
}
