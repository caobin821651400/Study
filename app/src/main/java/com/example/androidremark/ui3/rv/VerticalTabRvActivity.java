package com.example.androidremark.ui3.rv;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.ui2.baserecycler.XRecyclerViewAdapter;
import com.example.androidremark.ui2.baserecycler.XViewHolder;
import com.example.androidremark.ui3.group.BaseGroupBean;
import com.example.androidremark.ui3.group.GroupDataSource;
import com.example.androidremark.utils.MyUtils;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerticalTabRvActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private MagicIndicator magicIndicator;
    private String[] groups = {"分组1", "分组2", "分组3", "分组4", "分组5", "分组6",};
    private List<String> mGroupList;
    private int currentTabIndex;
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private MultiTypeAdapter mAdapter;
    private List<BaseGroupBean> groupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_tab_rv);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        magicIndicator = findViewById(R.id.magic_indicator);
        mGroupList = Arrays.asList(groups);

        mAdapter = new MultiTypeAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        final GridLayoutManager manager = new GridLayoutManager(this, 3, OrientationHelper.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter.isFooterPosition(position)){
                    return 1;
                }
                return mAdapter.getItem(position).getType() == BaseGroupBean.typeChild ? 1 : manager.getSpanCount();
            }
        });
        mRecyclerView.setLayoutManager(manager);

        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, MyUtils.getScreenHeight(this) - MyUtils.dp2px(this, 50)));
        mAdapter.addFooterView(view);

        mAdapter.setDataList(GroupDataSource.getBaseGroupBeanList());

        for (BaseGroupBean bean : GroupDataSource.getBaseGroupBeanList()) {
            if (bean.getType() == BaseGroupBean.typeGroup) {
                groupList.add(bean);
            }
        }

        initIndicator();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager manager = (GridLayoutManager) mRecyclerView.getLayoutManager();
                int nowPosition = manager.findFirstVisibleItemPosition();
                if (mAdapter.getItem(nowPosition).getType() == BaseGroupBean.typeGroup) {
                    mFragmentContainerHelper.handlePageSelected(groupList.indexOf(GroupDataSource.getBaseGroupBeanList().get(nowPosition)));
                }
            }
        });
    }


    /**
     * 初始化ViewPager指数器
     */
    private void initIndicator() {
        mFragmentContainerHelper.handlePageSelected(0, false);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mGroupList == null ? 0 : mGroupList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(VerticalTabRvActivity.this,
                        R.color.colorAccent));
                simplePagerTitleView.setText(mGroupList.get(index));
                simplePagerTitleView.setTextSize(12);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentTabIndex = index;
                        mFragmentContainerHelper.handlePageSelected(currentTabIndex);
                        System.err.println("哈哈 " + GroupDataSource.getBaseGroupBeanList().indexOf(groupList.get(index)));
                        mRecyclerView.scrollToPosition(GroupDataSource.getBaseGroupBeanList().indexOf(groupList.get(index)));
                        GridLayoutManager mLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
                        mLayoutManager.scrollToPositionWithOffset(GroupDataSource.getBaseGroupBeanList().indexOf(groupList.get(index)), 0);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                linePagerIndicator.setColors(ContextCompat.getColor(VerticalTabRvActivity.this,
                        R.color.colorAccent));
                return linePagerIndicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
    }


    class MultiTypeAdapter extends XRecyclerViewAdapter<BaseGroupBean> {

        public MultiTypeAdapter(RecyclerView mRecyclerView) {
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
