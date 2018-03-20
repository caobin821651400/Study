package com.example.androidremark.ui2.sinmath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * 类描述：
 * <p>
 * 作者：Administrator on 2017/7/10 0010 11:47
 * 邮箱：377289596@qq.com
 */

public class MyWaveView extends View {

    private Paint mPaint;
    private Path mPath;

    // view宽度
    private int width;
    // view高度
    private int height;

    // 波浪高低偏移量
    private int offset = 100;

    // X轴，view的偏移量
    private int xoffset = 0;

    // view的Y轴高度
    private int viewY = 0;

    // 波浪速度
    private int waveSpeed = 50;

    //private ValueAnimator animator;

    public MyWaveView(Context context) {
        super(context);
        init(context);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);

        mPath = new Path();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();

//        animator = new ValueAnimator();
//        animator.setFloatValues(0, width);
//        animator.setDuration(waveSpeed * 20);
//        animator.setRepeatCount(-1);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float change = (float) animation.getAnimatedValue();
//                xoffset = (int) change;
//                invalidate();
//            }
//        });
//
//        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //设置一个默认值，就是这个View的默认宽度为500，这个看我们自定义View的要求
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {//相当于我们设置为wrap_content
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            result = specSize;
        }
        width = result;
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        height = specSize;
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        viewY = height / 2;

        // 绘制屏幕内的波浪
        mPath.moveTo(xoffset, viewY);
        mPath.quadTo(width / 4 + xoffset, viewY - offset, width / 2 + xoffset, viewY);
        mPath.moveTo(width / 2 + xoffset, viewY);
        mPath.quadTo(width / 4 * 3 + xoffset, viewY + offset, width + xoffset, viewY);

        // 绘制屏幕外的波浪
        mPath.moveTo(xoffset - width, viewY);
        mPath.quadTo(width / 4 + xoffset - width, viewY - offset, width / 2 + xoffset - width, viewY);
        mPath.moveTo(width / 2 + xoffset - width, viewY);
        mPath.quadTo(width / 4 * 3 + xoffset - width, viewY + offset, width + xoffset - width, viewY);

        canvas.drawPath(mPath, mPaint);

    }

    /**
     * 设置 波浪的高度
     */
    public void setWaveHeight(int waveHeight){
        offset = waveHeight;
    }

    /**
     * 获取 波浪的高度
     */
    public int getWaveHeight(){
        return offset;
    }
//
//    /**
//     * 设置 波浪的速度
//     */
//    public void setWaveSpeed(int speed){
//        waveSpeed = 2000 - speed * 20;
//        animator.setDuration(waveSpeed);
//    }

    /**
     * 获取 波浪的速度
     */
    public int getWaveSpeed(){
        return waveSpeed;
    }

}
