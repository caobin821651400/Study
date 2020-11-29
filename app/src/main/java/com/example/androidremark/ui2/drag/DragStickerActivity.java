package com.example.androidremark.ui2.drag;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.bean.GroupChildBean;
import com.example.androidremark.bean.GroupChildBean.ResultMsgBean.GroupListBean.ChildListBean;
import com.example.androidremark.ui2.grouprecycler.GroupRecyAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DragStickerActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_sticker);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "分组recycler", true);


    }
}

