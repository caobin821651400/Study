package com.example.androidremark.ui3.dagger;

import dagger.Component;

/**
 * 康跑能
 * Created by bin on 2019/1/21.
 * 第一步 添加@Component
 * 第二部 添加module
 */
//@Singleton 表示单例，一个对象如果存在就不会再去创建了
@Component(modules = {MainModule.class})
public interface MainComponent {
    //第三步 绑定activity
    void inject(DaggerTestActivity activity);
}
