package com.cdfive.sentinel.web.servlet;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 */
public class CustomUrlCleaner implements UrlCleaner {

    private static final Logger log = LoggerFactory.getLogger(CustomUrlCleaner.class);

    private List<UrlParser> urlParsers = new ArrayList<>();

    @Override
    public String clean(String originUrl) {
        if (CollectionUtils.isEmpty(urlParsers)) {
            return originUrl;
        }

        for (UrlParser urlParser : urlParsers) {
            if (urlParser.getUrls() != null && urlParser.getUrls().contains(originUrl)) {
                try {
                    return urlParser.parseUrl(originUrl);
                } catch (Exception e) {
                    log.error("urlParser[{}] parse url[{}] error", urlParser.getClass().getSimpleName(), originUrl, e);
                    return originUrl;
                }
            }
        }

        return originUrl;
    }

    public List<UrlParser> getUrlParsers() {
        return urlParsers;
    }

    public void setUrlParsers(List<UrlParser> urlParsers) {
        this.urlParsers = urlParsers;
    }
}