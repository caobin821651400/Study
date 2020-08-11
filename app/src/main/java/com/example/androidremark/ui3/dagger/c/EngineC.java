package com.example.androidremark.ui3.dagger.c;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/3/24 10:58
 * @Desc :引擎
 * ====================================================
 */
public class EngineC {

    /**
     * 用于自定义注解，通过@Scope自定义注解来限定注解的作用域，实现局部的单例
     * 1.用@Scope定义一个CarScope注解
     */
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CarScope {

    }

    public EngineC(String name) {
        this.name = name;
        System.out.println("EngineC create: " + name);
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
        System.out.println("引擎C转起来了~~~" + name);
    }

}
