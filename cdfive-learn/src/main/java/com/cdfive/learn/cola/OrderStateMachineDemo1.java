package com.cdfive.learn.cola;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;

/**
 * @author cdfive
 */
public class OrderStateMachineDemo1 {

    public static void main(String[] args) {
        String machineId = "ORDER";

        StateMachineBuilder<OrderState, OrderEvent, OrderContext> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(OrderState.CREATING)
                .to(OrderState.TO_PAY)
                .on(OrderEvent.CREATE)
                .when((ctx) -> true)
                .perform((from, to, event, ctx) -> {
                    System.out.println(ctx.getOrderCode() + ": " + event + ", " + from + "=>" + to);
                });

        StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = builder.build(machineId);

        OrderContext ctx = new OrderContext("ORDER_1001");

        OrderState toState = stateMachine.fireEvent(OrderState.CREATING, OrderEvent.CREATE, ctx);

        System.out.println(toState);

        System.out.println("done");
    }
}
