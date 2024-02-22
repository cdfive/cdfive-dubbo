package com.cdfive.learn.cola.fsm;

/**
 * @author cdfive
 */
public enum OrderState {

    INIT,

    TO_PAY,

    PAID,

    PAY_EXPIRED,

    PAY_CANCEL,

    TO_RECEIVE,

    RECEIVED;

//    REJECTED,

//    APPLY_RETURN,
//
//    RETURNING,
//
//    RETURN_RECEIVED,
//
//    RETURN_REJECTED,

//    FINISHED;
}
