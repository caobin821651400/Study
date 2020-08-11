package com.example.androidremark.ui3.dagger.c;

import javax.inject.Inject;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 11:03
 * @Desc :汽车
 * ====================================================
 */
public class CarC {

    @Inject
    EngineC mEngineC;

    @Inject
    EngineC mEngineC2;

    public CarC() {
        DaggerCarComponentC.builder()
                .carModuleC(new CarModuleC())
                .build().inject(this);
    }

    private EngineC getEngine() {
        return mEngineC;
    }

    private EngineC getEngine2() {
        return mEngineC2;
    }

    public static void main(String[] args) {
        CarC car = new CarC();

        System.err.println(car.getEngine());
        System.err.println(car.getEngine2());

//        car.getEngine().run();
//        car.getEngine2().run();
    }

}
