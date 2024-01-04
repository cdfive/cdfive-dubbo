package com.cdfive.common.util;

import org.junit.Test;

/**
 * @author cdfive
 */
public class LonLatUtilTest {

    @Test
    public void testDistance() {
        double oriLat = 24.908252D;
        double oriLon = 118.611469D;
        // 1704.1768475214317
        System.out.println(LonLatUtil.distance(oriLat, oriLon, 24.921732D, 118.6033386D));
        // 1244.129004498457
        System.out.println(LonLatUtil.distance(oriLat, oriLon, 24.897879D, 118.606747D));

        oriLat = 30.566508D;
        oriLon = 104.06363D;
        // 2975.9845756466448
        System.out.println(LonLatUtil.distance(oriLat, oriLon, 30.592160485238328D, 104.07277040781341D));
        // 5424.734432039334
        System.out.println(LonLatUtil.distance(oriLat, oriLon, 30.614194259235795D, 104.05094906052447D));
    }
}
