package com.example.androidremark.ui3.dagger;


import dagger.Module;
import dagger.Provides;

/**
 * Created by bin on 2019/1/21.
 */
@Module//第一步生成Module
public class MainModule {
    //第二步 使用provider 注解实例化对象
    //一个完整的Module必须拥有@Module和@Provides
//    @Provides
//    AObject providerA() {
//        return new AObject();
//    }


    // @Singleton 表示单例 component必须也要指定@Singleton
    @Provides
    BObject providerB() {
        return new BObject();
    }
}
