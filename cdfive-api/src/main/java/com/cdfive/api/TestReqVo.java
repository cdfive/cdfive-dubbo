package com.cdfive.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestReqVo implements Serializable {

    private static final long serialVersionUID = 117827441864238656L;

    private String test;
}
