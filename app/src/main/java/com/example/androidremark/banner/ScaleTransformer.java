package com.example.androidremark.banner;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by cb on 2017/10/11.
 */

public class ScaleTransformer implements ViewPager.PageTransformer {

    private float MAX = -1.0f;
    private float MIN = -1.0f;

    public ScaleTransformer(float maxScale, float minScale) {
        if (maxScale > 0 && minScale > 0) {
            MAX = maxScale;
            MIN = minScale;
        }
    }

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1 + position : 1 - position;

        float slope = (MAX - MIN) / 1;
        //
        float scaleValue=MIN+tempScale*slope;

        if (position < 0) {
            //ViewH
        }
    }
}
