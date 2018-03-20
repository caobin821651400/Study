package com.example.androidremark.ui2.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidremark.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by caobin on 2017/9/19.
 */

public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<calendarBean> mList;

    public CalendarAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(ArrayList<calendarBean> cells) {
        this.mList = cells;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public calendarBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_calecdar_cell, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cell = (CalendarCellTextView) convertView.findViewById(R.id.tv_cell);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        calendarBean bean =getItem(position);
        Date date = bean.getDate();
        int day = date.getDate();
        viewHolder.cell.setText(String.valueOf(day));
        Date now = new Date();

        //有效日期
        if (date.getMonth() == now.getMonth()) {
            viewHolder.cell.setTextColor(Color.BLACK);
        } else {
            viewHolder.cell.setTextColor(Color.GRAY);
        }

        //做标记
        if (bean.isMark()) {
            viewHolder.cell.isMark=true;
        }else{
            viewHolder.cell.isMark=false;
        }

        //当天
        if (now.getDate() == date.getDate() && now.getMonth() == date.getMonth() && now.getYear() == date.getYear()) {
            viewHolder.cell.setTextColor(Color.RED);
            viewHolder.cell.isToday = true;
        } else {
            viewHolder.cell.isToday = false;
        }

        return convertView;
    }


    class ViewHolder {
        CalendarCellTextView cell;
    }
}
