package com.cdfive.common.util;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author cdfive
 */
public class MoneyUtil {

    public static BigDecimal fenToYuan(Long fen) {
        if (Objects.isNull(fen)) {
            return null;
        }

        return new BigDecimal(fen).movePointLeft(2);
    }

    public static Long yuanToFen(BigDecimal yuan) {
        if (Objects.isNull(yuan)) {
            return null;
        }

        return yuan.movePointRight(2).longValue();
    }
}
