package com.example.androidremark.ui.interfaceloading;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

public class TipActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    XLoadingLayout vLoading;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "界面提示", true);

        mListView = (ListView) findViewById(R.id.list_view);

        vLoading = (XLoadingLayout) findViewById(R.id.loading);
        vLoading.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vLoading.showLoading();
                toast("重新加载");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vLoading.showEmpty();
                    }
                }, 1200);
            }
        });
        vLoading.showContent();


        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = new TextView(parent.getContext());
                view.setGravity(Gravity.CENTER);
                view.setPadding(40, 40, 40, 40);
                view.setText("我是 " + position);
                return view;
            }
        });

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                vLoading.showEmpty();
                break;
            case R.id.btn2:
                vLoading.showError();
                break;
            case R.id.btn3:
                vLoading.showLoading();
                break;
            case R.id.btn4:
                vLoading.showNoNetwork();
                break;
            case R.id.btn5:
                vLoading.showContent();
                break;
        }
    }
}
