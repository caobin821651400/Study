package com.example.androidremark.ui.lizi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author: cb
 * @date: 2024/1/27 17:59
 * @desc: 描述
 */
public class VortexView   extends View {

    private Paint mPaint;
    private ArrayList<Circle> mParticles;
    private ArrayList<RainDrop> mRainDrops;
    private long mLastUpdateTime;
    private int padding = 20;

    public VortexView(Context context) {
        this(context,null);
    }

    public VortexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VortexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initV();
    }


    private void initV() {
        mPaint = new Paint();
        mParticles = new ArrayList<>();
        mRainDrops = new ArrayList<>();
        mLastUpdateTime = System.currentTimeMillis();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = Math.min(getWidth(), getHeight()) / 3f;

        // 创建新的雨滴
        if (mRainDrops.size() < 80) {
            int num = getWidth() / padding;
            double nth = num * Math.random() * padding;
            double x = nth + padding / 2f * Math.random();
            RainDrop drop = new RainDrop((float) x, -50f);
            mRainDrops.add(drop);
        }

        // 创建新的粒子
        if (mParticles.size() < 100) {
            float x = (float) (getWidth() / 2f - radius + 2*radius * Math.random());
            float y = (float) (getHeight()/2f - radius + 2*radius * Math.random() );

            Circle particle = new Circle(x, y,5);
            mParticles.add(particle);
        }

        // 绘制雨滴
        mPaint.setColor(Color.WHITE);
        for (RainDrop drop : mRainDrops) {
            canvas.drawLine(drop.x, drop.y, drop.x, drop.y + 20, mPaint);
        }

        // 绘制粒子
        for (Circle particle : mParticles) {
            mPaint.setColor(particle.color);
            canvas.drawCircle(particle.x, particle.y, particle.radius, mPaint);
        }

        // 更新雨滴位置
        Iterator<RainDrop> rainIterator = mRainDrops.iterator();
        while (rainIterator.hasNext()) {
            RainDrop drop = rainIterator.next();
            if (drop.y > getHeight() + 50) {
                int num = getWidth() / padding;
                double nth = num * Math.random() * padding;
                double x = nth + padding * Math.random();

                drop.x = (float) (x);
                drop.y = -50;
            } else {
                drop.y += 20;
            }

        }

        // 更新粒子位置
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - mLastUpdateTime) / 1000f;
        mLastUpdateTime = currentTime;

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        Iterator<Circle> iterator = mParticles.iterator();
        while (iterator.hasNext()) {
            Circle particle = iterator.next();
            float dx = particle.x - centerX;
            float dy = particle.y - centerY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy) + 3.5f;//  增加偏移
            float angle = (float) Math.atan2(dy, dx) + deltaTime * 0.65f;
            particle.radius += 1f;

            particle.x = centerX + (float) Math.cos(angle) * distance;
            particle.y = centerY + (float) Math.sin(angle) * distance;

            if (particle.radius > 10) {
                int maxRadius = 100;
                float fraction = (particle.radius - 10) / (maxRadius - 10);
                if (fraction >= 1) {
                    fraction = 1;
                }
                particle.color = argb((int) (255 * (1 - fraction)), Color.red(particle.color), Color.green(particle.color), Color.blue(particle.color));
            }
            if (Color.alpha(particle.color) == 0) {

                float x = (float) (getWidth() / 2f - radius + 2* radius * Math.random());
                float y = (float) (getHeight()/2f - radius + 2*radius * Math.random() );
                particle.reset(x,y, 5);
            }

        }

        Collections.sort(mParticles, comparator);

        // 使view无效从而重新绘制，实现动画效果
        invalidate();
    }
    Comparator comparator = new Comparator<Circle>() {
        @Override
        public int compare(Circle left, Circle right) {
            return (int) (left.radius - right.radius);
        }
    };

    public static int argb(
            int alpha,
            int red,
            int green,
            int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    private static class Circle {
        float x;
        float y;
        int color;

        float radius;

        Circle(float x, float y, float radius) {
            reset(x, y, radius);
        }

        private void reset(float x, float y, float radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        }
    }

    private static class RainDrop {
        float x;
        float y;

        RainDrop(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
