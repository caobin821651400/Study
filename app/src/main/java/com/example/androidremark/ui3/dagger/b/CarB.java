package com.example.androidremark.ui3.dagger.b;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 11:03
 * @Desc :汽车
 * ====================================================
 */
public class CarB {

    /**
     * 如果需要注入的的构造是带参数或者Engine是第三方库的，我们无法修改
     * 这种时候就需要用到@Moudle+@Provider的方式来实现依赖
     * <p>
     * 步骤1：
     * 首先找到@Moudle标注的注解中是否存在依赖的方法
     * <p>
     * 步骤2：
     * 若存在提供依赖的方法，查看方法是否存在参数。
     * a:若存在参数，则从步骤1开始一次初始化每个参数；
     * b:若不存在，直接初始化该类实例，完成一次依赖注入。
     * <p>
     * 步骤3：
     * 若不存在依赖的方法，则查找@Inject标注的构造方法，查看构造方法中是否存在参数
     * a:若存在参数，则从步骤1开始一次初始化每个参数；
     * b:若不存在，直接初始化该类实例，完成一次依赖注入。
     */


    /**
     * 不管使用哪种方式注入，成员标量这里都要使用@Inject注解，告诉Dagger2这个标量需要提供依赖注入
     */

    @Named("provider1")
    @Inject
    EngineB mEngineB;

    @Named("provider2")
    @Inject
    EngineB mEngineB2;

    //*****************另一种实现******************//
    @EngineB.QualifierA
    @Inject
    EngineB mEngineB3;

    @EngineB.QualifierB
    @Inject
    EngineB mEngineB4;


    public CarB() {
        DaggerCarComponentB.builder()
                .carModuleB(new CarModuleB())
                .build().inject(this);
    }

    private EngineB getEngine() {
        return mEngineB;
    }

    private EngineB getEngine2() {
        return mEngineB2;
    }

    private EngineB getEngine3() {
        return mEngineB3;
    }

    private EngineB getEngine4() {
        return mEngineB4;
    }

    public static void main(String[] args) {
        CarB car = new CarB();
        car.getEngine().run();
        car.getEngine2().run();
//        System.err.println("-----------另一种实现---------");
        car.getEngine3().run();
        car.getEngine4().run();
    }
}
