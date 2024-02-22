package com.cdfive.learn.cola.fsm;

/**
 * @author cdfive
 */
public enum OrderEvent {

    CREATE,

    PAY,

    EXPIRED_PAY,

    CANCEL_PAY,

    DELIVERY,

    CONFIRM_RECEIVE;
}
