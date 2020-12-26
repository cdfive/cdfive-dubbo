package com.cdfive.common.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author cdfive
 */
public class EmojiUtilTest {
    
    @Test
    public void testHideName() {
        String s = "李💥";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("李***💥", EmojiUtil.hideName(s));

        s = "💥李";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("💥***李", EmojiUtil.hideName(s));

        s = "💥逍遥";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("💥***遥", EmojiUtil.hideName(s));

        s = "123💥4张";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("1***张", EmojiUtil.hideName(s));

        s = "123💥王";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("1***王", EmojiUtil.hideName(s));

        s = "1";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("1***", EmojiUtil.hideName(s));

        s = "李";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("李***", EmojiUtil.hideName(s));

        s = "💥";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("💥***", EmojiUtil.hideName(s));

        s = "12";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("1***2", EmojiUtil.hideName(s));

        s = "123";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("1***3", EmojiUtil.hideName(s));

        s = "1234";
        System.out.println(EmojiUtil.hideName(s));
        assertEquals("1***4", EmojiUtil.hideName(s));
    }
}
