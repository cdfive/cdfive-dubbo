package com.cdfive.log.event;

import com.cdfive.log.po.BizLogPo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author cdfive
 */
@Getter
@Setter
public class AddBizLogEvent extends ApplicationEvent {

    private BizLogPo bizLogPo;

    public AddBizLogEvent(Object source, BizLogPo bizLogPo) {
        super(source);
        this.bizLogPo = bizLogPo;
    }
}
