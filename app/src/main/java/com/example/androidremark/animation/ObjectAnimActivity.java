package com.example.androidremark.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ObjectAnimActivity extends BaseActivity implements View.OnClickListener {

    private int resId[] = {R.id.iv_a, R.id.iv_b, R.id.iv_c, R.id.iv_d, R.id.iv_e};

    private List<ImageView> imageViews = new ArrayList<>();

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_anim);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "ObjectAnim", true);

        for (int i = 0; i < resId.length; i++) {
            ImageView imageview = (ImageView) findViewById(resId[i]);
            imageview.setOnClickListener(this);
            imageViews.add(imageview);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_a:
                if (!isOpen) {
                    openMenu();
                } else {
                    closeMenu();
                }
                break;
            default:
                toast("我是 " + v.getId());
                break;
        }
    }

    /**
     * 打开菜单
     */
    private void openMenu() {
        for (int i = 1; i < resId.length; i++) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", 0F, i * 200F);
            objectAnimator.setDuration(500);
            objectAnimator.setStartDelay((4 * 150) / i);//延迟
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.start();
        }
        isOpen = true;


//        for (int i = 1; i < resId.length; i++) {
//            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", 0F, i * 200F);
//            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", 0F, i * 200F);
//
//            AnimatorSet set = new AnimatorSet();
//            set.play(objectAnimator).after(objectAnimator2);
////            set.playTogether(objectAnimator, objectAnimator2);
//
//            set.setDuration(500);
//            set.setStartDelay((4 * 150) / i);//延迟
//            set.setInterpolator(new DecelerateInterpolator());
//            set.start();
//        }
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {
        for (int i = 1; i < resId.length; i++) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", i * 200F, 0F);
            objectAnimator.setDuration(500);
            objectAnimator.setStartDelay(i * 150);
            objectAnimator.start();

        }
        isOpen = false;
    }

//    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", 0F, i * 200F);
//
//    AnimatorSet set = new AnimatorSet();
//            set.play(objectAnimator).after(objectAnimator2);
//            set.playTogether(objectAnimator,objectAnimator2);
//
//            set.setDuration(500);
//            set.setStartDelay((4 * 150) / i);//延迟
//            set.setInterpolator(new DecelerateInterpolator());
//            set.start();

    //
    //DecelerateInterpolator减速插值器
    //AccelerateDecelerateInterpolator先加速后减速
    //AccelerateInterpolator加速
    //AnticipateInterpolator先回弹
    //OvershootInterpolator后回弹
    // AnticipateOvershootInterpolator两者都有
    //BounceInterpolator弹力球

    //alpha 透明度
    //rotation z轴旋转
    //rotationX x轴旋转
    //rotationY y轴旋转
    //translationX x水平偏移
    //translationY y水平偏移
    //ScaleX x轴缩放
    //ScaleY y轴缩放
}
