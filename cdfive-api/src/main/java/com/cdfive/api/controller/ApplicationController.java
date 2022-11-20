package com.cdfive.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@Slf4j
@RequestMapping("/application")
@RestController
public class ApplicationController {

    @Autowired
    private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;

    @GetMapping("/online")
    public String online() {
        log.info("online start");
        eurekaAutoServiceRegistration.start();
        log.info("online end");
        return "online";
    }

    @GetMapping("/offline")
    public String offline() {
        log.info("offline start");
        eurekaAutoServiceRegistration.stop();
        log.info("offline end");
        return "offline";
    }
}
