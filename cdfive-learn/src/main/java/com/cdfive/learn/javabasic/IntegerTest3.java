package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author cdfive
 */
public class IntegerTest3 {

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(5, 7, 10, 11, 13, 14);
        for (Integer num : nums) {
            System.out.println(StringUtils.leftPad(Integer.toBinaryString(num), 4, "0"));
        }
    }
}
