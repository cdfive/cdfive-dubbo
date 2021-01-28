package com.cdfive.common.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BootstrapPageRespVo<T> implements Serializable {

    private int total;

    private List<T> rows;
}
