package com.cdfive.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

/**
 * @author cdfive
 */
public class CommonUtil {

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
