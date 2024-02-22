package com.cdfive.learn.cola.fsm.order;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.cdfive.learn.cola.fsm.OrderContext;
import com.cdfive.learn.cola.fsm.OrderEvent;
import com.cdfive.learn.cola.fsm.OrderState;
import com.cdfive.learn.cola.fsm.order.po.OrderPo;

/**
 * @author cdfive
 */
public class OrderStateMachineHelper {

    private StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = null;

    public OrderStateMachineHelper() {
        StateMachineBuilder<OrderState, OrderEvent, OrderContext> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(OrderState.INIT)
                .to(OrderState.TO_PAY)
                .on(OrderEvent.CREATE)
                .when((ctx) -> true)
                .perform(action());

        builder.externalTransition()
                .from(OrderState.TO_PAY)
                .to(OrderState.PAID)
                .on(OrderEvent.PAY)
                .when((ctx) -> true)
                .perform(action());

        builder.externalTransition()
                .from(OrderState.TO_PAY)
                .to(OrderState.PAY_EXPIRED)
                .on(OrderEvent.EXPIRED_PAY)
                .when((ctx) -> true)
                .perform(action());

        builder.externalTransition()
                .from(OrderState.TO_PAY)
                .to(OrderState.PAY_CANCEL)
                .on(OrderEvent.CANCEL_PAY)
                .when((ctx) -> true)
                .perform(action());

        builder.externalTransition()
                .from(OrderState.PAID)
                .to(OrderState.TO_RECEIVE)
                .on(OrderEvent.DELIVERY)
                .when((ctx) -> true)
                .perform(action());

        builder.externalTransition()
                .from(OrderState.TO_RECEIVE)
                .to(OrderState.RECEIVED)
                .on(OrderEvent.CONFIRM_RECEIVE)
                .when((ctx) -> true)
                .perform(action());

        stateMachine = builder.build("order_state_machine");
    }

    public StateMachine<OrderState, OrderEvent, OrderContext> getStateMachine() {
        return stateMachine;
    }

    private Action<OrderState, OrderEvent, OrderContext> action() {
        return new Action<OrderState, OrderEvent, OrderContext>() {
            @Override
            public void execute(OrderState from, OrderState to, OrderEvent event, OrderContext context) {
                OrderPo orderPo = context.getOrderPo();

                if (!from.equals(orderPo.getState())) {
                    throw new RuntimeException("订单状态非法更新,orderCode=" + orderPo.getOrderCode() + ",from="
                            + from + ",to=" + to + ",cur=" + orderPo.getState());
                }

                System.out.println(context.getOrderPo().getOrderCode() + " " + event + "=>" + to);
            }
        };
    }
}
