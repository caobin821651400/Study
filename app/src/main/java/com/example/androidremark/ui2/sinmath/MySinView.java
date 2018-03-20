package com.example.androidremark.ui2.sinmath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidremark.R;
import com.example.androidremark.utils.MyUtils;

/**
 * 正弦函数,成长轨迹
 * Created by cb on 2017/10/24.
 */

public class MySinView extends View {

    Paint paint;//画波浪线
    Paint dotPaint;//画点
    Paint bitmapPaint;
    Paint txtPaint;
    //左侧偏移量
    private float leftMargin;
    //右侧偏移量
    private float rightMarMargin;
    //波浪中心点向下偏移量
    private int yBottomMargin;
    //每个点占整条线X轴的比例
    private float datSpaceScale = 0.22f;
    //圆点半径
    private int radius = 15;
    //sin函数振幅
    private int sinAmplitude = 20;
    //用户的积分
    private int currentFraction = 350;
    //点和点之间的分数间隔
    private int spaceFraction = 100;
    //蓝色向上箭头
    private Bitmap blueArrowBitmap;
    //蓝色箭头的高度
    private int blueArrowHeight;
    //蓝色箭头离波浪线中心点的高度
    private int blueArrowToLine = 100;
    //字体大小
    private float bottomTxtSize;

    private int[] grayId = {R.drawable.ic_gray_t1, R.drawable.ic_gray_t2, R.drawable.ic_gray_t3,
            R.drawable.ic_gray_t4, R.drawable.ic_gray_t5};
    private int[] yellowId = {R.drawable.ic_yellow_t1, R.drawable.ic_yellow_t2, R.drawable.ic_yellow_t3,
            R.drawable.ic_yellow_t4, R.drawable.ic_yellow_t5};

    public MySinView(Context context) {
        this(context, null);
    }

    public MySinView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        leftMargin = MyUtils.dp2px(context, 20);
        rightMarMargin = MyUtils.dp2px(context, 30);
        yBottomMargin = MyUtils.dp2px(context, 10);
        bottomTxtSize = MyUtils.dp2px(context, 16);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        //渐变色
        Shader shader = new LinearGradient(0, 0, 800, 800, Color.parseColor("#30F3EE"),
                Color.parseColor("#B936F4"), Shader.TileMode.MIRROR);
        paint.setShader(shader);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.FILL);

        //
        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setColor(Color.BLUE);
        dotPaint.setStyle(Paint.Style.FILL);

        bitmapPaint = new Paint();
        bitmapPaint.setStyle(Paint.Style.FILL);
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setFilterBitmap(true);

        blueArrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_up_blue_arrow);
        blueArrowHeight = blueArrowBitmap.getHeight();

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(Color.parseColor("#575757"));
        txtPaint.setTextSize(bottomTxtSize);
        txtPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //波浪线的宽度
        final int mWidth = getWidth() - (int) rightMarMargin;
        //view的高度
        final int height = getHeight();
        System.err.println("哈哈  "+height);
        //Y轴中心乡下偏移30PX
        final int centerY = height / 2 + yBottomMargin;
        for (int i = 0; i < mWidth; i++) {
            //sin函数，25是振幅，sin函数最低点-最高点的绝对值大小
            int y = (int) (centerY - sin(i) * sinAmplitude);
            //画波浪线
            canvas.drawPoint(i + leftMargin, y, paint);
        }
        //点X轴的间隔
        int dotSpace = (int) (mWidth * datSpaceScale);
        //画五个点,
        for (int i = 0; i < mWidth; i++) {
            int ii = (i % dotSpace);
            //position是第几个点
            int position = i / dotSpace;
            if (ii == 0) {//刚好是倍数的时候画点
                int datY = (int) (centerY - sin(i) * sinAmplitude);//每个点的Y坐标
                canvas.drawCircle(leftMargin + position * dotSpace, datY, radius, dotPaint);
                //画蓝色的箭头
                canvas.drawBitmap(blueArrowBitmap, leftMargin + position * dotSpace - 10, centerY - blueArrowToLine, bitmapPaint);
                //画ti,t2 t3 t4 t5
                if (currentFraction < position * spaceFraction) {//当前积分没到达该点
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), grayId[position]);
                    int bitWidth = bitmap.getWidth();
                    canvas.drawBitmap(bitmap, leftMargin + position * dotSpace - bitWidth / 2 - 10, centerY -
                            (blueArrowToLine + blueArrowHeight + 30), bitmapPaint);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), yellowId[position]);
                    int bitWidth = bitmap.getWidth();
                    canvas.drawBitmap(bitmap, leftMargin + position * dotSpace - bitWidth / 2 - 10, centerY -
                            (blueArrowToLine + blueArrowHeight + 30), bitmapPaint);
                }
                //画点下面的数字
                int txtWith = (int) txtPaint.measureText(position * spaceFraction + "");
                canvas.drawText(position * spaceFraction + "", leftMargin + position * dotSpace - txtWith / 2,
                        centerY + 120, txtPaint);
            }
        }
    }

    /**
     * sin函数
     *
     * @param degree x坐标
     * @return
     */
    private double sin(double degree) {
        return Math.sin(degree * Math.PI / 180);
    }

    public int getResourceId(String imageName, Context context) {
        int resId = getResources().getIdentifier(imageName, "drawable-xhdpi", context.getPackageName());
        return resId;
    }
}
