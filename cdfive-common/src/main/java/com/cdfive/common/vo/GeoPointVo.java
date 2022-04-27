package com.cdfive.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeoPointVo implements Serializable {

    private BigDecimal lon;

    private BigDecimal lat;
}
