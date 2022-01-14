package com.cdfive.es.vo;

import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.common.lucene.uid.Versions;
import org.elasticsearch.index.VersionType;

import java.io.Serializable;

/**
 * @author cdfive
 */
public class EsWriteOptionVo implements Serializable {

    public static final EsWriteOptionVo OPTION_REFRESH_WAIT_UNTIL = new EsWriteOptionVo(WriteRequest.RefreshPolicy.WAIT_UNTIL);
    public static final EsWriteOptionVo OPTION_REFRESH_IMMEDIATE = new EsWriteOptionVo(WriteRequest.RefreshPolicy.IMMEDIATE);

    public EsWriteOptionVo() {

    }

    public EsWriteOptionVo(WriteRequest.RefreshPolicy refreshPolicy) {
        this.refreshPolicy = refreshPolicy;
    }

    public EsWriteOptionVo(WriteRequest.RefreshPolicy refreshPolicy, String routing, long version, VersionType versionType) {
        this.refreshPolicy = refreshPolicy;
        this.routing = routing;
        this.version = version;
        this.versionType = versionType;
    }

    private WriteRequest.RefreshPolicy refreshPolicy;

    private String routing;

    private long version = Versions.MATCH_ANY;

    private VersionType versionType = VersionType.INTERNAL;

    public WriteRequest.RefreshPolicy getRefreshPolicy() {
        return refreshPolicy;
    }

    public void setRefreshPolicy(WriteRequest.RefreshPolicy refreshPolicy) {
        this.refreshPolicy = refreshPolicy;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public VersionType getVersionType() {
        return versionType;
    }

    public void setVersionType(VersionType versionType) {
        this.versionType = versionType;
    }
}
