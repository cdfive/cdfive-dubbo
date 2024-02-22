package com.cdfive.learn.cola.fsm.order;

import com.cdfive.learn.cola.fsm.order.service.OrderService;
import com.cdfive.learn.cola.fsm.order.vo.*;

/**
 * @author cdfive
 */
public class OrderApplication {

    public static void main(String[] args) {
        OrderService service = new OrderService();

        CreateOrderRespVo createOrderRespVo = service.createOrder(new CreateOrderReqVo());
        String orderCode = createOrderRespVo.getOrderCode();

        service.payOrder(new PayOrderReqVo(orderCode));

        service.delivery(new DeliveryReqVo(orderCode));

        // 订单状态非法更新,orderCode=B2C000001,from=TO_PAY,to=PAY_CANCEL,cur=TO_RECEIVE
//        service.cancelPay(new CancelPayReqVo(orderCode));

        service.confirmReceive(new ConfirmReceiveReqVo(orderCode));

        System.out.println("done");
    }
}
