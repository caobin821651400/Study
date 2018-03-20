package com.example.androidremark.ui2.grouprecycler;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.bean.GroupChildBean;
import com.example.androidremark.bean.GroupChildBean.ResultMsgBean.GroupListBean.ChildListBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GroupRecyclerActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private GroupRecyAdapter mAdapter;
    private LinkedHashMap<String, ArrayList<ChildListBean>> groupMap =
            new LinkedHashMap<String, ArrayList<ChildListBean>>();
    private ArrayList<ChildListBean> childList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_recycler);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "分组recycler", true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final GridLayoutManager manager = new GridLayoutManager(this, 2, OrientationHelper.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == GroupRecyAdapter.GROUP_ITEM_TYPE ? manager.getSpanCount() : 1;
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GroupRecyAdapter(this);

        initData();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setList(groupMap);
    }

    private void initData() {
/**
 * json数据
 */
        String data = "{\"resultMsg\":{\"groupList\":[{\"childList\":[{\"childName\":\"组员1\",\"openTime\":\"2017-04-20 11:40:18\"}," +
                "{\"childName\":\"组员2\",\"openTime\":\"2017-04-20 11:40:18\"},{\"childName\":\"组员3\",\"openTime\":\"2017-04-20 11:40:18\"}],\"groupName\":\"分组1\"}," +
                "{\"childList\":[{\"childName\":\"组员1\",\"openTime\":\"2017-07-06 10:05:16\"}," +
                "{\"childName\":\"组员2\",\"openTime\":\"2017-07-06 10:05:16\"}],\"groupName\":\"分组2\"}]}}";

        System.err.println("哈哈 "+data);
        GroupChildBean bean = new Gson().fromJson(data, GroupChildBean.class);

        for (GroupChildBean.ResultMsgBean.GroupListBean group : bean.getResultMsg().getGroupList()) {
            childList = new ArrayList<>();
            for (GroupChildBean.ResultMsgBean.GroupListBean.ChildListBean child : group.getChildList()) {
                ChildListBean childBean = new ChildListBean(child.getChildName());
                childList.add(childBean);
            }
            groupMap.put(group.getGroupName(), childList);
        }

//        private ArrayList<GroupChildBean.ResultMsgBean.GroupListBean> childList;
//        for (GroupChildBean.ResultMsgBean.GroupListBean group : bean.getResultMsg().getGroupList()) {
//            childList = new ArrayList<>();
//            childList.add(group);
//        }
//
//        for (int i = 0; i < 1; i++) {
//            childList = new ArrayList<>();
//            for (int j = 0; j < 1; j++) {
//                ChildListBean childBean = new ChildListBean("组员" + (j + 1));
//                childList.add(childBean);
//            }
//            groupMap.put("分组" + i, childList);
//        }
    }
}

