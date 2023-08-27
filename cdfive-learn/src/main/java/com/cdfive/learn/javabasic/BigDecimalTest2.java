package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author cdfive
 */
public class BigDecimalTest2 {

    public static void main(String[] args) {
        Integer totalCount;
        Integer successCount;
        Integer errorCount;

        totalCount = 618;
        successCount = 60;
        errorCount = 18;
        // 12.48 ERROR
        calProgress1(totalCount, successCount, errorCount);
        // 12.62 OK
        calProgress2(totalCount, successCount, errorCount);

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        totalCount = 61800;
        successCount = 60;
        errorCount = 18;
        // 0.00 ERROR
        calProgress1(totalCount, successCount, errorCount);
        // 0.13 ERROR
        calProgress2(totalCount, successCount, errorCount);
    }

    private static void calProgress1(Integer totalCount, Integer successCount, Integer errorCount) {
        BigDecimal calProgress = new BigDecimal(String.valueOf(successCount + errorCount))
                .multiply(new BigDecimal("100")
                .divide(new BigDecimal(String.valueOf(totalCount)), 2, RoundingMode.HALF_UP));
        System.out.println(calProgress.toString());
    }

    private static void calProgress2(Integer totalCount, Integer successCount, Integer errorCount) {
        BigDecimal calProgress2 = new BigDecimal(String.valueOf(successCount + errorCount))
                .multiply(new BigDecimal("100"))
                .divide(new BigDecimal(String.valueOf(totalCount)), 2, RoundingMode.HALF_UP);
        System.out.println(calProgress2.toString());
    }
}
