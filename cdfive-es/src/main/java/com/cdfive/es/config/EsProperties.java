package com.cdfive.es.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cdfive
 */
@ConfigurationProperties(prefix = "spring.data.elasticsearch")
public class EsProperties {

    private String clusterNodes;

    private String username;

    private String password;

    private Integer connectionRequestTimout = 1000;

    private Integer connetionTimeout = 5000;

    private Integer socketTimeout = 60000;

    private Integer maxConnPerRoute = 2;

    private Integer maxConnTotal = 20;

    private Boolean trackTotalHits = true;

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getConnectionRequestTimout() {
        return connectionRequestTimout;
    }

    public void setConnectionRequestTimout(Integer connectionRequestTimout) {
        this.connectionRequestTimout = connectionRequestTimout;
    }

    public Integer getConnetionTimeout() {
        return connetionTimeout;
    }

    public void setConnetionTimeout(Integer connetionTimeout) {
        this.connetionTimeout = connetionTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(Integer maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public Integer getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(Integer maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }

    public Boolean getTrackTotalHits() {
        return trackTotalHits;
    }

    public void setTrackTotalHits(Boolean trackTotalHits) {
        this.trackTotalHits = trackTotalHits;
    }
}
