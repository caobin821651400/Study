package com.example.androidremark.ui.recycler.sticky;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.bean.GroupChildBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 粘性recycler
 */
public class StickyRecyclerActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private StickHeaderAdapter mAdapter;
    private List<ItemInfoBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_recycler);
        initData();
        initView();
    }


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "粘性recycler", true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        StickyHeaderDecoration stickyHeaderDecoration = new StickyHeaderDecoration(30, this);
        stickyHeaderDecoration.setDatas(mDatas);
        mRecyclerView.addItemDecoration(stickyHeaderDecoration);

        mAdapter = new StickHeaderAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 数据
     */
    private void initData() {
        mDatas = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String tag = "分组" + i;
                ItemInfoBean bean = new ItemInfoBean("组员" + j, "其他信息");
                bean.tag = tag;
                mDatas.add(bean);
            }
        }

//
//        String data = "{\"resultMsg\":{\"groupList\":[{\"childList\":[{\"childName\":\"组员1\",\"openTime\":\"2017-04-20 11:40:18\"}," +
//                "{\"childName\":\"组员2\",\"openTime\":\"2017-04-20 11:40:18\"},{\"childName\":\"组员3\",\"openTime\":\"2017-04-20 11:40:18\"}],\"groupName\":\"分组1\"}," +
//                "{\"childList\":[{\"childName\":\"组员1\",\"openTime\":\"2017-07-06 10:05:16\"}," +
//                "{\"childName\":\"组员2\",\"openTime\":\"2017-07-06 10:05:16\"}],\"groupName\":\"分组2\"}]}}";
//
//        GroupChildBean bean = new Gson().fromJson(data, GroupChildBean.class);
//
//        for (GroupChildBean.ResultMsgBean.GroupListBean group : bean.getResultMsg().getGroupList()) {
//            for (GroupChildBean.ResultMsgBean.GroupListBean.ChildListBean child : group.getChildList()) {
//                String tag = group.getGroupName();
//                ItemInfoBean bean2 = new ItemInfoBean(child.getChildName(), "其他信息");
//                bean2.tag = tag;
//                mDatas.add(bean2);
//            }
//        }
    }
}
