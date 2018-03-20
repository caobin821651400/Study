package com.example.androidremark.ui2.tree_recycler;

import android.view.View;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.widget.tree.TreeNode;
import com.example.androidremark.widget.tree.base.CheckableNodeViewBinder;


/**
 * Created by zxy on 17/4/23.
 */

public class FiveLevelNodeViewBinder extends CheckableNodeViewBinder {
    TextView textView;
    public FiveLevelNodeViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_five_level;
    }

    @Override
    public void bindView(TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
    }
}
