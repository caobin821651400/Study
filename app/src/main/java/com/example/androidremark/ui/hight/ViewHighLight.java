package com.example.androidremark.ui.hight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidremark.R;

/**
 * @author: cb
 * @date: 2024/1/27 18:14
 * @desc: 描述
 */
public class ViewHighLight extends View {
     Bitmap bms; //source 原图
     Bitmap bmm; //mask 蒙版
     Paint paint;
    final int width = 4;
    final int step = 15; // 1...45
    int index = -1;
    int max = 15;
    int[] colors = new int[max];
    final int[] highlightColors = {0xfff00000,0,0xffff9922,0,0xff00ff00,0};

    public ViewHighLight(Context context) {
        super(context);
        initV();
    }

    public ViewHighLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initV();
    }

    public ViewHighLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initV();
    }


    private void initV() {
        bms = decodeBitmap(R.drawable.x_loading_empty);
        bmm = Bitmap.createBitmap(bms.getWidth(), bms.getHeight(), Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(bmm);
        canvas.drawBitmap(bms, 0, 0, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        postDelayed(() -> {
            shake();
        }, 3000);
    }

    private Bitmap decodeBitmap(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw blur shadow

        for (int i = 0; i < 360; i += step) {
            float x = width * (float) Math.cos(Math.toRadians(i));
            float y = width * (float) Math.sin(Math.toRadians(i));
            canvas.drawBitmap(bmm, x, y, paint);
        }
        canvas.drawBitmap(bms, 0, 0, null);

        if(index == -1){
            return;
        }
        index++;
        if(index > max +1){
            return;
        }
        if(index >= max){
            paint.setColor(Color.TRANSPARENT);
        }else{
            paint.setColor(colors[index]);
        }
        postInvalidateDelayed(200);
    }


    public void shake() {
        index = 0;
        for (int i = 0; i < max; i+=2) {
            colors[i] = highlightColors[i % highlightColors.length];
        }
        postInvalidate();
    }
}