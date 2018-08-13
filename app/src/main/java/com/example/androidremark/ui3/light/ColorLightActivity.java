package com.example.androidremark.ui3.light;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.view.BlurMaskCircularView;
import com.example.androidremark.view.colorpicker.ColorPicker;

/**
 * 七彩灯
 */
public class ColorLightActivity extends BaseActivity {

    private BlurMaskCircularView mMaskFilterView;//中间的发光圆
    private ColorPicker mColorPicker;
    private AppCompatSeekBar mSeekBar1, mSeekBar2, mSeekBar3;
    private TextView tvSeekBar1, tvSeekBar2, tvSeekBar3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_light);
        initUI();
    }

    public void initUI() {
        findView();
        initListener();

        mColor = mMaskFilterView.getColor();
        mColorPicker.setColor(mColor);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //mColorPicker.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }

    /**
     *
     */
    private void findView() {
        mMaskFilterView = findViewById(R.id.mask_view);
        mColorPicker = findViewById(R.id.color_picker);
        mSeekBar1 = findViewById(R.id.seek1);
        mSeekBar2 = findViewById(R.id.seek2);
        mSeekBar3 = findViewById(R.id.seek3);
        tvSeekBar1 = findViewById(R.id.tv_seek1);
        tvSeekBar2 = findViewById(R.id.tv_seek2);
        tvSeekBar3 = findViewById(R.id.tv_seek3);
    }

    private int mColor;
    private float mHue;//色调范围0-360
    private float mSat;//饱和度范围0-1
    private float mVal;//亮度范围0-1

    /**
     * 事件监听
     */
    private void initListener() {
        //取色盘提取颜色（松开手）
        mColorPicker.setOnColorSelectedListener(new ColorPicker.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                mColor = color;
            }
        });
        //取色盘提取颜色（不松开手）
        mColorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                mMaskFilterView.setColor(color);
//                float[] hsv = new float[3];
//                Color.colorToHSV(color, hsv);
//                mHue = hsv[0];
//                mSat = hsv[1];
//                mVal = hsv[2];
            }
        });
        //饱和度
        mSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tvSeekBar1.setText(progress + "%");
                float[] hsv = new float[3];
                Color.colorToHSV(mColor, hsv);
                mHue = hsv[0];
                mSat = (float) progress / seekBar.getMax();
                mVal = hsv[2];
                mMaskFilterView.setColor(Color.HSVToColor(new float[]{mHue, mSat, mVal}));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //亮度
        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tvSeekBar2.setText(progress + "%");
                float[] hsv = new float[3];
                Color.colorToHSV(mColor, hsv);
                mHue = hsv[0];
                mSat = hsv[1];
                mVal = (float) progress / seekBar.getMax();
                if (mVal < 0.35) {
                    mVal = 0.35f;
                }
                mMaskFilterView.setColor(Color.HSVToColor(new float[]{mHue, mSat, mVal}));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //色调
        mSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tvSeekBar3.setText(progress + "%");
                float[] hsv = new float[3];
                Color.colorToHSV(mColor, hsv);
                mHue = (float) (3.6 * progress);
                mSat = hsv[1];
                mVal = hsv[2];
                mMaskFilterView.setColor(Color.HSVToColor(new float[]{mHue, mSat, mVal}));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
