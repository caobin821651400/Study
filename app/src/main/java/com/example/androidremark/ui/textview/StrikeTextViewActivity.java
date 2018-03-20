package com.example.androidremark.ui.textview;

import android.os.Bundle;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.ui.textview.verticalroll.AdItemViewAdapter;
import com.example.androidremark.ui.textview.verticalroll.AdverItemView;
import com.example.androidremark.ui.textview.verticalroll.AdverNotice;

import java.util.ArrayList;
import java.util.List;

/**
 * 斜线textview
 */
public class StrikeTextViewActivity extends BaseActivity {
    private AdverItemView mAdverItemView;
    private List<AdverNotice> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strike_text_view);

        initData();
        AdItemViewAdapter adapter = new AdItemViewAdapter(mDatas);
        mAdverItemView = (AdverItemView) findViewById(R.id.jd_adverview);
        mAdverItemView.setAdapter(adapter);
        //开启线程滚东
        mAdverItemView.start();
    }

    private void initData() {
        mDatas = new ArrayList<AdverNotice>();

        mDatas.add(new AdverNotice("瑞士维氏军刀 新品满200-50", "最新"));
        mDatas.add(new AdverNotice("家居家装焕新季，讲199减100！", "最火爆"));
        mDatas.add(new AdverNotice("带上相机去春游，尼康低至477", "HOT"));
        mDatas.add(new AdverNotice("价格惊呆！电信千兆光纤上市", "new"));
    }

    @Override
    protected void onDestroy() {
        mAdverItemView.stop();
        super.onDestroy();
    }
}
