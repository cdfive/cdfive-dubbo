package com.cdfive.learn.cola.fsm;

/**
 * @author cdfive
 */
public class OrderStateMachine {

    private static OrderStateMachine instance = null;

    public static void main(String[] args) {
        OrderStateMachine stateMachine = OrderStateMachine.getInstance();


    }

    public static OrderStateMachine getInstance() {
        if (instance == null) {
            synchronized(OrderStateMachine.class) {
                if (instance == null) {
                    instance = new OrderStateMachine();
                }
            }
        }

        return instance;
    }

    private OrderStateMachine() {

    }

    public void createOrder() {

    }

    public void payOrder() {

    }
}
