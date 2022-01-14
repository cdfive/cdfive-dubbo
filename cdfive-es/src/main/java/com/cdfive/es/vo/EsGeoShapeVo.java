package com.cdfive.es.vo;

import java.io.Serializable;

/**
 * @author cdfive
 */
public class EsGeoShapeVo implements Serializable {

    public EsGeoShapeVo() {

    }

    public EsGeoShapeVo(String type, Object coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    // 形状类型
    private String type;

    // 坐标点集合
    private Object coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }
}
