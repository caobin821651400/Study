package com.example.androidremark.ui2.xloadingdialog;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * 加载dialog
 */
public class XLoadingDialogActivity extends BaseActivity {
    private MyHandler myHandler;

    static class MyHandler extends Handler {

        WeakReference<XLoadingDialogActivity> mActivity;

        public MyHandler(XLoadingDialogActivity activity) {
            mActivity = new WeakReference<XLoadingDialogActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            XLoadingDialogActivity activity = mActivity.get();
            XLoadingDialog.with(activity).dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xloading_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "XLoadingDialog", true);
        myHandler = new MyHandler(this);
    }

    public void show(View view) {
        switch (view.getId()) {
            case R.id.loading1:
                XLoadingDialog.with(this).show();
                break;
            case R.id.loading2:
                XLoadingDialog.with(this)
                        .setBackgroundColor(Color.parseColor("#aa000000"))
                        .setMessageColor(Color.WHITE)
                        .setCanceled(false)
                        .show();
                myHandler.sendEmptyMessageDelayed(1, 3000);
                break;
            case R.id.loading3:
                XLoadingDialog.with(this)
                        .setOrientation(XLoadingDialog.VERTICAL)
                        .setMessage("加载中...")
                        .show();
                break;
            case R.id.loading4:
                XLoadingDialog.with(this)
                        .setCanceled(false)
                        .setOrientation(XLoadingDialog.VERTICAL)
                        .setBackgroundColor(Color.parseColor("#aa000000"))
                        .setMessageColor(Color.WHITE)
                        .setMessage("加载中...")
                        .show();
                myHandler.sendEmptyMessageDelayed(1, 3000);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}

