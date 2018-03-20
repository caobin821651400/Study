package com.example.androidremark.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.androidremark.R;
import com.youth.banner.loader.ImageLoader;

/**
 * 重写图片加载器
 * Created by caobin on 2017/1/6.
 */
public class GlideImageLoader extends ImageLoader {
    /**
     * @param context
     * @param path      路径  可以是本地也可以是网址
     * @param imageView
     */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        //Glide 加载图片简单用法
        Glide.with(context.getApplicationContext()).load(path)
                //.error(R.drawable.avatar1)
                .placeholder(R.drawable.avatar1)
                .dontAnimate()
                .crossFade(1000)
                .into(imageView);
    }
}
