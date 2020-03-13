package com.cdfive.com.util;

import com.cdfive.common.util.StringUtil;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for {@link com.cdfive.common.util.StringUtil}.
 *
 * @author cdfive
 */
public class StringUtilTest {

    @Test
    public void isBlank() {
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank(" "));
        assertTrue(StringUtil.isBlank("   "));
        assertTrue(StringUtil.isBlank("     "));
        assertFalse(StringUtil.isBlank("a"));
        assertFalse(StringUtil.isBlank("abc"));
        assertFalse(StringUtil.isBlank("123"));
        assertFalse(StringUtil.isBlank(" a"));
        assertFalse(StringUtil.isBlank("    123"));
    }
}
