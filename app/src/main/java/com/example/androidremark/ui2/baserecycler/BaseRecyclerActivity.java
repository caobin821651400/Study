package com.example.androidremark.ui2.baserecycler;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseRecyclerActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private TestmAdapter mAdapter;
    private Handler mHandler = new Handler();
    private SwipeRefreshLayout mSwipeLayout;
    List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recycler);
        datas = new ArrayList<>();
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "recyclermAdapter", true);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.main_color);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.addItemDecoration(new DividerDecoration(Color.parseColor("#C4C4C4"), 2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new TestmAdapter(mRecyclerView);

        //点击事件
        mAdapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                toast("点击第" + position);
            }
        });
        mAdapter.isLoadMore(true);
        mAdapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {//加载失败，重新加载回调方法
                // load();
                toast("11");
            }

            @Override
            public void onLoadMore() {//加载更多回调方法
                load();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    public void load() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                //模拟几种错误情况和正确情况
                int random = new Random().nextInt(10);
                if (random < 3) {
                    mAdapter.showLoadError();//显示加载错误
                } else if (random >= 3 && random < 6) {
                    mAdapter.showLoadComplete();//没有更多数据了
                } else {
                    mAdapter.add(datas.size(), "新增加");//加载更多
                }
            }
        }, 2000);
    }

    public List<String> getDatas(String str) {
        for (int i = 1; i <= 20; i++) {
            datas.add("第 " + i + " 个");
        }
        return datas;
    }


    @Override
    public void onRefresh() {
//        mAdapter.isLoadMore(false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setDataList(getDatas("刷新"));
                mSwipeLayout.setRefreshing(false);
                mAdapter.showLoadComplete();
                mAdapter.isLoadMore(true);
            }
        }, 2000);
    }


    class TestmAdapter extends XRecyclerViewAdapter<String> {

        public TestmAdapter(@NonNull RecyclerView mRecyclerView) {
            super(mRecyclerView, R.layout.dome_item);
        }

        @Override
        protected void bindData(XViewHolder holder, String data, int position) {
            TextView textView = (TextView) holder.getConvertView().findViewById(R.id.text);
            textView.setText(data);
        }
    }
}

