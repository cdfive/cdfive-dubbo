package com.cdfive.learn.cola.fsm;

/**
 * @author cdfive
 */
public enum OrderState {

    CREATING,

    TO_PAY,

    EXPIRED,

    PAID,

    DELIVERED,

    RECEIVED,

    REJECTED,

    FINISHED,

    CLOSED;
}
