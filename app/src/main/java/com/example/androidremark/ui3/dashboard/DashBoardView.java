package com.example.androidremark.ui3.dashboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.androidremark.R;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/18 9:55
 * @Desc :仪表盘
 * ====================================================
 */
public class DashBoardView extends View {

    private int mPadding;//边距，取最大值保持正方形
    private int text2SideWidth;//刻度字内边距
    private int mRadius; // 画布边缘半径（去除padding后的半径）
    private int mProgressWidth;//进度的宽度
    private float mCenterX, mCenterY; // 圆心坐标
    private Paint mPaint;
    private Path mPath;

    //    private int kbps = 1000 * 8;
    private int mMinSpeed = 0; // 最小带宽kb/s
    private int mMaxSpeed = 450 * 1024; // 最大带宽kb/s 3686400
    private int mCurrentSpeed = 0;//当前带宽  带宽粗略计算 网速*8
    private int mStartAngle = 165; // 起始角度
    private int mStartSpaceAngle = 3; // 起始向上偏移角度
    private int mSweepAngle = 210; // 绘制角度
    private int mCreditValue = 650; // 网速
    private int mSection = 10; //等分份数
    private String mHeaderText = "0k/s"; // 表头
    private String mXzsd = "下载速度";
    private int mXzsdColor;
    private int mPLRadius; // 指针长半径
    private int mPSRadius; // 指针短半径
    private int mProgressColors;

    private Rect mKeduTextRect;
    private Rect mSpeedTextRect;
    private RectF mProgressRectF;//进度的矩阵
    private RectF mTextRectF;//刻度文字矩阵


    public DashBoardView(Context context) {
        this(context, null);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mProgressWidth = dp2px(12);

        mProgressRectF = new RectF();
        mTextRectF = new RectF();
        mKeduTextRect = new Rect();
        mSpeedTextRect = new Rect();
        mPath = new Path();

        mProgressColors = Color.parseColor("#00B4A2");
        mXzsdColor = Color.parseColor("#606060");

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.err.println("onMeasure onMeasure");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mPadding = Math.max(
                Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom())
        );

        setPadding(mPadding, mPadding, mPadding, mPadding);
        mRadius = (widthSize - mPadding * 2) / 2;

        mPLRadius = mRadius - dp2px(40);
        mPSRadius = dp2px(20);
        //获取测量的宽度
//        int width = resolveSize(widthSize, widthMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        setMeasuredDimension(width, width);

        mCenterX = mCenterY = getMeasuredWidth() / 2;

        //设置矩阵位置
        mProgressRectF.set(
                mPadding + mProgressWidth / 2f,
                mPadding + mProgressWidth / 2f,
                getMeasuredWidth() - mPadding - mProgressWidth / 2f,
                getMeasuredWidth() - mPadding - mProgressWidth / 2f
        );
        //刻度文字
        mPaint.getTextBounds("00", 0, "00".length(), mKeduTextRect);
        text2SideWidth = mPadding + mProgressWidth + dp2px(2);
        mTextRectF.set(
                text2SideWidth + mKeduTextRect.height(),
                text2SideWidth + mKeduTextRect.height(),
                getMeasuredWidth() - text2SideWidth - mKeduTextRect.height(),
                getMeasuredWidth() - text2SideWidth - mKeduTextRect.height()
        );
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**====画圆弧====**/
        mPaint.setStyle(Paint.Style.STROKE);
        //Paint.Cap.BUTT：无;Paint.Cap.SQUARE：方形;Paint.Cap.ROUND： 半圆形
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setColor(mProgressColors);
        canvas.drawArc(mProgressRectF, mStartAngle, mSweepAngle, false, mPaint);

        /**====画刻度====**/
        mPaint.setStrokeWidth(dp2px(3));
        float x0 = mCenterX;
        float y0 = mPadding + mProgressWidth;
        float x1 = mCenterX;
        float y1 = y0 + dp2px(3);

        //逆时针到开始处
        canvas.save();
        canvas.rotate(-mSweepAngle / 2 + mStartSpaceAngle, mCenterX, mCenterY);
        canvas.drawLine(x0, y0, x1, y1, mPaint);//第一个刻度线

        //分成7份,第一条已经画过了
        float degree = (mSweepAngle - mStartSpaceAngle * 2) * 1f / (mSection - 1);
        for (int i = 0; i < mSection - 1; i++) {
            canvas.rotate(degree, mCenterX, mCenterY);
            canvas.drawLine(x0, y0, x1, y1, mPaint);
        }
        canvas.restore();

