package com.cdfive.framework.springcloud.ribbon;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author cdfive
 */
public class SmartPing implements IPing {

    private static final Logger logger = LoggerFactory.getLogger(SmartPing.class);

    private static final String ACTUATOR_INFO_URL = "/actuator/info";

    private static final String SCHEME_HTTP = "http://";

    @Override
    public boolean isAlive(Server server) {
        String instanceUrl = server.getId();
        String scheme = server.getScheme();
        String actuatorInfoUrl = (StringUtils.hasText(scheme) ? scheme : SCHEME_HTTP) + instanceUrl + ACTUATOR_INFO_URL;

        SimpleClientHttpRequestFactory clientHttpRequestFactory;
        clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(1 * 1000);
        clientHttpRequestFactory.setReadTimeout(1 * 1000);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        boolean success = false;
        String errorInfo = null;
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(actuatorInfoUrl, String.class);
            if (responseEntity != null) {
                if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                    success = true;
                } else {
                    errorInfo = responseEntity.getStatusCode().toString();
                }
            } else {
                errorInfo = "empty response";
            }
        } catch (Throwable e) {
            if (e != null) {
                errorInfo = e.getMessage();
            }
        }

        if (!success) {
            logger.error("SmartPing failed,server={},errorInfo={}", server.getId(), errorInfo);
        } else {
            logger.debug("SmartPing success,server={}", server.getId());
        }

        return success;
    }
}
