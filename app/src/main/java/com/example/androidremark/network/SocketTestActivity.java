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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;

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
    private SocketThread mSocketThread;
    private boolean receiveStop;


    private static final String IP = "192.168.123.1";
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
                new SocketThread().start();
            }
        });
    }


    private class SocketThread extends Thread {

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        @Override
        public void run() {
            try {
                mSocket = new Socket(IP, 8080);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8"));
                bufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "UTF-8"));

                bufferedWriter.write("客户端发送的消息。。。" + "\n");
                bufferedWriter.flush();

                String receiveMsg;
                while ((receiveMsg = bufferedReader.readLine()) != null) {
                    System.err.println("客户端接收的消息-->" + receiveMsg);
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedWriter.close();
                   // bufferedReader.close();
                    mSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startServerListener(final BufferedReader bufferedReader) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String receiveMsg;
                try {
                    while ((receiveMsg = bufferedReader.readLine()) != null) {
                        System.err.println("客户端接收的消息-->" + receiveMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
