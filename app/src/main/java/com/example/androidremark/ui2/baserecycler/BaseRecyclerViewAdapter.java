package com.example.androidremark.ui2.baserecycler;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by caobin on 2017/9/13.
 */

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> dataLists;//数据集合

    public BaseRecyclerViewAdapter() {
        this(new ArrayList<T>());
    }

    public BaseRecyclerViewAdapter(ArrayList<T> ts) {
        this.dataLists = ts;
    }

    /**
     * 获取制定位置的数据
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        return dataLists.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<T> getDataList() {
        return dataLists;
    }

    /**
     * 刷新全部数据
     *
     * @param list
     */
    public void setDataList(List<T> list) {
        dataLists.clear();
        if (list != null && !list.isEmpty()) {
            dataLists.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 在数据集中插入一个数据,
     *
     * @param position position只能是（0-dataList.size()）
     * @param data
     */
    public void add(int position, T data) {
        dataLists.add(position, data);
        notifyItemChanged(position);
    }

    /**
     * 添加数据集合
     *
     * @param list
     */
    public void addAll(List<T> list) {
        dataLists.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 在制定位置添加数据集合
     *
     * @param position position只能是（0-dataList.size()）
     * @param list
     */
    public void addAll(int position, List<T> list) {
        dataLists.addAll(position, list);
        notifyDataSetChanged();
    }

    /**
     * 输出制定索引条目
     *
     * @param position
     */
    public void remove(int position) {
        dataLists.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除制定数据条目
     *
     * @param data
     */
    public void remove(T data) {
        remove(dataLists.indexOf(data));
    }

    /**
     * 替换制定索引的数据
     *
     * @param position
     * @param data
     */
    public void replace(int position, T data) {
        dataLists.set(position, data);
        notifyItemChanged(position);
    }

    /**
     * 替换制定数据的新数据
     *
     * @param oldData
     * @param newData
     */
    public void replace(T oldData, T newData) {
        replace(dataLists.indexOf(oldData), newData);
    }

    /**
     * @param oldPosition
     * @param newPosition
     */
    public void move(int oldPosition, int newPosition) {
        Collections.swap(dataLists, oldPosition, newPosition);
        notifyItemMoved(oldPosition, newPosition);
    }

    /**
     * 清空所有数据
     */
    public void clear() {
        dataLists.clear();
        notifyDataSetChanged();
    }
}
