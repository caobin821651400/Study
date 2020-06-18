package com.example.androidremark.ui3.tantan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidremark.utils.XLogUtils;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/17 23:15
 * @Desc :卡片折叠式LayoutManager
 * ====================================================
 */
public class SwipeCardLayoutManager extends RecyclerView.LayoutManager {

    /**
     * RV LayoutParams
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置子View的显示位置
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        //回收
        detachAndScrapAttachedViews(recycler);

        int itemCount = getItemCount();
        int startPosition = 0;

        if (itemCount >= CardConfig.MAX_SHOW_COUNT) {
            startPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }

        //遍历每一个View
        for (int i = startPosition; i < itemCount; i++) {

            XLogUtils.d("itemCount-->" + itemCount);
            XLogUtils.d("startPosition-->" + startPosition);

            //复用
            View view = recycler.getViewForPosition(i);
            addView(view);

            measureChildWithMargins(view, 0, 0);

            //padding
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);//水平边距
            int heightSpace = getWidth() - getDecoratedMeasuredHeight(view);//垂直边距

            //布局
            int left, top, right, bottom;
            left = widthSpace / 2;
            top = heightSpace / 2;
            right = widthSpace / 2 + getDecoratedMeasuredWidth(view);
            bottom = heightSpace / 2 + getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view, left, top, right, bottom);


            //显示三个实际加载了四个，第四个和第三个大小一致
            int level = itemCount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * level);
                    view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                    view.setScaleY(1 - CardConfig.SCALE_GAP * level);
                } else {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                    view.setScaleX(1 - CardConfig.SCALE_GAP * (level - 1));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                }
            }
        }
    }
}
