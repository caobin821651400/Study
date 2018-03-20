package com.example.androidremark.ui2.sinmath;

import android.os.Bundle;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

public class SinActivity extends BaseActivity {

    MySinView mySinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin);
        initView();

    }

    private void initView() {
        mySinView = (MySinView) findViewById(R.id.sin_view);
    }
}
