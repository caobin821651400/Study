package com.example.androidremark.banner;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.StatusBarUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ScaleInOutTransformer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图
 * https://github.com/youth5201314/banner
 */
public class Banner1Activity extends BaseActivity {
    private List<String> mList;
    private List<String> mList2;
    private Banner banner;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, banner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner1);

//        banner = new Banner(this);
//        ViewGroup.LayoutParams linearParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyUtils.getScreenHeight(this) / 3);
//        addContentView(banner, linearParams);
        banner = (Banner) findViewById(R.id.banner1);


        mList = new ArrayList<>();
        mList.add("http://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png");
        mList.add("http://img.zcool.cn/community/01fca557a7f5f90000012e7e9feea8.jpg");
        mList.add("http://img.zcool.cn/community/01996b57a7f6020000018c1bedef97.jpg");
        mList.add("http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg");

        mList2 = new ArrayList<>();
        mList2.add("1");
        mList2.add("2");
        mList2.add("3");
        mList2.add("4");

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.update(mList, mList2);
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                toast("我是第" + (position + 1) + "个");
            }
        });

        /*=============================================================================================*/
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_style:
                        toast("点击了");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
