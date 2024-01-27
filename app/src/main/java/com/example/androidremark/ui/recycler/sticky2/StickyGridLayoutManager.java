package com.example.androidremark.ui.recycler.sticky2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: cb
 * @date: 2024/1/27 16:22
 * @desc: 描述
 */
public class StickyGridLayoutManager extends GridLayoutManager {

    private static final String TAG = "StickyGridManager";
    private final List<View> stickyAttachedViewList = new ArrayList<>();
    private int[] stickyItemTypes = null;


    public StickyGridLayoutManager(Context context, int[] stickyItemTypes, int spanCount) {
        super(context, spanCount);
        this.stickyItemTypes = stickyItemTypes;
    }

    public StickyGridLayoutManager(Context context, int[] stickyItemTypes, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        this.stickyItemTypes = stickyItemTypes;

    }

    public StickyGridLayoutManager(Context context, AttributeSet attrs, int[] stickyItemTypes, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.stickyItemTypes = stickyItemTypes;
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.stickyItemTypes == null
                || this.stickyItemTypes.length == 0
                || getOrientation() != RecyclerView.VERTICAL) {

            super.onLayoutChildren(recycler, state);
            return;
        }
        //先移除吸顶的View，防止LayoutManager将吸顶的View作为anchor 锚点
        removeStickyView(recycler);
        //让LayoutManager布局，其实这时候可能会将吸顶View加入进去，不过没关系，RecyclerView的addView很强大
        super.onLayoutChildren(recycler, state);
        //布局吸顶的View
        layoutStickyView(recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.stickyItemTypes == null || this.stickyItemTypes.length == 0) {
            return super.scrollVerticallyBy(dy, recycler, state);
        }
        //先移除吸顶的View，防止LayoutManager将吸顶的View作为anchor 锚点
        removeStickyView(recycler);
        //让LayoutManager布局，其实这时候可能会将吸顶View加入进去，不过没关系，RecyclerView的addView很强大
        int scrollOffsetY = super.scrollVerticallyBy(dy, recycler, state);
        //布局吸顶的View
        layoutStickyView(recycler, state);
        return scrollOffsetY;
    }

    private void layoutStickyView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        View currentStickyItemView = lookupStickyItemView(recycler);
        if (currentStickyItemView == null) {
            return;
        }

        /**
         * 下面方法将当前要吸顶的View添加进去
         * 注意1：addView被RecyclerView魔改过，正常情况下一个View只能被addView一次
         * 注意2: LayoutManager的addView会尽可能抑制requestLayout，正常情况下，addView必然会requestLayout
         * 注意3: LayoutManager多次addView同一个View，如果两次位置不一样，那只会改变View的加入顺序和绘制顺序
         * 注意4: 在Android系统的中，最后加入的View绘制顺序和接受事件的优先级是最高的。
         */
        addView(currentStickyItemView);

        measureChildWithMargins(currentStickyItemView, 0, 0);
        List<View> stickyChildren = getStickyItemViews();

        int top = 0;
        int topStickyViewHeight = getDecoratedMeasuredHeight(currentStickyItemView);
        int topStickyViewTop = getDecoratedTop(currentStickyItemView);

        /**
         * 因为不能保证吸顶的View顺序是最理想的按默认排列，因此这里正在西定的View在绘制顺序的最顶部，
         * 但是其他可以吸顶的View是正常顺序，因此删除掉，从开始位置计算，如果下一个离正在吸顶View最近的View顶到了它 （哈哈，莫要想歪了），
         * 那么就得让他偏移
         */
        stickyChildren.remove(currentStickyItemView);

        int size = stickyChildren.size();
        for (int index = 0; index < size; index++) {
            View nextChild = stickyChildren.get(index);
            int nextStickyViewTop = getDecoratedTop(nextChild);
            if (nextStickyViewTop < topStickyViewTop) {
                continue;
            }
            if (nextStickyViewTop > topStickyViewHeight) {
                continue;
            }
            top = nextStickyViewTop - topStickyViewHeight; //计算偏移距离
            break;
        }
        int bottom = top + topStickyViewHeight;
        layoutDecoratedWithMargins(currentStickyItemView, 0, top, getDecoratedMeasuredWidth(currentStickyItemView), bottom);
    }

    /**
     * 获取当前页面布局区域内的所有吸顶View
     *
     * @return
     */
    private List<View> getStickyItemViews() {
        stickyAttachedViewList.clear();
        int childCount = getChildCount();
        if (childCount <= 0) {
            return stickyAttachedViewList;
        }
        for (int i = 1; i < childCount; i++) {
            View child = getChildAt(i);
            if (child == null) continue;
            int itemViewType = getItemViewType(child);
            if (isStickyItemType(itemViewType)) {
                stickyAttachedViewList.add(child);
            }
        }
        return stickyAttachedViewList;
    }

    @Nullable
    private View lookupStickyItemView(RecyclerView.Recycler recycler) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return null;
        }
        //先看看第一个View是不是可以吸顶，如果不可以，则从缓存中查询
        View view = getChildAt(0);
        int itemViewType = getItemViewType(view);
        int adapterPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        View groupView = null;
        if (!isStickyItemType(itemViewType)) {
            //一般来说下，吸顶View的itemType在前面查询，如果要改成吸底的则在后面查询，因此这里逆序
            for (int i = adapterPosition - 1; i >= 0; i--) {
                //从缓存中查询
                View childView = recycler.getViewForPosition(i);
                //获取View类型
                itemViewType = getItemViewType(childView);
                if (isStickyItemType(itemViewType)) {
                    groupView = childView;
                    break;
                }
            }
        } else {
            //页面上第一个View就是吸顶的View
            groupView = view;
        }

        if (groupView == null) {
            Log.d(TAG, "not found " + itemViewType + " ,topChildPosition =" + adapterPosition);
            return null;
        }
        return groupView;
    }

    private boolean isStickyItemType(int itemViewType) {
        if (this.stickyItemTypes == null || this.stickyItemTypes.length == 0) {
            return false;
        }
        for (int i = 0; i < this.stickyItemTypes.length; i++) {
            if (this.stickyItemTypes[i] == itemViewType) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除正在吸顶的View
     *
     * @param recycler
     */
    private void removeStickyView(RecyclerView.Recycler recycler) {
        int count = getChildCount();
        if (count <= 0) {
            return;
        }
        /**
         * 注意，这里一定要删除页面上的View，而不是从缓存中拿出来删，那样是无用功
         */
        for (int i = 1; i < count; i++) {
            View child = getChildAt(i);
            if (child == null) continue;
            int itemViewType = getItemViewType(child);
            if (!isStickyItemType(itemViewType)) {
                continue;
            }
            int decoratedTop = getDecoratedTop(child);
            if (decoratedTop <= 0) {
                //删除 top <= 0的吸顶View，因为正常情况下页面child要么在吸顶，要么不可见了
                removeAndRecycleView(child, recycler);
            }
        }
    }
}
