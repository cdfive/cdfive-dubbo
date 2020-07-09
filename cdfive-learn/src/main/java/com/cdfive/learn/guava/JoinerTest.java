package com.cdfive.learn.guava;

import com.google.common.base.Joiner;

/**
 * @author cdfive
 */
public class JoinerTest {

    public static void main(String[] args) {
        Object[] arguments = new Object[2];
        arguments[0] = null;
        arguments[1] = "a";
//        String str = Joiner.on(",").join(arguments);// NPE
        String str = Joiner.on(",").skipNulls().join(arguments);// OK
        System.out.println(str);
    }
}
