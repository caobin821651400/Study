package com.example.androidremark.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.example.androidremark.utils.MyUtils;


/**
 * 服务满意度里的进度条
 * Created by cb on 2017/11/20.
 */

public class MyProgressBar extends ProgressBar {

    private Paint mTextPaint;
    private int mTextSize;//上面%字大小
    private Context mContext;
    private int txtMarginTop;//文字距离进度条走过的高度

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mTextSize = MyUtils.sp2px(mContext, 10);
        txtMarginTop = MyUtils.sp2px(mContext, 15);
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true); // 抗锯齿
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        float textWidth = 0;
        String text = getProgress() + "%";
        textWidth = mTextPaint.measureText(text);//文字宽度

        float scale = getProgress() * 1.0f / getMax();//当前比例
        float progressPosY = height * scale;//当前进度条走过的高度
        if (getProgress() != 0)
            canvas.drawText(text, (width - textWidth) / 2, height - progressPosY + txtMarginTop, mTextPaint);

    }
}
