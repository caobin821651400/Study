package com.example.androidremark.ui3.dagger.b;

import dagger.Component;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 11:05
 * @Desc :Component 可以理解为注射器 将需要注入的变量初始化后注入需要的使用的地方
 * ====================================================
 */
@Component(modules = CarModuleB.class)
public interface CarComponentB {
    void inject(CarB car);
}
