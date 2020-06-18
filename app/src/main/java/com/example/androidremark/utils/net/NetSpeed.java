package com.example.androidremark.utils.net;

import android.net.TrafficStats;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/17 16:57
 * @Desc :获取某个时刻的网速
 * ====================================================
 */
public class NetSpeed {

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    /**
     * @param uid
     * @return 单位kb/s
     */
    public long getNetSpeed(int uid) {
        long nowTotalRxBytes = getTotalRxBytes(uid);
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return speed;
    }


    //getApplicationInfo().uid
    public long getTotalRxBytes(int uid) {
        return TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }


//    private lateinit var mNetSpeedTimer: NetSpeedTimer
//
//            //创建NetSpeedTimer实例
//            mNetSpeedTimer = NetSpeedTimer(this, NetSpeed(), mHandler).setDelayTime(1000).setPeriodTime(2000)
//    //在想要开始执行的地方调用该段代码
//            mNetSpeedTimer.startSpeedTimer()
//
//
//    @SuppressLint("HandlerLeak")
//    private val mHandler = object : Handler() {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            if (msg.what === NetSpeedTimer.NET_SPEED_TIMER_DEFAULT) {
//                val speed = msg!!.obj as String
//                //打印你所需要的网速值，单位默认为kb/s
//                XLogUtils.i("current net speed  = $speed")
//            }
//        }
//    }
//
//
//    override fun onDestroy() {
//        if (null != mNetSpeedTimer) {
//            mNetSpeedTimer.stopSpeedTimer()
//        }
//        //handler
//        super.onDestroy()
//    }
}
