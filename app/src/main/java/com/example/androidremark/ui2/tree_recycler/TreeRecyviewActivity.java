package com.example.androidremark.ui2.tree_recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.androidremark.R;
import com.example.androidremark.widget.tree.TreeNode;
import com.example.androidremark.widget.tree.TreeView;
import com.example.androidremark.widget.tree.TreeViewAdapter;

import java.util.List;


public class TreeRecyviewActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    private ViewGroup viewGroup;
    private TreeNode root;
    private TreeView treeView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_tree_recyview);
        initView();

        root = TreeNode.root();
        buildTree();
        treeView = new TreeView(root, this, new MyNodeViewFactory());
        //treeView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView = (RecyclerView) treeView.getView();
        mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(mRecyclerView);
        treeView.getAdapter().setOnItemClickListener(new TreeViewAdapter.OnItemClickListener() {
            @Override
            public void click(TreeNode treeNode) {
                System.err.println("哈哈 ");
                treeNode.setExpanded(!treeNode.isExpanded());

                if (treeNode.isExpanded()) {
                    //打开
                    treeView.expandNode(treeNode);
                    // expandNode(treeNode);
                } else {
                    //关闭
                    treeView.collapseNode(treeNode);
                    //collapseNode(treeNode);
                }
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewGroup = (RelativeLayout) findViewById(R.id.container);
        setSupportActionBar(toolbar);
//        setLightStatusBar(viewGroup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_all:
                treeView.selectAll();
                break;
            case R.id.deselect_all:
                treeView.deselectAll();
                break;
            case R.id.expand_all:
                treeView.expandAll();
                break;
            case R.id.collapse_all:
                treeView.collapseAll();
                break;
            case R.id.expand_level:
                treeView.expandLevel(1);
                break;
            case R.id.collapse_level:
                treeView.collapseLevel(1);
                break;
            case R.id.show_select_node:
                Toast.makeText(getApplication(), getSelectedNodes(), Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getSelectedNodes() {
        StringBuilder stringBuilder = new StringBuilder("You have selected: ");
        List<TreeNode> selectedNodes = treeView.getSelectedNodes();
        for (int i = 0; i < selectedNodes.size(); i++) {
            if (i < 5) {
                stringBuilder.append(selectedNodes.get(i).getValue().toString() + ",");
            } else {
                stringBuilder.append("...and " + (selectedNodes.size() - 5) + " more.");
                break;
            }
        }
        return stringBuilder.toString();
    }

    private void buildTree() {
        for (int i = 0; i < 4; i++) {
            TreeNode treeNode = new TreeNode(new String("第1层  " + "第" + i));
            treeNode.setLevel(0);
            for (int j = 0; j < 5; j++) {
                TreeNode treeNode1 = new TreeNode(new String("第2层  " + "第" + j));
                treeNode1.setLevel(1);
                for (int k = 0; k < 5; k++) {
                    TreeNode treeNode2 = new TreeNode(new String("第3层" + "第" + k));
                    treeNode2.setLevel(2);
                    for (int l = 0; l < 3; l++) {
                        TreeNode treeNode3 = new TreeNode(new String("第4层" + "第" + l));
                        treeNode3.setLevel(3);
//                        for (int m = 0; m < 2; m++) {
//                            TreeNode treeNode4 = new TreeNode(new String("第5层" + "第" + m));
//                            treeNode4.setLevel(4);
//                            treeNode3.addChild(treeNode4);
//                        }
                        treeNode2.addChild(treeNode3);
                    }
                    treeNode1.addChild(treeNode2);
                }
                treeNode.addChild(treeNode1);
            }
            root.addChild(treeNode);
        }
    }

}
