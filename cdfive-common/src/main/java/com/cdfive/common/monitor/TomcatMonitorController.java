package com.cdfive.common.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@Slf4j
@RequestMapping("/monitor/tomcat")
@RestController
public class TomcatMonitorController {

    @Autowired
    private ServletWebServerApplicationContext applicationContext;

//    @RequestMapping("/threadPool")
//    public String threadPool() {
//        TomcatWebServer webServer = (TomcatWebServer) applicationContext.getWebServer();
//        String info = webServer.getTomcat().getConnector().getProtocolHandler().getExecutor().toString();
//        log.info("TomcatMonitorController threadPool=>" + info);
//        return info;
//    }
}
