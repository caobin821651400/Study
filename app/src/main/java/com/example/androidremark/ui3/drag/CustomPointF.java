package com.example.androidremark.ui3.drag;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * @author: cb
 * @date: 2024/3/29 11:31
 * @desc: 描述
 */
public class CustomPointF {
    private PointF point;
    private int circleRadius;
    private int circleColor;
    private int textColor;

    public CustomPointF(PointF point, int circleRadius, int circleColor, int textColor) {
        this.point = point;
        this.circleRadius = circleRadius;
        this.circleColor = circleColor;
        this.textColor = textColor;
    }

    public void draw(Canvas canvas, Paint paint, int number) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(circleColor);
        canvas.drawCircle(point.x, point.y, circleRadius, paint);

        paint.setColor(textColor);
        paint.setTextSize(circleRadius);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(number), point.x, point.y + (circleRadius / 2), paint);
    }
}
