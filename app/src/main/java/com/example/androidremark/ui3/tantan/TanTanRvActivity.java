package com.example.androidremark.ui3.tantan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.ui2.baserecycler.XRecyclerViewAdapter;
import com.example.androidremark.ui2.baserecycler.XViewHolder;

import java.util.List;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/17 23:05
 * @Desc :仿探探首页卡片滑动
 * ====================================================
 */
public class TanTanRvActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private MAdapter mAdapter;
    private List<SwipeCardBean> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_tan_rv);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv);

        mDatas = SwipeCardBean.initDatas();

        //初始化一些配置
        CardConfig.initConfig(this);


        mRecyclerView.setLayoutManager(new SwipeCardLayoutManager());
        mAdapter = new MAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataList(mDatas);

        //TODO 滑动
        ItemTouchHelper.Callback callback = new SwipeCardCallback(mRecyclerView, mAdapter, mDatas);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
    }


    /**
     * ====================================================
     *
     * @User :caobin
     * @Date :2020/6/17 23:13
     * @Desc :
     * ====================================================
     */
    class MAdapter extends XRecyclerViewAdapter<SwipeCardBean> {

        public MAdapter(@NonNull RecyclerView mRecyclerView) {
            super(mRecyclerView, R.layout.item_swipe_card);
        }

        @Override
        protected void bindData(XViewHolder holder, SwipeCardBean swipeCardBean, int position) {

            View view = holder.itemView;
            ImageView iv = view.findViewById(R.id.iv);
            TextView tvName = view.findViewById(R.id.tvName);
            TextView tvPrecent = view.findViewById(R.id.tvPrecent);

            tvName.setText(swipeCardBean.getName());
            tvPrecent.setText(swipeCardBean.getPostition() + "/" + mDatas.size());
            Glide.with(TanTanRvActivity.this)
                    .load(swipeCardBean.getUrl())
                    .into(iv);

        }
    }
}
