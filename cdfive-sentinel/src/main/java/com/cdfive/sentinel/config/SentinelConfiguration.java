package com.cdfive.sentinel.config;

import com.alibaba.csp.sentinel.Constants;
import com.alibaba.csp.sentinel.adapter.dubbo.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.cdfive.sentinel.datasource.DataSourceBuilder;
import com.cdfive.sentinel.datasource.DataSourceType;
import com.cdfive.sentinel.web.servlet.CustomUrlCleaner;
import com.cdfive.sentinel.web.servlet.UrlParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static com.cdfive.sentinel.constant.SentinelConstant.LOG_PRIFEX;

/**
 * @author cdfive
 */
@Slf4j
@Getter
@Setter
public class SentinelConfiguration {

    @Autowired
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

    @Autowired(required = false)
    private List<DataSourceBuilder> dataSourceBuilders;

    private String appName;

    private String ip;

    private Integer port;

    @PostConstruct
    public void init() throws Exception {
        log.info("{}SentinelConfiguration init start", LOG_PRIFEX);

        boolean enable = sentinelProperties.isEnable();
        log.info("{}enable={}", LOG_PRIFEX, enable);
        if (!enable) {
            log.info("{}sentinel has been disabled", LOG_PRIFEX);
            Constants.ON = false;
            return;
        }

        initProperties();

        initDataSource();

        initDubbo();

        initServlet();

        initSentinel();
        log.info("{}SentinelConfiguration init success", LOG_PRIFEX);
    }

    public void initProperties() {
        this.appName = sentinelProperties.getAppName();
        if (StringUtil.isNotBlank(appName)) {
            log.info("{}SentinelConfiguration appName={}", LOG_PRIFEX, appName);
            System.setProperty(SentinelConfig.APP_NAME_PROP_KEY, appName);
        }

        this.ip = TransportConfig.getHeartbeatClientIp();
        log.info("{}SentinelConfiguration ip={}", LOG_PRIFEX, ip);

        SentinelProperties.Transport transport = sentinelProperties.getTransport();
        if (transport != null) {
            this.port = transport.getPort();
            if (port != null) {
                log.info("{}SentinelConfiguration port={}", LOG_PRIFEX, port);
                TransportConfig.setRuntimePort(port);
            }

            String dashboard = transport.getDashboard();
            if (StringUtil.isNotBlank(dashboard)) {
                log.info("{}SentinelConfiguration dashboard={}", LOG_PRIFEX, dashboard);
                SentinelConfig.setConfig(TransportConfig.CONSOLE_SERVER, dashboard);
            }
        }
    }

    public void initDataSource() {
        SentinelProperties.DataSource dataSource = sentinelProperties.getDataSource();
        if (dataSource == null) {
            log.warn("{}SentinelConfiguration empty dataSource", LOG_PRIFEX);
            return;
        }

        String dataSourceType = dataSource.getType();
        if (!StringUtils.hasText(dataSourceType)) {
            log.error("{}SentinelConfiguration empty DataSourceType", LOG_PRIFEX);
            return;
        }

        DataSourceType type = DataSourceType.of(dataSourceType);
        if (type == null) {
            log.error("{}SentinelConfiguration invalid DataSourceType: {}", LOG_PRIFEX, dataSourceType);
            return;
        }

        if (CollectionUtils.isEmpty(dataSourceBuilders)) {
            log.error("{}SentinelConfiguration empty DataSourceBuilder", LOG_PRIFEX);
            return;
        }

        Optional<DataSourceBuilder> optDataSourceBuilder = dataSourceBuilders.stream().filter(o -> type.equals(o.dataSourceType())).findFirst();
        if (!optDataSourceBuilder.isPresent()) {
            log.error("{}SentinelConfiguration no DataSourceBuilder found for dataSourceType: {}", LOG_PRIFEX, type);
            return;
        }

        DataSourceBuilder dataSourceBuilder = optDataSourceBuilder.get();
        dataSourceBuilder.buildDataSource(sentinelProperties, appName, ip, port);
    }

    public void initDubbo() {
        if (dubboProviderFallback != null) {
            log.info("{}SentinelConfiguration register dubbo provider fallback", LOG_PRIFEX);
            DubboFallbackRegistry.setProviderFallback(dubboProviderFallback);
        }

        if (dubboConsumerFallback != null) {
            log.info("{}SentinelConfiguration register dubbo consumer fallback", LOG_PRIFEX);
            DubboFallbackRegistry.setConsumerFallback(dubboConsumerFallback);
        }
    }

    public void initServlet() {
        if (urlBlockHandler != null) {
            log.info("{}SentinelConfiguration register urlBlockHandler", LOG_PRIFEX);
            WebCallbackManager.setUrlBlockHandler(urlBlockHandler);
        }

        if (urlCleaner != null) {
            log.info("{}SentinelConfiguration register urlCleaner", LOG_PRIFEX);
            WebCallbackManager.setUrlCleaner(urlCleaner);
        } else if (!CollectionUtils.isEmpty(urlParsers)) {
            log.info("{}SentinelConfiguration register CustormUrlCleaner,urlParsers size={}", LOG_PRIFEX, urlParsers.size());
            for (UrlParser urlParser : urlParsers) {
                log.info("{}SentinelConfiguration urlParser={}", LOG_PRIFEX, urlParser.getClass().getSimpleName());
            }
            CustomUrlCleaner urlCleaner = new CustomUrlCleaner();
            urlCleaner.setUrlParsers(urlParsers);
            WebCallbackManager.setUrlCleaner(urlCleaner);
        } else {
            log.info("{}SentinelConfiguration use DefaultUrlCleaner", LOG_PRIFEX);
        }
    }

    public void initSentinel() {
        log.info("{}SentinelConfiguration InitExecutor.doInit() start", LOG_PRIFEX);
        InitExecutor.doInit();
        log.info("{}SentinelConfiguration InitExecutor.doInit() end", LOG_PRIFEX);
    }
}
