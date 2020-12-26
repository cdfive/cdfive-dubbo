package com.cdfive.common.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author cdfive
 */
public class GenerateUtilTest {

    @Test
    public void testGenRandomChars() {
        String s;
        s = GenerateUtil.genRandomChars(0);
        System.out.println(s);
        assertEquals(new String(new char[0]), s);

        s = GenerateUtil.genRandomChars(1);
        System.out.println(s);
        assertEquals(1, s.length());

        s = GenerateUtil.genRandomChars(6);
        System.out.println(s);
        assertEquals(6, s.length());
    }

    @Test
    public void testGenRandomStrId() {
        System.out.println(GenerateUtil.genRandomStrId());
        System.out.println(GenerateUtil.genRandomStrId());
        System.out.println(GenerateUtil.genRandomStrId());
        System.out.println(GenerateUtil.genRandomStrId());
        System.out.println(GenerateUtil.genRandomStrId());
        System.out.println(GenerateUtil.genRandomStrId());
    }
}