package com.example.androidremark.ui.recycler.sticky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.example.androidremark.utils.MyUtils;

import java.util.List;

/**
 * Created by caobin on 2016/9/23.
 */
public class StickyHeaderDecoration extends RecyclerView.ItemDecoration {

    private float mDividerHeight;//bar的高度
    private Paint mPaint;
    private Paint mTextPaint;
    private List<ItemInfoBean> mDatas;
    private float topOffset;//分割线的高度
    private float leftOffset;//paddingLeft

    /**
     * @param height  bar的高度
     * @param context
     */
    public StickyHeaderDecoration(int height, Context context) {
        this(height, Color.GRAY, context);
    }


    public StickyHeaderDecoration(int height, @ColorInt int color, Context ctx) {
        this.mDividerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, ctx.getResources().getDisplayMetrics());
        topOffset = MyUtils.dp2px(ctx, 1);
        leftOffset = MyUtils.dp2px(ctx, 12);


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, ctx.getResources().getDisplayMetrics()));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 如果是头部,留出顶部空间用作画Header
        if (((AllItemBean) view.getTag()).isGroupStart) {
            outRect.set(0, (int) mDividerHeight, 0, 0);
        } else {
            outRect.set(0, (int) topOffset, 0, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);
            if (index != RecyclerView.NO_POSITION && ((AllItemBean) view.getTag()).isGroupStart) {
                drawHeader(c, parent, view, index);
            } else {
                //自己画画分割线
            }
        }

    }

    /**
     * 画头部
     *
     * @param c
     * @param parent
     * @param view
     * @param position
     */
    private void drawHeader(Canvas c, RecyclerView parent, View view, int position) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        float left = parent.getPaddingLeft();
        float right = parent.getWidth() - parent.getPaddingRight();
        float bottom = view.getTop() - params.topMargin - Math.round(view.getTranslationY());
        float top = bottom - mDividerHeight;
        //计算文字居中的基线
        Rect rect = new Rect((int) left, (int) top, (int) right, (int) bottom);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        int baseLine = (int) ((rect.bottom + rect.top - metrics.bottom - metrics.top) / 2);
        c.drawRect(left, top, right, bottom, mPaint);
        c.drawText(mDatas.get(position).tag, left + leftOffset, baseLine, mTextPaint);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        View child1 = parent.getChildAt(0);
        View child2 = parent.getChildAt(1);
        if (child1 != null && child2 != null) {
            AllItemBean allItemBean1 = (AllItemBean) child1.getTag();
            AllItemBean allItemBean2 = (AllItemBean) child2.getTag();

            int position = parent.getChildAdapterPosition(child1);
            //四个坐标
            int left = parent.getPaddingLeft();
            int top = 0;//这个bar是固定在recycler顶部的
            int right = parent.getWidth() - parent.getPaddingRight();
            int bottoom = (int) mDividerHeight;//
            // 判断是否达到临界点
            // (第一个可见item是每组的最后一个,第二个可见tiem是下一组的第一个,并且第一个可见item的底部小于header的高度)
            // 这里直接判断item的底部位置小于header的高度有点欠妥,应该还要考虑paddingtop以及margintop,这里展示不考虑了
            if (allItemBean1.isGroupEnd && allItemBean2.isGroupStart && child1.getBottom() <= mDividerHeight) {
                bottoom = child1.getBottom();
                top = (int) (bottoom - mDividerHeight);
            }
            // 计算文字居中时候的基线
            Rect targetRect = new Rect(left, top, right, bottoom);
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            // 背景
            c.drawRect(left, top, right, bottoom, mPaint);
            // 文字
            c.drawText(mDatas.get(position).tag, left + leftOffset, baseline, mTextPaint);
        }
    }

    /**
     * 头部的名称
     *
     * @param mDatas
     */
    public void setDatas(List<ItemInfoBean> mDatas) {
        this.mDatas = mDatas;
    }
}
