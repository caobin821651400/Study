package com.example.androidremark.ui2.baserecycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidremark.R;

import java.util.List;

/**
 * recycler的baseAdapter封装一些常用的方法
 * Created by caobin on 2017/9/13.
 */

public abstract class XRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T, XViewHolder> {
    //用来存放head和footer  view的集合
    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private SparseArray<View> mFooterViews = new SparseArray<>();

    private static int TYPE_HEADER = 0x100;//头部标识
    private static int TYPE_FOOTER = 0x200;//
    private static final int TYPE_LOAD_FAILED = 0x1;//加载失败
    private static final int TYPE_NO_MORE = 0x2;//没有更多
    private static final int TYPE_LOAD_MORE = 0x3;//可以加载
    private static final int TYPE_NO_VIEW = 0x4;//没有view
    private static final int TYPE_EMPTY = 0x5;//空数据
    private static final int TYPE_ERROR = 0x6;//请求出错
    private boolean isRequestError = false;//请求是否出错

    private RecyclerView mRecyclerView;
    private Context mContext;
    private View mLoadMoreView;
    private View mLoadMoreFailedView;
    private View mNoMoreView;
    private int mLoadItemType = TYPE_NO_VIEW;//默认footer

    private OnLoadMoreListener onLoadMoreListener;
    private LayoutInflater inflater;
    private int layoutId;
    private boolean isLoadError = false;//标记是否加载出错
    private boolean isCanLoadMore = false;//是否有加载更多
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public XRecyclerViewAdapter(@NonNull RecyclerView mRecyclerView) {
        this(mRecyclerView, -1);
    }

