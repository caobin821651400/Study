package com.example.androidremark.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static com.example.androidremark.R.id.btn_receiver;
import static com.example.androidremark.R.id.btn_send;

/**
 * socket练习
 */
public class SocketTestActivity extends BaseActivity {

    private EditText receiverEd;
    private Button sendTv, recevierBtn;
    private TextView receiverMsg;
    private Socket mSocket = null;
    private SocketConnectionThread mSocketConnectionThread;
    private HeartBeatThread mHeartBeatThread;
    private boolean receiveStop;

    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    private boolean mServerIsConnection = false;//服务器是否在连接状态
    private long sleepTime = 6 * 1000;//线程休眠时间
    private long cycTime = 6 * 1000;//定时向服务器发送心跳包
    private long lastSendTime = 0;//每发送完成记录当前时间


    private static final String IP = "10.206.14.210";
    private MyHandler mHandler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
        initView();
    }

    private void initView() {

        receiverEd = (EditText) findViewById(R.id.tv_show);
        sendTv = (Button) findViewById(btn_send);
        recevierBtn = (Button) findViewById(btn_receiver);
        receiverMsg = (TextView) findViewById(R.id.tv_receiver);
        mHandler = new MyHandler(this);


        sendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SocketConnectionThread().start();
            }
        });
    }

    /**
     * 服务连接线程
     */
    private class SocketConnectionThread extends Thread {

        @Override
        public void run() {
            try {
                while (!mServerIsConnection) {
                    System.err.println("重新连接。。。");
                    mSocket = new Socket();
                    SocketAddress socketAddress = new InetSocketAddress(IP, 8080);
                    mSocket.connect(socketAddress, 8000);
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8"));
                    bufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "UTF-8"));

                    mServerIsConnection = true;
                    mHeartBeatThread = new HeartBeatThread();
                    mHeartBeatThread.start();

                    bufferedWriter.write("客户端发送的消息。。。" + "\n");
                    bufferedWriter.flush();

                    String receiveMsg;
                    while ((receiveMsg = bufferedReader.readLine()) != null) {
                        if (receiveMsg.equals("111")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toast("弹窗");
                                }
                            });
                        }
                        System.err.println("客户端接收的消息-->" + receiveMsg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mServerIsConnection = false;
                System.err.println("重新连接失败了。。。");
                resetSocket();
            } finally {
                try {
                    bufferedWriter.close();
                    bufferedReader.close();
                    mSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 心跳包线程
     */
    private class HeartBeatThread extends Thread {
        @Override
        public void run() {
            while (mServerIsConnection) {
                try {
                    DataOutputStream outputStream = new DataOutputStream(mSocket.getOutputStream());
                    outputStream.write("0xxfffww\n".getBytes());
                    outputStream.flush();
                    mServerIsConnection = true;
                    System.err.println("客户端发送心跳包");
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("服务器连接中断。。。");
                    mServerIsConnection = false;
                    resetSocket();
                }
            }
        }
    }

    /**
     * 断线重连
     */
    private void resetSocket() {
        if (!mSocket.isConnected() && !mServerIsConnection) {
            new SocketConnectionThread().start();
        }
    }

//    /**
//     * 判断是否断开连接，断开返回true,没有返回false
//     *
//     * @param socket
//     * @return
//     */
//    public static Boolean isServerClose(Socket socket) {
//        try {
//            //发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
//            socket.sendUrgentData(0);
//            return false;
//        } catch (Exception se) {
//            return true;
//        }
//    }

    static class MyHandler extends Handler {

        WeakReference<SocketTestActivity> mActivity;

        public MyHandler(SocketTestActivity demoActivity) {
            mActivity = new WeakReference<SocketTestActivity>(demoActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SocketTestActivity thisActivity = mActivity.get();
            if (msg.what == 0) {
                Log.e("消息", "------------> = " + msg.obj);
                thisActivity.receiverMsg.setText((String) msg.obj);
            }
        }

    }


    @Override
    protected void onDestroy() {
        mServerIsConnection = false;
        if (mSocketConnectionThread != null && !mSocketConnectionThread.isInterrupted()) {
            mSocketConnectionThread.interrupt();
            mSocketConnectionThread = null;
        }
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
