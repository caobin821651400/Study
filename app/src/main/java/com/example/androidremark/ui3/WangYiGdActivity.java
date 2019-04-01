package com.example.androidremark.ui3;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.MyUtils;
import com.example.androidremark.utils.StatusBarUtil;
import com.example.androidremark.utils.StatusBarUtils;

public class WangYiGdActivity extends BaseActivity {
    private int mOffset = 0;
    private int mScrollY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wang_yi_gd);
        initView();
    }


    @Override
    protected void setStatusBar() {
    }

    private void initView() {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final View buttonBar = findViewById(R.id.buttonBarLayout);
        final View bgImage = findViewById(R.id.parallax);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //状态栏透明和间距处理
        StatusBarUtils.immersive(this);
        StatusBarUtils.setPaddingSmart(this, toolbar);
        buttonBar.setAlpha(0);
        toolbar.setBackgroundColor(0);

        final NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = MyUtils.dp2px(getApplicationContext(), 200);//滑动多高才完全变色
            private int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("哈哈 ",scrollY+"");
                Log.v("固定高 ",h+"");
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBar.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                   // bgImage.setTranslationY(-mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
    }
}
