package com.example.androidremark.ui3.dagger.a;

import dagger.Component;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 11:05
 * @Desc :Component 可以理解为注射器 将需要注入的变量初始化后注入需要的使用的地方
 * ====================================================
 */
@Component
public interface CarComponent {
    void inject(CarA car);

    //用于标注接口，是依赖需求方和依赖提供方之间的桥梁。被Component标注的接口在编译时会生成该接口的实现类
    // （如果@Component标注的接口为CarComponent，则编译期生成的实现类为DaggerCarComponent）,我们通过调用这个实现类的方法完成注入；
}
