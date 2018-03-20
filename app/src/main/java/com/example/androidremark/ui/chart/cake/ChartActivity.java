package com.example.androidremark.ui.chart.cake;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.bean.CakeBean;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends BaseActivity {
    private Toolbar toolbar;
    private List<CakeBean> beans;
    private String[] names = {"php", "object-c", "c", "c++", "java", "android", "linux"};
    private float[] values = {2f, 2f, 3f, 4f, 5f, 6f, 7f};
    private int[] colArrs = {Color.RED, Color.parseColor("#4ebcd3"), Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.parseColor("#f68b2b"), Color.parseColor("#6fb30d")};//圆弧颜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        beans = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            CakeBean bean = new CakeBean();
            bean.name = names[i];
            bean.value = values[i];
            bean.mColor = colArrs[i];
            beans.add(bean);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        CakeView cake_view = (CakeView) findViewById(R.id.cake_view);
        cake_view.setData(beans);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "自定义View", true);
    }

}
