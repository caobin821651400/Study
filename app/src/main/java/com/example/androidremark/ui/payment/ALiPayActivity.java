package com.example.androidremark.ui.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

public class ALiPayActivity extends BaseActivity implements View.OnClickListener {
    private AlipayView alipay_view;
    private Button btn_start_pay, btn_end_pay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "支付宝支付", true);
        alipay_view = (AlipayView) findViewById(R.id.alipay_view);
        alipay_view.setState(AlipayView.State.IDLE);
        btn_start_pay = (Button) findViewById(R.id.btn_start_pay);
        btn_end_pay = (Button) findViewById(R.id.btn_end_pay);
        //
        btn_start_pay.setOnClickListener(this);
        btn_end_pay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_pay:
                alipay_view.setOverPay(false);
                alipay_view.setState(AlipayView.State.PROGRESS);
                break;
            case R.id.btn_end_pay:
                alipay_view.setOverPay(true);
                break;
        }
    }
}
