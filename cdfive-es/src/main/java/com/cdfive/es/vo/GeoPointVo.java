package com.cdfive.es.vo;

import java.io.Serializable;

/**
 * @author cdfive
 * @date 2020-09-05
 */
public class GeoPointVo implements Serializable {

    private double lat;

    private double lon;

    public GeoPointVo() {

    }

    public GeoPointVo(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
