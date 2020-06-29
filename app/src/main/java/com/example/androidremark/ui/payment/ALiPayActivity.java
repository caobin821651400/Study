package com.example.androidremark.ui.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.utils.Utils;
import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.MyUtils;

public class ALiPayActivity extends BaseActivity implements View.OnClickListener {
    private AlipayView alipay_view;
    private Button btn_start_pay, btn_end_pay;

//    private ImageView mImageView;
//    private TaskClearDrawable mTaskClearDrawable;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
//        setContentView(R.layout.activity_payment2);
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

        //自定义Drawable
//        mImageView = findViewById(R.id.image);
//        mTaskClearDrawable = new TaskClearDrawable(this, MyUtils.dp2px(this,400), Utils.dp2px(this,400));
//        mImageView.setImageDrawable(mTaskClearDrawable);
//        mTaskClearDrawable.start();
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
