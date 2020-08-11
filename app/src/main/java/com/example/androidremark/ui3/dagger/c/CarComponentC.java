package com.example.androidremark.ui3.dagger.c;

import dagger.Component;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 11:05
 * @Desc :Component 可以理解为注射器 将需要注入的变量初始化后注入需要的使用的地方
 * 3. 同时还需要使用@Scope去标注注入器Compoent
 * ====================================================
 */
@EngineC.CarScope
@Component(modules = CarModuleC.class)
public interface CarComponentC {
    void inject(CarC car);
}
