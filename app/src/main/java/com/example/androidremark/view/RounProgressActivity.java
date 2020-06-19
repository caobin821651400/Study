package com.example.androidremark.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.net.NetSpeed;
import com.example.androidremark.utils.net.NetSpeedTimer;
import com.example.androidremark.ui3.dashboard.DashBoardView;

public class RounProgressActivity extends BaseActivity {

    private RoundProgressBar progressBarSuccess;
    private DashBoardView dashboard;


    //测速
    private NetSpeedTimer mNetSpeedTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roun_progress);


        progressBarSuccess = findViewById(R.id.progress_bar_success);

        dashboard = findViewById(R.id.dashboard);


        //测速
        mNetSpeedTimer = new NetSpeedTimer(this, new NetSpeed(), mHandler).setDelayTime(1000).setPeriodTime(1000);
//        mNetSpeedTimer.startSpeedTimer();
    }

    private int progress = 0;

    private Button button;
    private boolean isStop = false;

    public void aaaa(View view) {
        button = (Button) view;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    mHandler.sendEmptyMessage(1);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();
    }


    public void stop(View view) {
        isStop = !isStop;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                progress += 500*8;
                dashboard.setProgress(progress);
                button.setText("网速*8："+progress);
            } else if (msg.what == NetSpeedTimer.NET_SPEED_TIMER_DEFAULT) {
                long speed = (long) msg.obj;
                Log.d("网速-->", speed + "");
                dashboard.setProgress((int) speed);
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (null != mNetSpeedTimer) {
            mNetSpeedTimer.stopSpeedTimer();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

}
