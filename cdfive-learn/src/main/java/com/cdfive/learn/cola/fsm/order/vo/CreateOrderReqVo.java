package com.cdfive.learn.cola.fsm.order.vo;

import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class CreateOrderReqVo {

    private Long userId;

    private String productCode;
}
