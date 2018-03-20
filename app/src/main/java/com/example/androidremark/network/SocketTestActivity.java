package com.example.androidremark.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.androidremark.R.id.btn_receiver;
import static com.example.androidremark.R.id.btn_send;

/**
 * socket练习
 */
public class SocketTestActivity extends BaseActivity {

    private EditText receiverEd;
    private Button sendTv, recevierBtn;
    private TextView receiverMsg;

    Socket socket;
    PrintWriter printWriter;
    BufferedReader reader;

    private static final String IP = "10.206.14.83";
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
        initService();

        mHandler = new MyHandler(this);
        sendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(receiverEd.getText().toString().trim())) {
                    sendMessage();
                }
            }
        });

        recevierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (socket != null) {
//                    resetSocket();
//                }
            }
        });

    }

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

    /**
     * 连接服务器
     */
    private void initService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 连接服务器
                    socket = new Socket(IP, 8080);


                    heartBeat();
                    //输入流
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // 获取输出流
                    printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")), true);
                    String s = "";
//                    Message message;
                    while ((s = reader.readLine()) != null) {
                        Message message = mHandler.obtainMessage();
                        message.obj = s;
                        message.what = 0;
                        mHandler.sendMessage(message);
                    }
                } catch (UnknownHostException e) {
                    System.err.println("unknown host exception: " + e.toString());
                } catch (IOException e) {
                    System.err.println("io exception: " + e.toString());
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                        if (printWriter != null) {
                            printWriter.close();
                        }
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private long sleepTime = 6 * 1000;//线程休眠时间
    private long cycTime = 6 * 1000;//定时向服务器发送心跳包
    private long lastSendTime = 0;//每发送完成记录当前时间
    private boolean isConn = true;//服务器是否连接

    /**
     * 心跳包
     */
    private void heartBeat() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isConn) {
                    if ((System.currentTimeMillis() - lastSendTime) > cycTime) {
                        try {
                            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                            outputStream.write("0xxfffww\n".getBytes());
                            outputStream.flush();
                            System.err.println("客户端发送心跳包");
                        } catch (IOException e) {
                            e.printStackTrace();
                            isConn = false;
                            System.err.println("服务器连接中断。。。");
                        }
                        lastSendTime = System.currentTimeMillis();
                    } else {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 断线重连
     */
    private void resetSocket() {
        while (isServerClose(socket)) {
            initService();
        }
    }

    /**
     * 判断是否断开连接，断开返回true,没有返回false
     *
     * @param socket
     * @return
     */
    public static Boolean isServerClose(Socket socket) {
        try {
            //发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            socket.sendUrgentData(0);
            return false;
        } catch (Exception se) {
            return true;
        }
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        printWriter.print(receiverEd.getText().toString() + "\n");
        printWriter.flush();
    }

    @Override
    protected void onDestroy() {
        isConn = false;
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
