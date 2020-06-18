package com.example.androidremark.utils.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/17 16:59
 * @Desc :定时获取网速
 * ====================================================
 */
public class NetSpeedTimer {
    private long defaultDelay = 1000;
    private long defaultPeriod = 1000;
    private static final int ERROR_CODE = -101011010;
    private int mMsgWhat = ERROR_CODE;
    private NetSpeed mNetSpeed;
    private Handler mHandler;
    private Context mContext;
    private SpeedTimerTask mSpeedTimerTask;
    private Timer mTimer;

    public static final int NET_SPEED_TIMER_DEFAULT = 101010;

    public NetSpeedTimer(Context context, NetSpeed netSpeed, Handler handler) {
        this.mContext = context;
        this.mNetSpeed = netSpeed;
        this.mHandler = handler;
    }

    public NetSpeedTimer setDelayTime(long delay) {
        this.defaultDelay = delay;
        return this;
    }

    public NetSpeedTimer setPeriodTime(long period) {
        this.defaultPeriod = period;
        return this;
    }

    public NetSpeedTimer setHanderWhat(int what) {
        this.mMsgWhat = what;
        return this;
    }

    /**
     * 开启获取网速定时器
     */
    public void startSpeedTimer() {
        mTimer = new Timer();
        mSpeedTimerTask = new SpeedTimerTask(mContext, mNetSpeed, mHandler,
                mMsgWhat);
        mTimer.schedule(mSpeedTimerTask, defaultDelay, defaultPeriod);
    }

    /**
     * 关闭定时器
     */
    public void stopSpeedTimer() {
        try {
            if (null != mSpeedTimerTask) {
                mSpeedTimerTask.cancel();
            }
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author 静态内部类
     */
    private static class SpeedTimerTask extends TimerTask {
        private int mMsgWhat;
        private NetSpeed mNetSpeed;
        private Handler mHandler;
        private Context mContext;

        public SpeedTimerTask(Context context, NetSpeed netSpeed,
                              Handler handler, int what) {
            this.mContext = context;
            this.mHandler = handler;
            this.mNetSpeed = netSpeed;
            this.mMsgWhat = what;
        }

        @Override
        public void run() {
            if (null != mNetSpeed && null != mHandler) {
                Message obtainMessage = mHandler.obtainMessage();
                if (mMsgWhat != ERROR_CODE) {
                    obtainMessage.what = mMsgWhat;
                } else {
                    obtainMessage.what = NET_SPEED_TIMER_DEFAULT;
                }
                obtainMessage.obj = mNetSpeed.getNetSpeed(mContext
                        .getApplicationInfo().uid);
                mHandler.sendMessage(obtainMessage);
            }
        }
    }
}
