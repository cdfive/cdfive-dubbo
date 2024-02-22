package com.cdfive.learn.cola.fsm.order.po;

import com.cdfive.learn.cola.fsm.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPo {

    private Long id;

    private String orderCode;

    private OrderState state;
}
