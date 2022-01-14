package com.cdfive.es.vo;

import java.io.Serializable;

/**
 * @author cdfive
 */
public class EsGeoPointVo implements Serializable {

    public EsGeoPointVo() {

    }

    public EsGeoPointVo(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    // 经度
    private Double lon;

    // 纬度
    private Double lat;

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
