package com.cdfive.learn.cola.fsm;

/**
 * @author cdfive
 */
public enum OrderEvent {

    CREATE,

    PAY_SUCCESS,

    PAY_FAILED,

    PAY_EXPIRED;
}