    public XRecyclerViewAdapter(@NonNull RecyclerView mRecyclerView, @LayoutRes int layoutId) {
        this.mRecyclerView = mRecyclerView;
        this.layoutId = layoutId;
        this.mContext = mRecyclerView.getContext();
        this.inflater = LayoutInflater.from(mContext);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (isRequestError) return TYPE_ERROR;
        if (dataLists == null || dataLists.isEmpty()) return TYPE_EMPTY;
        if (isHeaderPosition(position)) return mHeaderViews.keyAt(position);
        if (isLoadPosition(position)) return mLoadItemType;
        if (isFooterPosition(position)) {
            position = position - getHeaderCount() - getDataCount();
            return mFooterViews.keyAt(position);
        }
        position = position - getHeaderCount();
        return getItemLayoutResId(getItem(position), position);
    }

    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return new XViewHolder(mHeaderViews.get(viewType));
        }
        if (isFooterViewType(viewType)) {
            return new XViewHolder(mFooterViews.get(viewType));
        }
        if (viewType == TYPE_NO_MORE) {
            mNoMoreView = inflater.inflate(R.layout.adapter_no_more, mRecyclerView, false);
            return new XViewHolder(mNoMoreView);
        }
        if (viewType == TYPE_LOAD_MORE) {
            mLoadMoreView = inflater.inflate(R.layout.adapter_loading, mRecyclerView, false);
            return new XViewHolder(mLoadMoreView);
        }
        if (viewType == TYPE_LOAD_FAILED) {
            mLoadMoreFailedView = inflater.inflate(R.layout.adapter_loading_failed, mRecyclerView, false);
            return new XViewHolder(mLoadMoreFailedView);
        }
        if (viewType == TYPE_EMPTY) {//空数据
            View view = inflater.inflate(R.layout.adapter_loading_empty, mRecyclerView, false);
            return new XViewHolder(view);
        }
        if (viewType == TYPE_ERROR) {//请求出错
            View view = inflater.inflate(R.layout.adapter_loading_empty, mRecyclerView, false);
            return new XViewHolder(view);
        }
        return new XViewHolder(inflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final XViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_LOAD_FAILED) {
            mLoadMoreFailedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onRetry();
                        isLoadMore(true);
                    }
                }
            });
            return;
        }
        if (holder.getItemViewType() == TYPE_LOAD_MORE) {
            if (onLoadMoreListener != null && isCanLoadMore) {
                if (!isLoadError) {
                    onLoadMoreListener.onLoadMore();
                }
            }
            return;
        }
        if (isFooterPosition(position) || isHeaderPosition(position)) return;

        final int finalPosition = position - getHeaderCount();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.itemView, finalPosition);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    return onItemLongClickListener.onItemLongClick(holder.itemView, finalPosition);
                }
                return false;
            }
        });

        bindData(holder, getItem(finalPosition), finalPosition);
    }

    protected abstract void bindData(XViewHolder holder, T data, int position);

    /**
     * 返回正常item布局
     *
     * @return
     */
    public int getItemLayoutResId(T data, int position) {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        if (isRequestError) return 1;
        if (dataLists == null || dataLists.isEmpty()) return 1;//空数据返回1
        return getDataCount() + getHeaderCount() + getFooterCount() + (isCanLoadMore ? 1 : 0);
    }

    public int getDataCount() {
        return dataLists.size();
    }

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFooterViews.size();
    }

    /**
     * 是否可以加载更多
     */
    public void isLoadMore(boolean isHaveStatesView) {
        mLoadItemType = TYPE_LOAD_MORE;
        isLoadError = false;
        this.isCanLoadMore = isHaveStatesView;
        notifyItemChanged(getItemCount());
    }

    /**
     * 加载更多失败
     */
    public void showLoadError() {
        mLoadItemType = TYPE_LOAD_FAILED;
        isLoadError = true;
        isCanLoadMore = true;
        notifyItemChanged(getItemCount());
    }

    /**
     * 加载更多完成
     */
    public void showLoadComplete() {
        mLoadItemType = TYPE_NO_MORE;
        isLoadError = false;
        isCanLoadMore = true;
        notifyItemChanged(getItemCount());
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    @Override
    public void setDataList(List<T> datas) {
        if (datas == null || datas.isEmpty()) {
            mLoadItemType = TYPE_EMPTY;
        } else {
            mLoadItemType = TYPE_LOAD_MORE;
        }
        super.setDataList(datas);
    }

    /**
     * 判断是否是FooterView
     *
     * @param viewType
     * @return
     */
    private boolean isFooterViewType(int viewType) {
        int position = mFooterViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 判断是否是HeaderView
     *
     * @param viewType
     * @return
     */
    private boolean isHeaderViewType(int viewType) {
        int position = mHeaderViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 当前位置是否是Footer
     *
     * @param position
     * @return
     */
    private boolean isFooterPosition(int position) {
        return position >= (getHeaderCount() + getDataCount());
    }

    /**
     * 当前位置是否是加载更多
     *
     * @param position
     * @return
     */
    private boolean isLoadPosition(int position) {
        return position == getItemCount() - 1 && isCanLoadMore;
    }

    /**
     * 当前位置是否是headView
     *
     * @param position
     * @return
     */
    private boolean isHeaderPosition(int position) {
        return position < getHeaderCount();
    }

    /**
     * 添加HeaderView
     *
     * @param view
     */
    public void addHeaderView(View view) {
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            mHeaderViews.put(TYPE_HEADER++, view);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加FooterView
     *
     * @param view
     */
    public void addFooterView(View view) {
        int position = mFooterViews.indexOfValue(view);
        if (position < 0) {
            mFooterViews.put(TYPE_FOOTER++, view);
        }
        notifyDataSetChanged();
    }

    /**
     * 去掉HeaderView
     *
     * @param view
     */
    public void removeHeaderView(View view) {
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) return;
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    /**
     * 去掉FooterView
     *
     * @param view
     */
    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) return;
        mFooterViews.removeAt(index);
        notifyDataSetChanged();
    }

    /**
     * 目的让headView和footerView独占一行
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                    return (isHeaderPosition(position) || isFooterPosition(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 目的让headView和footerView独占一行
     */
    @Override
    public void onViewAttachedToWindow(XViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isFooterPosition(position) || isHeaderPosition(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * 上拉加载监听
     *
     * @param onLoadMoreListener
     * @return
     */
    public XRecyclerViewAdapter<T> setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    /**
     * item点击事件
     *
     * @param onItemClickListener
     * @return
     */
    public XRecyclerViewAdapter<T> setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    /**
     * item长按事件
     *
     * @param onItemLongClickListener
     * @return
     */
    public XRecyclerViewAdapter<T> setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }

    public interface OnLoadMoreListener {
        void onRetry();//点击重试回调

        void onLoadMore();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View v, int position);
    }

}
