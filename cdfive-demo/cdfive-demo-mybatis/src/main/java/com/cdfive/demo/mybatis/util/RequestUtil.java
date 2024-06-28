package com.cdfive.demo.mybatis.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cdfive
 */
public class RequestUtil {

    public static String REQUEST_NAME_REQ = "_req";

    public static String REQUEST_NAME_RESP = "_resp";

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return null;
        }

        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static void setRequestAttrReq(Object req) {
        getRequest().setAttribute(REQUEST_NAME_REQ, req);
    }

    public static Object getRequestAttrReq() {
        return getRequest().getAttribute(REQUEST_NAME_REQ);
    }

    public static void setRequestAttrResp(Object resp) {
        getRequest().setAttribute(REQUEST_NAME_REQ, resp);
    }

    public static Object getRequestAttrResp() {
        return getRequest().getAttribute(REQUEST_NAME_RESP);
    }
}
