package com.cdfive.learn.cola.fsm;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.alibaba.cola.statemachine.impl.Debugger;
import org.apache.commons.lang3.StringUtils;

/**
 * @author cdfive
 */
public class OrderStateMachineDemo1 {

    public static void main(String[] args) {
        Debugger.enableDebug();

        String machineId = "ORDER";

        StateMachineBuilder<OrderState, OrderEvent, OrderContext> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(OrderState.INIT)
                .to(OrderState.TO_PAY)
                .on(OrderEvent.CREATE)
                .when((ctx) -> true)
                .perform((from, to, event, ctx) -> {
                    System.out.println(ctx.getOrderCode() + ": " + event + ", " + from + "=>" + to);
                });

        builder.externalTransition()
                .from(OrderState.TO_PAY)
                .to(OrderState.PAY_EXPIRED)
                .on(OrderEvent.EXPIRED_PAY)
                .when((ctx) -> true)
                .perform((from, to, event, ctx) -> {
                    System.out.println(ctx.getOrderCode() + ": " + event + ", " + from + "=>" + to);
                });

        StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = builder.build(machineId);

        OrderContext ctx = new OrderContext("ORDER_1001");

        System.out.println(stateMachine.fireEvent(OrderState.INIT, OrderEvent.CREATE, ctx));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        System.out.println(stateMachine.fireEvent(OrderState.TO_PAY, OrderEvent.EXPIRED_PAY, ctx));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        System.out.println(stateMachine.fireEvent(OrderState.INIT, OrderEvent.EXPIRED_PAY, ctx));

        System.out.println("done");
    }
}
