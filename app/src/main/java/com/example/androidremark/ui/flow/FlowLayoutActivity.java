package com.example.androidremark.ui.flow;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

/**
 * 流式布局
 */
public class FlowLayoutActivity extends BaseActivity {
    private Toolbar toolbar;
    private TagFlowLayout mFlowLayout;
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        initView();
    }

    private void initView() {
        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "流式布局", true);
        //
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);

        //
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                //添加布局
                TextView textView = (TextView) LayoutInflater.from(FlowLayoutActivity.this)
                        .inflate(R.layout.tv, mFlowLayout, false);
                textView.setText(s);
                return textView;
            }

            @Override
            public boolean setSelected(int position, String s) {
                return super.setSelected(position, s);
            }
        });
        //点击单个
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(FlowLayoutActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });
        //这里输出所有点击过的
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //setTitle("choose:" + selectPosSet.toString());
                System.err.println("哈哈 " + selectPosSet.toString());
            }
        });
    }
}
