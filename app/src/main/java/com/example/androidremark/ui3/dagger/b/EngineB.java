package com.example.androidremark.ui3.dagger.b;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 10:58
 * @Desc :引擎
 * ====================================================
 */
public class EngineB {

    /**
     * Dagger2通过@Inject注解在需要这个类的实例的时候，来找到这个类的构造方法并实例化出来
     * 以此来为被@Inject标记得变量提供依赖
     */

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierA {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierB {
    }


    public EngineB() {
    }

    public EngineB(String name) {
        this.name = name;
    }

    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                '}';
    }

    public void run() {
        System.out.println("引擎B转起来了~~~" + name);
    }

}
