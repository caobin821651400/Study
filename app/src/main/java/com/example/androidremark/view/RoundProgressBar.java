package com.example.androidremark.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidremark.R;

/**
 * 圆形进度条
 * Created by caobin on 2017/8/25.
 */

public class RoundProgressBar extends View {

    private Paint mPaint;

    private int ringColor;//圆环的颜色

    private int ringProgressColor;//圆环进度的颜色

    private int centerBg;//圆环中间的背景

    private int centerTxtColor;//中间文字的颜色

    private float centerTxtSize;//中间文字的大小

    private float ringWidth;//圆环的宽度

    private int maxProgress;//最大进度

    private int currentProgress;//当前进度

    private int startAngle;//开始的角度

    private boolean isShowCenterTxt;//是否显示中间的文字

    private int style;//进度的风格，实心或者空心

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        //获取自定义属性和默认值，第一个参数是从用户属性中得到的设置，如果用户没有设置，那么就用默认的属性，即：第二个参数
        //圆环的颜色
        ringColor = mTypedArray.getColor(R.styleable.RoundProgressBar_ringColor, 0xff50c0e9);
        //圆环进度条的颜色
        ringProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_ringProgressColor, 0xffffc641);
        //文字的颜色
        centerTxtColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, 0xffff5f5f);
        //文字的大小
        centerTxtSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 35);
        //圆环的宽度
        ringWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_ringWidth, 10);
        //最大进度
        maxProgress = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        //当前进度
        currentProgress = mTypedArray.getInt(R.styleable.RoundProgressBar_progress, 0);
        //是否显示中间的进度
        isShowCenterTxt = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        //进度的风格，实心或者空心
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        //进度开始的角度数
        startAngle = mTypedArray.getInt(R.styleable.RoundProgressBar_startAngle, -90);
        //圆心背景颜色
        centerBg = mTypedArray.getColor(R.styleable.RoundProgressBar_centreColor, 0);
        //回收资源
        mTypedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2;//获取圆心x坐标
        int radius = (int) (center - ringWidth / 2); //圆环的半径

        /**
         * 话中心的背景
         */
        if (centerBg != 0) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(centerBg);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(center, center, radius, mPaint);
        }

        /**
         * 画外面大圆弧
         */
        mPaint.setColor(ringColor); //设置圆环的颜色
        mPaint.setStyle(Paint.Style.STROKE); //设置空心
        mPaint.setStrokeWidth(ringWidth); //设置圆环的宽度
        mPaint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(center, center, radius, mPaint); //画出圆环

        /**
         * 画圆弧
         */
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setColor(ringProgressColor);
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);

        switch (style) {
            case STROKE:
                /*第二个参数是进度开始的角度，-90表示从12点方向开始走进度，如果是0表示从三点钟方向走进度，依次类推
                 *public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
                    oval :指定圆弧的外轮廓矩形区域。
                    startAngle: 圆弧起始角度，单位为度。
                    sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
                    useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
                    paint: 绘制圆弧的画板属性，如颜色，是否填充等
                 *
                */
                mPaint.setStyle(Paint.Style.STROKE);
                if (currentProgress != 0) {
                    canvas.drawArc(rectF, startAngle, 360 * currentProgress / maxProgress, false, mPaint);
                }
                break;
            case FILL:
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (currentProgress != 0) {
                    canvas.drawArc(rectF, startAngle, 360 * currentProgress / maxProgress, false, mPaint);
                }
                break;
        }

        /**
         * 画百分比
         */
        mPaint.setStrokeWidth(0);
        mPaint.setColor(centerTxtColor);
        mPaint.setTextSize(centerTxtSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        DecimalFormat format = new DecimalFormat("0.00");
        float percent = 0;
        float txtWidth = 0;
        if (percent != 0) {
            percent = ((float) percent / maxProgress) * 100;//中间的进度百分比，先转换成float在进行除法运算，不然都为0
            String s = format.format(percent);
            txtWidth = mPaint.measureText(percent + "%");
        } else {
            txtWidth = mPaint.measureText("0.00%");
        }
        if (isShowCenterTxt && percent != 0 && style == STROKE) {
            String filesize = format.format(percent);
            canvas.drawText(filesize + "%", center - txtWidth / 2, center + centerTxtSize / 2, mPaint); //画出进度百分比
        } else {
            canvas.drawText("0.00%", center - txtWidth / 2, center + centerTxtSize / 2, mPaint); //画出进度百分比
        }
    }

    public synchronized int getMax() {
        return maxProgress;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.maxProgress = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return currentProgress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > maxProgress) {
            progress = maxProgress;
        }
        if (progress <= maxProgress) {
            this.currentProgress = progress;
            postInvalidate();
        }
    }


    public int getCircleColor() {
        return ringColor;
    }

    public void setCircleColor(int CircleColor) {
        this.ringColor = CircleColor;
    }

    public int getCircleProgressColor() {
        return ringProgressColor;
    }

    public void setCircleProgressColor(int CircleProgressColor) {
        this.ringProgressColor = CircleProgressColor;
    }

    public int getTextColor() {
        return centerTxtColor;
    }

    public void setTextColor(int textColor) {
        this.centerTxtColor = textColor;
    }

    public float getTextSize() {
        return centerTxtSize;
    }

    public void setTextSize(float textSize) {
        this.centerTxtSize = textSize;
    }

    public float getringWidth() {
        return ringWidth;
    }

    public void setringWidth(float ringWidth) {
        this.ringWidth = ringWidth;
    }
}
