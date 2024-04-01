package com.example.androidremark.ui3.drag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author: cb
 * @date: 2024/3/29 11:31
 * @desc: 描述
 */
public class GestureView extends View {
    private ArrayList<PointF> pointList;
    private int maxPoints = 6;
    private Paint pointPaint;
    private Paint regionPaint;
    private float touchTolerance = 100;
    private PointF startPoint; // 手势操作起始点
    private PointF endPoint; // 手势操作结束点


    public GestureView(Context context) {
        super(context);
        init();
    }

    public GestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ResourceAsColor")
    private void init() {
        pointList = new ArrayList<>();

        //描点
        pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setTextSize(50);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setAntiAlias(true);

        //区域
        regionPaint = new Paint();
        regionPaint.setARGB(60, 135, 206, 255);
        regionPaint.setStyle(Paint.Style.FILL);
        regionPaint.setAlpha(100);
        regionPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (pointList.isEmpty()) {
            return;
        }

        //所有点连接起来
        Path path = new Path();
        //从第一个点开始
        path.moveTo(pointList.get(0).x, pointList.get(0).y);
        for (int i = 1; i < pointList.size(); i++) {
            path.lineTo(pointList.get(i).x, pointList.get(i).y);
        }
        path.close();
        //得到范围边框路径path
        canvas.drawPath(path, regionPaint);
        //绘制每个点的数字
        for (int i = 0; i < pointList.size(); i++) {
            PointF point = pointList.get(i);
            @SuppressLint("DrawAllocation")
            CustomPointF customPointF = new CustomPointF(point, 40, Color.WHITE, Color.BLACK);
            customPointF.draw(canvas, pointPaint, i + 1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startPoint = new PointF(x, y); // 记录手势操作的起始点
                if (!isInRegion(x, y) && !isBetweenPoint(x, y)) {
                    //如果不是在区域内并且不在描点附近
                    if (pointList.size() < maxPoints) {
                        pointList.add(new PointF(x, y));
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                endPoint = new PointF(x, y); // 记录手势操作的结束点
                // 如果在区域内
                if (isInRegion(startPoint.x, startPoint.y) && !isBetweenPoint(x, y)) {
                    moveRegion();
                } else {
                    for (PointF point : pointList) {
                        //如果在描点附近
                        if (isBetweenPoint(point, x, y)) {
                            //对描点移动区域进行限制 0-getWidth()/getHeight()
                            x = Math.max(0, Math.min(x, getWidth()));
                            y = Math.max(0, Math.min(y, getHeight()));
                            point.x = x;
                            point.y = y;
                            invalidate();
                            break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                updateRegion();
                break;
        }
        return true;
    }


    //清除描点
    public void clear() {
        pointList.clear();
        invalidate();
    }

    //是否在描点附近
    private boolean isBetweenPoint(PointF point, float x, float y) {
        return Math.sqrt(Math.pow(x - point.x, 2) + Math.pow(y - point.y, 2)) <= touchTolerance;
    }

    //是否在描点附近并且不在区域内
    private boolean isBetweenPoint(float x, float y) {
        for (PointF point : pointList) {
            //如果在区域外
            if (isBetweenPoint(point, x, y)) {
                return true;
            }
        }
        return false;
    }

    //是否在区域内
    private boolean isInRegion(float a, float b) {
        int x, y;
        x = (int) a;
        y = (int) b;
        if (pointList.size() >= 3) {
            Path path = new Path();
            path.moveTo(pointList.get(0).x, pointList.get(0).y);
            for (int i = 1; i < pointList.size(); i++) {
                path.lineTo(pointList.get(i).x, pointList.get(i).y);
            }
            path.close();

            Region region = new Region();
            region.setPath(path, new Region(0, 0, getWidth(), getHeight()));

            return region.contains(x, y);
        }
        return false;
    }

    //更新区域
    private void updateRegion() {
        if (pointList.size() >= 3) {
            Path path = new Path();
            path.moveTo(pointList.get(0).x, pointList.get(0).y);
            for (int i = 1; i < pointList.size(); i++) {
                path.lineTo(pointList.get(i).x, pointList.get(i).y);
            }
            path.close();

            Region region = new Region();
            region.setPath(path, new Region(0, 0, getWidth(), getHeight()));

            if (isRegionValid()) {
                Toast.makeText(getContext(), "区域无效", Toast.LENGTH_SHORT).show();
            } else {
                // 区域有效，移动区域
            }
            startPoint = null;
        }
    }

    //移动区域
    private void moveRegion() {
        float offsetX = endPoint.x - startPoint.x; // 计算X轴位移量
        float offsetY = endPoint.y - startPoint.y; // 计算Y轴位移量
        //判断位移后是否超出范围
        for (PointF pointF : pointList) {
            if ((pointF.x + offsetX) < 0) {
                offsetX = -pointF.x;
            }
            if ((pointF.x + offsetX) > getWidth()) {
                offsetX = getWidth() - pointF.x;
            }
            if ((pointF.y + offsetY) < 0) {
                offsetY = -pointF.y;
            }
            if ((pointF.y + offsetY) > getHeight()) {
                offsetY = getHeight() - pointF.y;
            }
        }
        for (PointF pointF : pointList) {
            pointF.x += offsetX;
            pointF.y += offsetY;
        }
        startPoint = endPoint;
        invalidate();
    }

    //判断闭合区域的有效性
    public boolean isRegionValid() {
        if (pointList.size() < 3) {
            return true;
        }

        //遍历闭合区域中的所有线段
        for (int i = 0; i < pointList.size(); i++) {
            PointF p1 = pointList.get(i);//当前点
            PointF p2 = pointList.get((i + 1) % pointList.size());//下一个点

            for (int j = i + 2; j < pointList.size(); j++) {
                PointF p3 = pointList.get(j);
                PointF p4 = pointList.get((j + 1) % pointList.size());

                if (isSegmentsIntersect(p1, p2, p3, p4)) {
                    return true;
                }
            }
        }

        return false;
    }

    //当两条线段相交时，其叉积的乘积应为负数。如果两条线段平行或共线，则它们的叉积为0，无法判断相交性
    private boolean isSegmentsIntersect(PointF p1, PointF p2, PointF p3, PointF p4) {
        float v1 = crossProduct(p3, p4, p1);
        float v2 = crossProduct(p3, p4, p2);
        float v3 = crossProduct(p1, p2, p3);
        float v4 = crossProduct(p1, p2, p4);

        return (v1 * v2 < 0) && (v3 * v4 < 0);
    }

    //如果条件成立，则表示两条线段相交；如果条件不成立，则表示两条线段不相交。
    private float crossProduct(PointF p1, PointF p2, PointF p) {
        return (p.x - p1.x) * (p2.y - p1.y) - (p.y - p1.y) * (p2.x - p1.x);
    }
}