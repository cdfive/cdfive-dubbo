package com.cdfive.learn.javabasic;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author cdfive
 */
public class NumberFormatTest {
    public final static NumberFormatTest instance = new NumberFormatTest();

    private final static ThreadLocal<NumberFormat> TL = new ThreadLocal<>();

    private NumberFormatTest() {
    }

    public String fen2yuan(Integer fen) {
        try {
            if (fen == null || fen == 0) {
                return "0";
            }
            BigDecimal divide100 = BigDecimal.valueOf(100);
            return getFormat().format(BigDecimal.valueOf(fen).divide(divide100));
        } finally {
            TL.remove();
        }
    }

    public Integer yuan2fen(BigDecimal yuan) {
        if (yuan == null) {
            return 0;
        }
        BigDecimal multiply100 = BigDecimal.valueOf(100);
        return yuan.multiply(multiply100).intValue();
    }

    private static NumberFormat getFormat() {
        NumberFormat df = TL.get();
        if (df == null) {
            df = NumberFormat.getInstance();
            //去掉千分位
            df.setGroupingUsed(false);
            TL.set(df);
        }
        return df;
    }

    public static void main(String[] args) {
        System.out.println(NumberFormatTest.instance.fen2yuan(667713140));
        System.out.println(new BigDecimal("667713140").divide(new BigDecimal("100")));
    }
}
