package com.cdfive.es.vo;

import java.io.Serializable;

/**
 * @author cdfive
 */
public class EsEntityVo<ENTITY> implements Serializable {

    public EsEntityVo() {

    }

    public EsEntityVo(ENTITY entity) {
        this.entity = entity;
    }

    public EsEntityVo(ENTITY entity, Float score, Long version) {
        this.entity = entity;
        this.score = score;
        this.version = version;
    }

    // 文档对应实体
    private ENTITY entity;

    // 打分
    private Float score;

    // 版本号
    private Long version;

    public ENTITY getEntity() {
        return entity;
    }

    public void setEntity(ENTITY entity) {
        this.entity = entity;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
