package com.cdfive.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author cdfive
 */
@Data
@ToString(callSuper = true)
public class IntegerIdNameVo extends AbstractIdNameVo<Integer> {

    public IntegerIdNameVo() {
    }

    public IntegerIdNameVo(Integer id, String name) {
        super(id, name);
    }
}
