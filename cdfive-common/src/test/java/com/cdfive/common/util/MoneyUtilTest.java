package com.cdfive.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author cdfive
 */
public class MoneyUtilTest {

    @Test
    public void testFenToYuan() {
        assertEquals(null, MoneyUtil.fenToYuan(null));
        assertEquals(new BigDecimal("0.00"), MoneyUtil.fenToYuan(0L));
        assertEquals(new BigDecimal("0.01"), MoneyUtil.fenToYuan(1L));
        assertEquals(new BigDecimal("1.00"), MoneyUtil.fenToYuan(100L));
        assertEquals(new BigDecimal("1.23"), MoneyUtil.fenToYuan(123L));
    }

    @Test
    public void testYuanToFen() {
        assertEquals(null, MoneyUtil.yuanToFen(null));
        assertEquals(Long.valueOf(0), MoneyUtil.yuanToFen(new BigDecimal("0")));
        assertEquals(Long.valueOf(100), MoneyUtil.yuanToFen(new BigDecimal("1")));
        assertEquals(Long.valueOf(120), MoneyUtil.yuanToFen(new BigDecimal("1.2")));
        assertEquals(Long.valueOf(123), MoneyUtil.yuanToFen(new BigDecimal("1.23")));
        assertEquals(Long.valueOf(1300), MoneyUtil.yuanToFen(new BigDecimal("13")));
        assertEquals(Long.valueOf(1320), MoneyUtil.yuanToFen(new BigDecimal("13.2")));
        assertEquals(Long.valueOf(1314), MoneyUtil.yuanToFen(new BigDecimal("13.14")));
        assertEquals(Long.valueOf(52000), MoneyUtil.yuanToFen(new BigDecimal("520")));
    }
}
