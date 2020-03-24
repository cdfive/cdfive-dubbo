package com.cdfive.learn.designpattern.singleton;

/**
 * @author cdfive
 */
public class SingletonDemo2 {

    private static SingletonDemo2 INSTANCE = new SingletonDemo2();

    private SingletonDemo2() {

    }

    public static SingletonDemo2 getInstance() {
        return INSTANCE;
    }
}
