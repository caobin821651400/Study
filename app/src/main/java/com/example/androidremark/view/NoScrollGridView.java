package com.example.androidremark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

/**
 * Created by cb on 2017/10/26.
 */

public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    public NoScrollGridView(Context var1) {
        super(var1);
    }

    public NoScrollGridView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void onMeasure(int var1, int var2) {
        int var3 = MeasureSpec.makeMeasureSpec(536870911, -2147483648);
        super.onMeasure(var1, var3);
    }
}
