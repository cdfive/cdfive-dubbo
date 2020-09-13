package com.cdfive.ctf.es.config;

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
}