        /**====画刻度文字====**/
        mPaint.setTextSize(sp2px(13));
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        for (int i = 0; i < mSection; i++) {
            // 粗略把文字的宽度视为圆心角2*θ对应的弧长，利用弧长公式得到θ，下面用于修正角度
            float θ = (float) (180 * mKeduTextRect.width() / 2 /
                    (Math.PI * (mRadius - text2SideWidth - mKeduTextRect.height())));
            mPath.reset();

            // 正起始角度减去θ使文字居中对准长刻度
            float startAngle;
            //位置稍微调整下，三位数宽一点
            if (i == 0) {
                startAngle = mStartAngle + i * (mSweepAngle / (mSection - 1)) - θ + 2;
            } else if (i == 1) {
                startAngle = mStartAngle + i * (mSweepAngle / (mSection - 1)) - θ - 1;
            } else {
                startAngle = mStartAngle + i * (mSweepAngle / (mSection - 1)) - θ - 3;
            }

            mPath.addArc(
                    mTextRectF,
                    startAngle,
                    mSweepAngle
            );
            //沿着圆弧path画刻度文字
            canvas.drawTextOnPath(String.valueOf(50 * i), mPath, 0, dp2px(12), mPaint);
        }


        /**====“下载速度”文字====**/
        int pointerRadius = mRadius / 10;//指针圆盘的半径
        mPaint.setTextSize(sp2px(13));
        mPaint.setColor(mXzsdColor);
        float xzsdWidth = mPaint.measureText(mXzsd);
        float xzsdY = mCenterY - pointerRadius - dp2px(8);
        canvas.drawText(mXzsd, mCenterX - xzsdWidth / 2, xzsdY, mPaint);


        /**====“实时速度”文字====**/
        if (!TextUtils.isEmpty(mHeaderText)) {
            mPaint.setTextSize(sp2px(16));
            mPaint.setColor(Color.BLACK);
            mPaint.getTextBounds(mHeaderText, 0, mHeaderText.length(), mSpeedTextRect);
            float speedTxtWidth = mSpeedTextRect.width();
            float speedTxtHeight = mSpeedTextRect.height();
            canvas.drawText(mHeaderText, mCenterX - speedTxtWidth / 2, xzsdY - dp2px(6) - speedTxtHeight, mPaint);
        }


        /**====画指针====**/
        float θ = (mStartAngle + 3) + (mSweepAngle - 3) * (mCurrentSpeed - mMinSpeed) / (mMaxSpeed - mMinSpeed); // 指针与水平线夹角
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_dark_light));
        canvas.drawCircle(mCenterX, mCenterY, pointerRadius, mPaint);
        mPaint.setStrokeWidth(pointerRadius / 3);

        mPaint.setColor(mProgressColors);
        mPaint.setAlpha(180);//透明 把字挡着了
        float[] p1 = getCoordinatePoint(mPLRadius, θ);
        canvas.drawLine(p1[0], p1[1], mCenterX, mCenterY, mPaint);
        float[] p2 = getCoordinatePoint(mPSRadius, θ + 180);
        canvas.drawLine(mCenterX, mCenterY, p2[0], p2[1], mPaint);
    }

    /**
     * 计算角度转弧度后的XY坐标
     *
     * @param radius
     * @param angle
     * @return
     */
    public float[] getCoordinatePoint(int radius, float angle) {
        float[] point = new float[2];
        double arcAngle = Math.toRadians(angle); //将角度转换为弧度
        if (angle < 90) {
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        } else if (angle > 90 && angle < 180) {
            arcAngle = Math.PI * (180 - angle) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        } else if (angle > 180 && angle < 270) {
            arcAngle = Math.PI * (angle - 180) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        } else if (angle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        } else {
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }
        return point;
    }


    /**
     * 获取当前进度
     *
     * @return
     */
    public int getProgress() {
        return mCurrentSpeed;
    }

    /**
     * 设置进度单位(kb/s)
     *
     * @param progress 传过来的是kb/s是网速
     */
    public void setProgress(int progress) {
        progress = progress * 8;
        if (mCurrentSpeed == progress || progress < mMinSpeed || progress > mMaxSpeed) {
            return;
        }
        //计算带宽
        mCurrentSpeed = progress;
        if (progress >= 1024) {
            mHeaderText = String.format("%.1f", (progress * 1f / 1000)) + "m/s";
        } else {
            mHeaderText = mCurrentSpeed + "k/s";
        }
        postInvalidate();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }
}
