package com.cdfive.demo.rabbitmq.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class TestVo implements Serializable {

    private Integer id;

    private String name;

    private Long num;

    private Double price;
}
