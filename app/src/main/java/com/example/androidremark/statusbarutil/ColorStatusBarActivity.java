package com.example.androidremark.statusbarutil;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.StatusBarUtil;

import java.util.Random;

/**
 * Created by Jaeger on 16/2/14.
 *
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public class ColorStatusBarActivity extends BaseActivity {
    private Toolbar mToolbar;
    private Button mBtnChangeColor;
    private SeekBar mSbChangeAlpha;
    private TextView mTvStatusAlpha;

    private int mColor;
    private int mAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_status_bar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnChangeColor = (Button) findViewById(R.id.btn_change_color);
        mTvStatusAlpha = (TextView) findViewById(R.id.tv_status_alpha);
        mSbChangeAlpha = (SeekBar) findViewById(R.id.sb_change_alpha);

        // 设置toolbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 改变颜色
        mBtnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                mColor = 0xff000000 | random.nextInt(0xffffff);
                mToolbar.setBackgroundColor(mColor);
                StatusBarUtil.setColor(ColorStatusBarActivity.this, mColor, mAlpha);
            }
        });

        mSbChangeAlpha.setMax(255);
        mSbChangeAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAlpha = progress;
                StatusBarUtil.setColor(ColorStatusBarActivity.this, mColor, mAlpha);
                mTvStatusAlpha.setText(String.valueOf(mAlpha));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSbChangeAlpha.setProgress(StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    protected void setStatusBar() {
        mColor = getResources().getColor(R.color.colorPrimary);
        StatusBarUtil.setColor(this, mColor);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
