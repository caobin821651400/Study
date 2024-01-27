package com.example.androidremark.ui.scale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.example.androidremark.R;

/**
 * @author: cb
 * @date: 2024/1/27 18:42
 * @desc: 描述
 */
public class ScaleBigView extends View {
    private final DisplayMetrics mDM;
    private TextPaint mCommonPaint;
    private Bitmap mBitmap;
    private Shader shader = null;
    private Matrix matrix = new Matrix();
    private Bitmap scaledBitmap;

    public ScaleBigView(Context context) {
        this(context, null);
    }

    public ScaleBigView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ScaleBigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDM = getResources().getDisplayMetrics();
        initPaint();
        setClickable(true); //触发hotspot
    }

    private void initPaint() {
        //否则提供给外部纹理绘制
        mCommonPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mCommonPaint.setAntiAlias(true);
        mCommonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCommonPaint.setStrokeCap(Paint.Cap.ROUND);
        mCommonPaint.setFilterBitmap(true);
        mCommonPaint.setDither(true);
        mCommonPaint.setStrokeWidth(dp2px(20));
        mBitmap = decodeBitmap(R.drawable.bg_monkey);

    }

    private Bitmap decodeBitmap(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = mDM.widthPixels / 2;
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = widthSize / 2;
        }
        setMeasuredDimension(widthSize, heightSize);

    }
    private float x;
    private float y;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width < 1 || height < 1) {
            return;
        }
        if (shader == null) {
            float ratio  = 1.2f;
            scaledBitmap = Bitmap.createScaledBitmap(mBitmap, (int) (mBitmap.getWidth() * ratio), (int) (mBitmap.getHeight() * ratio), true);
            shader =  new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        }
        //放大平移
        float offsetX = -(scaledBitmap.getWidth() - mBitmap.getWidth()) / 2f;
        float offsetY = -(scaledBitmap.getHeight() - mBitmap.getHeight())/2f;

        //窥视镜圆心
        float  mirrorCenterX = mBitmap.getWidth() - width / 4f;
        float  mirrorCenterY =  mBitmap.getHeight() - width/4f;

        //(mirrorCenterX - x) ，(mirrorCenterY-y) 是把当前中心点的图像平移到圆心哪里
        matrix.setTranslate( offsetX + (mirrorCenterX - x)  , offsetY + (mirrorCenterY-y));
        shader.setLocalMatrix(matrix);

        int save = canvas.save();
        canvas.clipRect(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        canvas.drawBitmap(mBitmap, 0, 0, null);
        mCommonPaint.setColor(Color.DKGRAY);
        canvas.drawCircle(mirrorCenterX , mirrorCenterY,width/4f,mCommonPaint);
        mCommonPaint.setShader(shader);
        canvas.drawCircle( mirrorCenterX , mirrorCenterY,width/4f,mCommonPaint);
        mCommonPaint.setShader(null);

        canvas.restoreToCount(save);
    }

    @Override
    public void dispatchDrawableHotspotChanged(float x, float y) {
        super.dispatchDrawableHotspotChanged(x, y);
        this.x = x;
        this.y = y;
        postInvalidate();
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        postInvalidate();
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mDM);
    }

}
