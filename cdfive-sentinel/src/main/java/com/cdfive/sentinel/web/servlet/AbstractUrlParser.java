package com.cdfive.sentinel.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * base class for UrlParser
 *
 * @author cdfive
 */
public abstract class AbstractUrlParser implements UrlParser {

    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * separator between url and parameter
     */
    protected static final String URL_SEPERATOR = "#";

    /**
     * separator between parameter name and value
     */
    protected static final String PARAM_VALUE_SEPERATOR = "=";

    /**
     * separator between different parameters
     */
    protected static final String OTHER_PARAM_SEPERATOR = "&";

    /**
     * append parameters after url, including parameter name and parameter value
     */
    protected String appendUrlParam(String originUrl, String paramName, String paramValue) {
        return originUrl + URL_SEPERATOR + paramName + PARAM_VALUE_SEPERATOR + paramValue;
    }

    /**
     * batch append parameters after url, including parameter name and parameter value
     */
    protected String appendUrlParams(String originUrl, List<String> paramNames, List<String> paramValues) {
        StringBuilder newUrl = new StringBuilder(originUrl).append(URL_SEPERATOR);
        for (int i = 0; i < paramNames.size(); i++) {
            if (i > 0) {
                newUrl.append(OTHER_PARAM_SEPERATOR);
            }
            newUrl.append(paramNames.get(i)).append(PARAM_VALUE_SEPERATOR).append(paramValues.get(i));
        }
        return newUrl.toString();
    }

    /**
     * get HttpServletRequest object
     */
    protected HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        } catch (Exception e) {
            log.error("get HttpServletRequest error", e);
            return null;
        }
    }
}
