package com.example.androidremark.ui.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.androidremark.utils.MyUtils;

/**
 * Created by caobin on 2017/9/8.
 */

public class LineItemDecoration extends RecyclerView.ItemDecoration {

    private float mDividerHeight;//分割线的高度
    private Paint mPaint;
    private Context mContext;

    public LineItemDecoration(Context mContext) {
        this.mContext = mContext;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = MyUtils.dp2px(mContext, 10);
            mDividerHeight = MyUtils.dp2px(mContext, 10);
        }
    }


    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        //遍历所有可见的item
        int itemCount = parent.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);
            //第一行不需要绘制
            if (index == 0) {
                continue;
            }
            float dividerLeft = view.getPaddingLeft();
            float dividerTop = view.getTop() - mDividerHeight;
            float dividerRight = parent.getWidth() - view.getPaddingRight();
            float dividerBottom = view.getTop();

            canvas.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
        }
    }
}
