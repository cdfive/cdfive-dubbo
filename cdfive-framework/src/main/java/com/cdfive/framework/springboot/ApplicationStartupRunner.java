package com.cdfive.framework.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * @author cdfive
 */
@Slf4j
public class ApplicationStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Application has been started!");
    }
}
