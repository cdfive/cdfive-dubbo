package com.cdfive.learn.cola.fsm;

import com.cdfive.learn.cola.fsm.order.po.OrderPo;
import com.cdfive.learn.cola.fsm.order.vo.CreateOrderReqVo;
import com.cdfive.learn.cola.fsm.order.vo.CreateOrderRespVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderContext {

    public OrderContext(String orderCode) {
        this.orderCode = orderCode;
    }

    private String orderCode;

    private OrderPo orderPo;
}
