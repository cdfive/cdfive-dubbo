package com.cdfive.framework.config;

import com.cdfive.framework.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Slf4j
@ComponentScan({"cn.cdfive.framework"})
@Configuration
public class FrameworkAutoConfig {

    @ConditionalOnMissingBean({SpringContextUtil.class})
    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }
}
