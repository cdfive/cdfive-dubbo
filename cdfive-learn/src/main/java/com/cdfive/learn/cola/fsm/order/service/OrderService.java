package com.cdfive.learn.cola.fsm.order.service;

import com.cdfive.learn.cola.fsm.OrderContext;
import com.cdfive.learn.cola.fsm.OrderEvent;
import com.cdfive.learn.cola.fsm.OrderState;
import com.cdfive.learn.cola.fsm.order.OrderStateMachineHelper;
import com.cdfive.learn.cola.fsm.order.po.OrderPo;
import com.cdfive.learn.cola.fsm.order.vo.*;

/**
 * @author cdfive
 */
public class OrderService {

    private OrderStateMachineHelper machine = new OrderStateMachineHelper();

    public CreateOrderRespVo createOrder(CreateOrderReqVo reqVo) {
        OrderPo orderPo = new OrderPo();
        String orderCode = "B2C000001";
        orderPo.setOrderCode(orderCode);
        orderPo.setState(OrderState.INIT);
        saveOrderPo(orderPo);

        updateOrderState(orderCode, OrderState.INIT, OrderEvent.CREATE);

        CreateOrderRespVo respVo = new CreateOrderRespVo();
        respVo.setOrderCode(orderPo.getOrderCode());
        return respVo;
    }

    public PayOrderRespVo payOrder(PayOrderReqVo reqVo) {
        updateOrderState(reqVo.getOrderCode(), OrderState.TO_PAY, OrderEvent.PAY);
        return null;
    }

    public ExpiredPayRespVo expiredPay(ExpiredPayReqVo reqVo) {
        updateOrderState(reqVo.getOrderCode(), OrderState.TO_PAY, OrderEvent.EXPIRED_PAY);
        return null;
    }

    public CancelPayRespVo cancelPay(CancelPayReqVo reqVo) {
        updateOrderState(reqVo.getOrderCode(), OrderState.TO_PAY, OrderEvent.CANCEL_PAY);
        return null;
    }

    public DeliveryRespVo delivery(DeliveryReqVo reqVo) {
        updateOrderState(reqVo.getOrderCode(), OrderState.PAID, OrderEvent.DELIVERY);
        return null;
    }

    public ConfirmReceiveReqVo confirmReceive(ConfirmReceiveReqVo reqVo) {
        updateOrderState(reqVo.getOrderCode(), OrderState.TO_RECEIVE, OrderEvent.CONFIRM_RECEIVE);
        return null;
    }

    public void updateOrderState(String orderCode, OrderState state, OrderEvent event) {
        OrderPo orderPo = queryOrderPo(orderCode);

        OrderContext context = new OrderContext();
        context.setOrderPo(orderPo);

        OrderState toState = machine.getStateMachine().fireEvent(state, event, context);

        orderPo.setState(toState);
        updateOrderPo(orderPo);
    }

    private OrderPo orderPo;

    public OrderPo queryOrderPo(String orderCode) {
        return orderPo;
    }

    public void saveOrderPo(OrderPo orderPo) {
        this.orderPo = orderPo;
    }

    public void updateOrderPo(OrderPo orderPo) {
        this.orderPo.setState(orderPo.getState());
    }
}
