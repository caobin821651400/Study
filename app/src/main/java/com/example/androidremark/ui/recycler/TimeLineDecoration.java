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
 * 时光轴item
 * Created by caobin on 2017/9/8.
 */

public class TimeLineDecoration extends RecyclerView.ItemDecoration {

    private float mOffsetTop;//分割线的高度
    private float mOffsetLeft;//左边偏移量
    private float mCircularRadius;//圆环半斤
    private float mDividerHeight;//分割线的高度
    private Paint mPaint;
    private Context mContext;

    public TimeLineDecoration(Context mContext) {
        this.mContext = mContext;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);

        mOffsetLeft = MyUtils.dp2px(mContext, 40);
        mCircularRadius = MyUtils.dp2px(mContext, 8);
        mDividerHeight = MyUtils.dp2px(mContext, 2);

        //画bitmap
//        mIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.clock);
//        mIcon = Bitmap.createScaledBitmap(mIcon,(int)mNodeRadius * 2,(int)mNodeRadius * 2,false);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //第一个不需要绘制
        if (parent.getChildAdapterPosition(view) != 0) {
            mOffsetTop = MyUtils.dp2px(mContext, 2);
            outRect.top = (int) mOffsetTop;
        }
        //左偏移
        outRect.left = (int) mOffsetLeft;
    }


    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        int itemCount = parent.getChildCount();

        for (int i = 0; i < itemCount; i++) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);

            float itemTop = view.getTop() - mOffsetTop;
            if (index == 0) {
                itemTop = view.getTop();
            }
            float itemLeft = parent.getPaddingLeft();
            float itemRight = parent.getWidth() - parent.getPaddingRight();
            float itemBottom = view.getBottom();

            //中心点
            float centerX = mOffsetLeft / 2 + itemLeft;
            float centerY = (itemBottom - itemTop) / 2 + itemTop + mDividerHeight;

            //分割线
            if (index != 0) {
                float divLeft = parent.getPaddingLeft() + mOffsetLeft;
                float divTop = view.getTop() - mDividerHeight;
                float divRight = parent.getWidth() - parent.getPaddingRight();
                float divBottom = view.getTop();

                canvas.drawRect(divLeft, divTop, divRight, divBottom, mPaint);
            }

            //上半部分轴线
            float upLineTopX = centerX;
            float upLineTopY = itemTop;
            float upLineBottomX = centerX;
            float upLineBottomY = centerY - mCircularRadius;

            canvas.drawLine(upLineTopX, upLineTopY, upLineBottomX, upLineBottomY, mPaint);

            //中间的圆
            canvas.drawCircle(centerX, centerY, mCircularRadius, mPaint);
//            c.drawBitmap(mIcon,centerX - mNodeRadius,centerY - mNodeRadius,mPaint);

            //下半部分轴线
            float downLineTopX = centerX;
            float downLineTopY = centerY + mCircularRadius;
            float downLineBottomX = centerX;
            float downLineBottomY = itemBottom;

            canvas.drawLine(downLineTopX, downLineTopY, downLineBottomX, downLineBottomY, mPaint);
        }
    }
}
