package com.example.androidremark.ui.imageview;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.StatusBarUtil;
import com.example.androidremark.view.DragGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 橡皮擦 一般用于抽奖
 */
public class ScratchActivity extends BaseActivity {

    private Toolbar toolbar;

    private AppDragAdapter selectAdapter;
    private DragGridView selectGridView;

    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "橡皮擦view", true);
        toolbar.setBackgroundResource(R.color.green_deep_color);
        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            mList.add("我是" + i);
        }
        selectGridView = (DragGridView) findViewById(R.id.drag_grid_view);

        selectAdapter = new AppDragAdapter(this);
        selectGridView.setAdapter(selectAdapter);

        selectAdapter.setmList(mList);

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.green_deep_color),30);
    }
}
