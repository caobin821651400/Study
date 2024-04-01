package com.example.androidremark.ui3.photocropper;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

/**
 * @author: cb
 * @date: 2024/3/11 12:02
 * @desc: 描述
 */
public class PhotocropperActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photocropper);

        StandJumpRangeView view = findViewById(R.id.drag_view);

        view.setData();
    }
}
