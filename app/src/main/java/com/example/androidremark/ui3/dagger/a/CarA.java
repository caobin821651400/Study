package com.example.androidremark.ui3.dagger.a;

import javax.inject.Inject;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 11:03
 * @Desc :汽车
 * ====================================================
 */
public class CarA {

    /**
     * @Inject：@Inject 标记需要依赖的变量，告诉Dagger2为它提供依赖
     */
    @Inject
    EngineA mEngineA;

    public CarA() {
        DaggerCarComponent.builder()
                .build().inject(this);
    }

    private EngineA getEngine() {
        return mEngineA;
    }

    public static void main(String[] args) {
        //第一种方式 构造方法和成员变量都要使用@Inject来注解
        CarA carA = new CarA();
        carA.getEngine().run();
    }

}
