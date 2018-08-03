package com.example.androidremark.ui3.eventBus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventbusActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(EventBusBean event) {
        /* Do something */
        System.err.println("哈哈 " + event.getName());
      //  ((TextView) findViewById(R.id.text)).setText(event.getName());
    }

    public void send(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
