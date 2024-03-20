package com.cdfive.framework.springboot;

import lombok.Data;
import lombok.Getter;

/**
 * @author cdfive
 */
public class ApplicationStartupInfo {

    private static Long startingTime;

    private static Long startedTime;

    private static Long startTimeCostMs;

    public static void starting() {
        if (startingTime != null) {
            return;
        }
        startingTime = System.currentTimeMillis();
    }

    public static void started() {
        if (startedTime != null) {
            return;
        }

        startedTime = System.currentTimeMillis();
        startTimeCostMs = startedTime - startingTime;
    }

    public static Long getStartingTime() {
        return startingTime;
    }

    public static Long getStartedTime() {
        return startedTime;
    }

    public static Long getStartTimeCostMs() {
        return startTimeCostMs;
    }
}
