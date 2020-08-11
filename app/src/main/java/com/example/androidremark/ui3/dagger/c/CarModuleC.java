package com.example.androidremark.ui3.dagger.c;

import dagger.Module;
import dagger.Provides;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 11:40
 * @Desc :Module
 * ====================================================
 */
@Module
public class CarModuleC {


    public CarModuleC() {
    }

    /**
     * 2. @CarScope去标记依赖提供方MarkCarModule
     */
    @Provides
    @EngineC.CarScope
    EngineC providerEngineC() {
        return new EngineC("我是参数");
    }
}
