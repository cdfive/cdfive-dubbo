package com.cdfive.learn.guava;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class JoinerTest {

    public static void main(String[] args) {
        Object[] arguments = new Object[2];
        arguments[0] = null;
        arguments[1] = "a";

//        String str = Joiner.on(",").join(arguments); // NPE
        String str = Joiner.on(",").skipNulls().join(arguments); // OK
        System.out.println(str);

        String str2 = String.join(",", Arrays.asList(arguments).stream().map(o -> (String) o).collect(Collectors.toList()));
        System.out.println(str2); // null, a
    }
}
