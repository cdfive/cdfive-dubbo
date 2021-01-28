package com.cdfive.user.enums;

/**
 * @author cdfive
 */
public enum AdminStatusEnum {

    NORMAL(1),
    LOCKED(2),
    DISABLED(3);

    AdminStatusEnum(Integer value) {
        this.value = value;
    }

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
