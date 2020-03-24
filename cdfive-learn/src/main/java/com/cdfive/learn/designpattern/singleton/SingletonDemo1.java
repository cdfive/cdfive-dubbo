package com.cdfive.learn.designpattern.singleton;

/**
 * @author cdfive
 */
public class SingletonDemo1 {

    private static volatile SingletonDemo1 INSTANCE;

    private SingletonDemo1() {

    }

    public static SingletonDemo1 getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonDemo1.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingletonDemo1();
                }
            }
        }

        return INSTANCE;
    }
}
