package com.cdfive.common.servlet.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author cdfive
 */
public class PageFilter extends OncePerRequestFilter {

    // private Log log = LogFactory.getLog(getClass());

    private String pathPrefix;

    private String prefix;

    private String suffix;

    private String ignorePath;

    private String preloadPath;

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setIgnorePath(String ignorePath) {
        this.ignorePath = ignorePath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String pageUri = uri;
        if (pathPrefix != null) {
            pageUri = uri.substring(pathPrefix.length());
        }

        if (ignorePath != null) {
            String[] ignorePaths = ignorePath.split(",");
            for (String tmpPath : ignorePaths) {
                if (pageUri.startsWith(tmpPath)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        if (pageUri.equals("/")) {
//            filterChain.doFilter(request, response);
//            return;
            pageUri += "index";
        }

        Enumeration<?> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = request.getParameter(name);
            request.setAttribute(name, value);
        }

        pageUri = prefix + pageUri + suffix;
        request.getRequestDispatcher(pageUri).forward(request, response);
    }
}
