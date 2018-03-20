package com.example.androidremark.ui2.tree_recycler;

import android.view.View;

import com.example.androidremark.widget.tree.base.BaseNodeViewBinder;
import com.example.androidremark.widget.tree.base.BaseNodeViewFactory;


/**
 * Created by zxy on 17/4/23.
 */

public class MyNodeViewFactory extends BaseNodeViewFactory {

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new FirstLevelNodeViewBinder(view);
            case 1:
                return new SecondLevelNodeViewBinder(view);
            case 2:
                return new ThridLevelNodeViewBinder(view);
            case 3:
                return new FourLevelNodeViewBinder(view);
            case 4:
                return new FiveLevelNodeViewBinder(view);
            default:
                return null;
        }
    }
}
