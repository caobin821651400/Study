package com.example.androidremark.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidremark.utils.MyUtils;

/**
 * 圆 周围发光view
 * Created by caobin on 2018/7/24.
 */
public class BlurMaskCircularView extends View {
    private Paint mPaint;
    private int radius = 0;

    public BlurMaskCircularView(Context context) {
        this(context, null);
    }

    public BlurMaskCircularView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public BlurMaskCircularView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        radius = MyUtils.dp2px(context, 80);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //发光圆，radius是光晕半径
        mPaint.setMaskFilter(new BlurMaskFilter(radius + 60, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth() / 2, (float) (getHeight() / 2.3));

        canvas.drawCircle(0, 0, radius, mPaint);
    }

    /**
     * 刷新颜色zhi
     *
     * @param color
     */
    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    /**
     * 取当前颜色
     *
     * @return 颜色值
     */
    public int getColor() {
        return mPaint.getColor();
    }

    public void refresh() {
    }
}
