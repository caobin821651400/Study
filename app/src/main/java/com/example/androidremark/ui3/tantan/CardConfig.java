package com.example.androidremark.ui3.tantan;

import android.content.Context;
import android.util.TypedValue;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/17 23:13
 * @Desc :
 * ====================================================
 */
public class CardConfig {
    //屏幕上最多同时显示几个Item
    public static int MAX_SHOW_COUNT;

    //每一级Scale相差0.05f，translationY相差7dp左右
    public static float SCALE_GAP;
    public static int TRANS_Y_GAP;

    public static void initConfig(Context context) {
        MAX_SHOW_COUNT = 4;
        SCALE_GAP = 0.05f;
        TRANS_Y_GAP = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15,
                context.getResources().getDisplayMetrics()
        );
    }
}
