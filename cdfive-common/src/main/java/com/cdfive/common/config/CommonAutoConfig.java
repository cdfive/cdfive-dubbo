package com.cdfive.common.config;

import com.cdfive.common.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Slf4j
@Configuration
public class CommonAutoConfig {

    @ConditionalOnMissingBean({SpringContextUtil.class})
    @Bean
    public SpringContextUtil getSpringContextUtil() {
        return new SpringContextUtil();
    }
}
