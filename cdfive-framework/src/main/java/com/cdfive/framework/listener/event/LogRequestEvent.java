package com.cdfive.framework.listener.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cdfive
 */
@Data
public class LogRequestEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1781688576385028432L;

    private HttpServletRequest request;

    private Throwable ex;

    public LogRequestEvent(Object source) {
        super(source);
    }

    public LogRequestEvent(Object source, HttpServletRequest request, Throwable ex) {
        super(source);
        this.request = request;
        this.ex = ex;
    }
}
