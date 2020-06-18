package com.example.androidremark.ui3.dashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.androidremark.R;

import java.util.Random;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private DashboardView1 mDashboardView1;
    private DashboardView2 mDashboardView2;
    private DashboardView3 mDashboardView3;
    private DashboardView4 mDashboardView4;

    private boolean isAnimFinished = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDashboardView1 = (DashboardView1) findViewById(R.id.dashboard_view_1);
        mDashboardView2 = (DashboardView2) findViewById(R.id.dashboard_view_2);
        mDashboardView3 = (DashboardView3) findViewById(R.id.dashboard_view_3);
        mDashboardView4 = (DashboardView4) findViewById(R.id.dashboard_view_4);

        mDashboardView1.setOnClickListener(this);
        mDashboardView2.setOnClickListener(this);
        mDashboardView3.setOnClickListener(this);
        mDashboardView4.setOnClickListener(this);

        mDashboardView2.setCreditValueWithAnim(new Random().nextInt(600) + 350);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dashboard_view_1:
                mDashboardView1.setRealTimeValue(new Random().nextInt(100));

                break;
            case R.id.dashboard_view_2:
                mDashboardView2.setCreditValueWithAnim(new Random().nextInt(950 - 350) + 350);

                break;
            case R.id.dashboard_view_3:
                mDashboardView3.setCreditValue(new Random().nextInt(950 - 350) + 350);

                break;
            case R.id.dashboard_view_4:
                if (isAnimFinished) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(mDashboardView4, "mRealTimeValue",
                            mDashboardView4.getVelocity(), new Random().nextInt(180));
                    animator.setDuration(1500).setInterpolator(new LinearInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimFinished = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimFinished = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isAnimFinished = true;
                        }
                    });
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDashboardView4.setVelocity(value);
                        }
                    });
                    animator.start();
                }

                break;
        }
    }
}
