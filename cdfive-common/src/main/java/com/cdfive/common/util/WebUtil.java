package com.cdfive.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cdfive
 */
@Slf4j
public abstract class WebUtil {

    public static HttpServletRequest getCurrentHttpServletRequest() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request
                .getHeader("X-Requested-With").toString()));
    }

    public static String getIp() {
        return getIp(getCurrentHttpServletRequest());
    }

    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String unknow = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            return ip.split(",")[0];
        } else {
            return null;
        }
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();
        int begin = url.indexOf("//");
        begin = begin > 0 ? begin + 2 : 0;
        int end = StringUtils.equals(uri, "/") ? url.lastIndexOf(uri) : url.indexOf(uri);
        end = end > 0 ? end : url.length();
        return url.substring(begin, end);
    }

    public static void preloadJs(HttpServletResponse response) {
        try {
            PrintWriter pw;
            pw = response.getWriter();
            pw.append("<script type='text/javascript' src='/static/plugin/queryloader2/queryloader2.js'></script>");
            pw.append("<!-- Preloader -->");
            pw.append("<script type='text/javascript'>");
            pw.append("  window.addEventListener('DOMContentLoaded', function() {");
            pw.append("new QueryLoader2(document.querySelector('body'), {");
            pw.append("barColor: '#efefef',");
            pw.append("backgroundColor: '#111',");
            pw.append("percentage: true,");
            pw.append("barHeight: 1,");
            pw.append("minimumTime: 200,");
            pw.append("fadeOutTime: 1000");
            pw.append("});");
            pw.append("});");
            pw.append("</script>");
        } catch (IOException e) {
            log.error("[exception]WebUtil preloadJs", e);
        }
    }
}
