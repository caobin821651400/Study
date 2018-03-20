package com.example.androidremark.ui3.column;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ColumnCharActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ColumnCharAdapter columnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_char);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        columnAdapter = new ColumnCharAdapter(this);
        mRecyclerView.setAdapter(columnAdapter);

        List<ColumnValueBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new ColumnValueBean("第" + i, i * 20));
        }
        columnAdapter.setList(list);
    }
}
