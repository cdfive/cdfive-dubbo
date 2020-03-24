package com.cdfive.learn.designpattern.singleton;

/**
 * @author cdfive
 */
public class SingletonDemo3 {

    private SingletonDemo3() {

    }

    public static SingletonDemo3 getInstance() {
        return SingleDemo3Holder.INSTANCE;
    }

    private static class SingleDemo3Holder {
        private static SingletonDemo3 INSTANCE = new SingletonDemo3();
    }
}
