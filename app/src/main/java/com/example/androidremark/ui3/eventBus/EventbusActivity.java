package com.example.androidremark.ui3.eventBus;

import android.os.Bundle;
import android.view.View;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

public class EventbusActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        initView();
    }

    private void initView() {

    }

    public void send(View view) {
        EventBusBean bean = new EventBusBean("你好");
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
