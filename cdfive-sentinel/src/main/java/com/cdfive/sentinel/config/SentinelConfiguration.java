package com.cdfive.sentinel.config;

import com.alibaba.csp.sentinel.Constants;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cdfive.sentinel.web.servlet.CustomUrlCleaner;
import com.cdfive.sentinel.web.servlet.UrlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.cdfive.sentinel.constant.SentinelConstant.DATA_SOURCE_TYPE_REDIS;
import static com.cdfive.sentinel.constant.SentinelConstant.LOG_PRIFEX;

/**
 * @author cdfive
 */
public class SentinelConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SentinelConfiguration.class);

    @Autowired(required = false)
    private SentinelProperties sentinelProperties;

    @Qualifier("dubboProviderFallback")
    @Autowired(required = false)
    private DubboFallback dubboProviderFallback;

    @Qualifier("dubboConsumerFallback")
    @Autowired(required = false)
    private DubboFallback dubboConsumerFallback;

    @Autowired(required = false)
    private UrlBlockHandler urlBlockHandler;

    @Autowired(required = false)
    private UrlCleaner urlCleaner;

    @Autowired(required = false)
    private List<UrlParser> urlParsers;

    @PostConstruct
    public void init() throws Exception {
        log.error("{}SentinelConfiguration init", LOG_PRIFEX);

        if (sentinelProperties != null) {
            boolean enable = sentinelProperties.isEnable();
            log.error("{}SentinelConfiguration set enable={}", LOG_PRIFEX, enable);
            if (!enable) {
                Constants.ON = false;
                return;
            }

            String appName = sentinelProperties.getAppName();
            if (StringUtil.isNotBlank(appName)) {
                log.error("{}SentinelConfiguration set appName={}", LOG_PRIFEX, appName);
                System.setProperty(SentinelConfig.APP_NAME_PROP_KEY, appName);
            }

            String dashboard = sentinelProperties.getTransportDashboard();
            if (StringUtil.isNotBlank(dashboard)) {
                log.error("{}SentinelConfiguration set dashboard={}", LOG_PRIFEX, dashboard);
                SentinelConfig.setConfig(TransportConfig.CONSOLE_SERVER, dashboard);
            }

            Integer transportPort = sentinelProperties.getTransportPort();
            if (transportPort != null) {
                log.error("{}SentinelConfiguration set port={}", LOG_PRIFEX, transportPort);
                // Set transport port
                TransportConfig.setRuntimePort(transportPort);
            }

            String datasourceType = sentinelProperties.getDatasourceType();
            if (DATA_SOURCE_TYPE_REDIS.equals(datasourceType)) {
                String redisHost = sentinelProperties.getRedisHost();
                String redisPort = sentinelProperties.getRedisPort();
                String redisPassword = sentinelProperties.getRedisPassword();
                log.error("{}SentinelConfiguration init redis datasource,host={},port={},password={}", LOG_PRIFEX, redisHost, redisPort, redisPassword);
                if (StringUtil.isNotBlank(redisHost) && StringUtil.isNotBlank(redisPort)) {
                    RedisConnectionConfig.Builder redisConfigBuilder = RedisConnectionConfig.builder();
                    redisConfigBuilder.withHost(redisHost.replace("@", "")).withPort(Integer.parseInt(redisPort.replace("@", "")));
                    if (StringUtil.isNotBlank(redisPassword)) {
                        redisConfigBuilder.withPassword(redisPassword);
                    }
                    RedisConnectionConfig redisConfig = redisConfigBuilder.build();

                    // Get client ip
                    String ip = TransportConfig.getHeartbeatClientIp();
                    log.error("{}SentinelConfiguration client ip={}", LOG_PRIFEX, ip);

                    // Init RedisDataSource for flow rules
                    String flowRuleKey = buildRuleKey(appName, ip, transportPort, "flow");
                    RedisDataSource<List<FlowRule>> flowRuleRedisDataSource = new RedisDataSource<>(redisConfig, flowRuleKey, flowRuleKey, new Converter<String, List<FlowRule>>() {
                        @Override
                        public List<FlowRule> convert(String source) {
                            return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                            });
                        }
                    });
                    FlowRuleManager.register2Property(flowRuleRedisDataSource.getProperty());

                    // Init RedisDataSource for degrade rules
                    String degradeRuleKey = buildRuleKey(appName, ip, transportPort, "degrade");
                    RedisDataSource<List<DegradeRule>> degradeRuleRedisDataSource = new RedisDataSource<>(redisConfig, degradeRuleKey, degradeRuleKey, new Converter<String, List<DegradeRule>>() {
                        @Override
                        public List<DegradeRule> convert(String source) {
                            return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                            });
                        }
                    });
                    DegradeRuleManager.register2Property(degradeRuleRedisDataSource.getProperty());

                    // Init RedisDataSource for system rules
                    String systemRuleKey = buildRuleKey(appName, ip, transportPort, "system");
                    RedisDataSource<List<SystemRule>> systemRuleRedisDataSource = new RedisDataSource<>(redisConfig, systemRuleKey, systemRuleKey, new Converter<String, List<SystemRule>>() {
                        @Override
                        public List<SystemRule> convert(String source) {
                            return JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                            });
                        }
                    });
                    SystemRuleManager.register2Property(systemRuleRedisDataSource.getProperty());

                    // Init RedisDataSource for authority rules
                    String authorityRuleKey = buildRuleKey(appName, ip, transportPort, "authority");
                    RedisDataSource<List<AuthorityRule>> authorityRuleRedisDataSource = new RedisDataSource<>(redisConfig, authorityRuleKey, authorityRuleKey, new Converter<String, List<AuthorityRule>>() {
                        @Override
                        public List<AuthorityRule> convert(String source) {
                            return JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                            });
                        }
                    });
                    AuthorityRuleManager.register2Property(authorityRuleRedisDataSource.getProperty());

                    // Init RedisDataSource for paramFlow rules
                    String paramFlowRuleKey = buildRuleKey(appName, ip, transportPort, "paramFlow");
                    RedisDataSource<List<ParamFlowRule>> paramFlowRuleRedisDataSource = new RedisDataSource<>(redisConfig, paramFlowRuleKey, paramFlowRuleKey, new Converter<String, List<ParamFlowRule>>() {
                        @Override
                        public List<ParamFlowRule> convert(String source) {
                            return JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                            });
                        }
                    });
                    ParamFlowRuleManager.register2Property(paramFlowRuleRedisDataSource.getProperty());
                }
            }
        }

        if (dubboProviderFallback != null) {
            log.error("{}SentinelConfiguration register dubbo provider fallback", LOG_PRIFEX);
            DubboFallbackRegistry.setProviderFallback(dubboProviderFallback);
        }

        if (dubboConsumerFallback != null) {
            log.error("{}SentinelConfiguration register dubbo consumer fallback", LOG_PRIFEX);
            DubboFallbackRegistry.setConsumerFallback(dubboConsumerFallback);
        }

        if (urlBlockHandler != null) {
            log.error("{}SentinelConfiguration register urlBlockHandler", LOG_PRIFEX);
            WebCallbackManager.setUrlBlockHandler(urlBlockHandler);
        }

        if (urlCleaner != null) {
            log.error("{}SentinelConfiguration register urlCleaner", LOG_PRIFEX);
            WebCallbackManager.setUrlCleaner(urlCleaner);
        } else if (!CollectionUtils.isEmpty(urlParsers)) {
            log.error("{}SentinelConfiguration register CustormUrlCleaner,urlParsers size={}", LOG_PRIFEX, urlParsers.size());
            for (UrlParser urlParser : urlParsers) {
                log.error("{}SentinelConfiguration urlParser={}", LOG_PRIFEX, urlParser.getClass().getSimpleName());
            }
            CustomUrlCleaner urlCleaner = new CustomUrlCleaner();
            urlCleaner.setUrlParsers(urlParsers);
            WebCallbackManager.setUrlCleaner(urlCleaner);
        } else {
            log.error("{}SentinelConfiguration use DefaultUrlCleaner", LOG_PRIFEX);
        }

        log.error("{}SentinelConfiguration InitExecutor.doInit() start", LOG_PRIFEX);
        InitExecutor.doInit();
        log.error("{}SentinelConfiguration InitExecutor.doInit() end", LOG_PRIFEX);
    }

    public SentinelProperties getSentinelProperties() {
        return sentinelProperties;
    }

    public void setSentinelProperties(SentinelProperties sentinelProperties) {
        this.sentinelProperties = sentinelProperties;
    }

    public DubboFallback getDubboProviderFallback() {
        return dubboProviderFallback;
    }

    public void setDubboProviderFallback(DubboFallback dubboProviderFallback) {
        this.dubboProviderFallback = dubboProviderFallback;
    }

    public DubboFallback getDubboConsumerFallback() {
        return dubboConsumerFallback;
    }

    public void setDubboConsumerFallback(DubboFallback dubboConsumerFallback) {
        this.dubboConsumerFallback = dubboConsumerFallback;
    }

    public UrlBlockHandler getUrlBlockHandler() {
        return urlBlockHandler;
    }

    public void setUrlBlockHandler(UrlBlockHandler urlBlockHandler) {
        this.urlBlockHandler = urlBlockHandler;
    }

    public UrlCleaner getUrlCleaner() {
        return urlCleaner;
    }

    public void setUrlCleaner(UrlCleaner urlCleaner) {
        this.urlCleaner = urlCleaner;
    }

    public List<UrlParser> getUrlParsers() {
        return urlParsers;
    }

    public void setUrlParsers(List<UrlParser> urlParsers) {
        this.urlParsers = urlParsers;
    }

    public static String buildRuleKey(String appName, String ip, Integer port, String ruleType) {
        return join("-", appName, ip, String.valueOf(port), ruleType, "rules");
    }

    public static String join(String separator, String... values) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String value : values) {
            if (!first) {
                sb.append(separator);
            } else {
                first = false;
            }
            sb.append(value);
        }
        return sb.toString();
    }
}
