package com.cdfive.es.util;

import java.util.UUID;

/**
 * @author cdfive
 */
public class CommonUtil {

    public static String getTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
