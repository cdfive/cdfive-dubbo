package com.cdfive.common.servlet.filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author cdfive
 */
public class FilterProxy implements Filter {
    private Filter proxy;

    @Override
    public void init(FilterConfig config) throws ServletException {
        String targetFilterBean = config.getInitParameter("targetFilterBean");
        ServletContext servletContext = config.getServletContext();
        WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        proxy = (Filter) springContext.getBean(targetFilterBean);
        proxy.init(config);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        proxy.doFilter(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void destroy() {

    }
}
