package com.cdfive.sentinel.web.servlet;

import java.util.List;

/**
 * @author cdfive
 */
public interface UrlParser {

    List<String> getUrls();

    String parseUrl(String originUrl);
}
