package com.cdfive.xxljob.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class TestJobs {

    @XxlJob("testJob")
    public void testJob() throws Exception {
        log.info("testJob start");
        long start = System.currentTimeMillis();
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(5_000));
        log.info("testJob done,cost={}ms", (System.currentTimeMillis() - start));
    }
}
