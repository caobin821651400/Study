package com.example.androidremark.ui.layout;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.StatusBarUtil;

public class CoordinatorLayoutActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ImageView imageView;
    private Button testBtn;

    private NestedScrollView mNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        initView();


    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.image_view);
        testBtn = (Button) findViewById(R.id.btn_test);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_view);

//        mToolbar.set
        //显示返回按键
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("哈哈 ");
            }
        });


//        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.d("ScrollView滑动监听1",scrollX+"");
//                Log.d("ScrollView滑动监听2",scrollY+"");
//                Log.d("ScrollView滑动监听3",oldScrollX+"");
//                Log.d("ScrollView滑动监听4",oldScrollY+"");
//
//            }
//        });

    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(this,80);
    }
}
