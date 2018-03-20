package com.example.androidremark.ui3.group;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.ui2.baserecycler.XRecyclerViewAdapter;
import com.example.androidremark.ui2.baserecycler.XViewHolder;

/**
 * 利用baseAdapter实现分组
 */
public class BaseGroupRecyclerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_group_recycler);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "分组", true);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //recyclerView.addItemDecoration(new DividerDecoration(Color.parseColor("#f2f2f2"), 15));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final MultiTypeAdapter adapter = new MultiTypeAdapter(recyclerView);
        recyclerView.setAdapter(adapter);

        // StickyHeader
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(new StickyHeaderAdapter(BaseGroupRecyclerActivity.this));
        decoration.setIncludeHeader(false);
        recyclerView.addItemDecoration(decoration);

        adapter.setDataList(GroupDataSource.getBaseGroupBeanList());
    }


    class MultiTypeAdapter extends XRecyclerViewAdapter<BaseGroupBean> {

        public MultiTypeAdapter(@NonNull RecyclerView mRecyclerView) {
            super(mRecyclerView);
        }

        @Override
        public int getItemLayoutResId(BaseGroupBean data, int position) {
            int layoutResId = -1;
            switch (data.getType()) {
                case BaseGroupBean.typeChild:
                    layoutResId = R.layout.dome_item;
                    break;
                case BaseGroupBean.typeGroup:
                    layoutResId = R.layout.dome_item_group;
                    break;
            }
            return layoutResId;
        }

        @Override
        public void bindData(XViewHolder holder, BaseGroupBean data, int position) {
            switch (data.getType()) {
                case BaseGroupBean.typeChild:
                    TextView textView = (TextView) holder.getConvertView().findViewById(R.id.text);
                    textView.setText(data.getTitle());
                    break;
                case BaseGroupBean.typeGroup:
                    TextView textView1 = (TextView) holder.getConvertView().findViewById(R.id.text11);
                    textView1.setText(data.getTitle());
                    break;
            }
        }
    }
}
