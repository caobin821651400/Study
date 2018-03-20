package com.example.androidremark.ui2.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by caobin on 2017/9/19.
 */

public class CalendarCellTextView extends TextView {

    public boolean isToday = false;//是否为今天
    public boolean isMark = false;//是否标记
    private Paint mPaint;

    public CalendarCellTextView(Context context) {
        super(context);
    }

    public CalendarCellTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public CalendarCellTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布移到中间
        canvas.translate(getWidth() / 2, getHeight() / 2);

        if (isToday) {
            canvas.drawCircle(0, 0, getWidth() / 2, mPaint);
        }

        //做标记
        if (isMark) {
            mPaint.setColor(Color.BLACK);
            canvas.drawCircle(0, 0, getWidth() / 2, mPaint);
        }
    }
}
