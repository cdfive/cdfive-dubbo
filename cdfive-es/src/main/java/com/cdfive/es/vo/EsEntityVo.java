package com.cdfive.es.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author cdfive
 */
public class EsEntityVo<ENTITY> implements Serializable {

    public EsEntityVo() {

    }

    public EsEntityVo(String id, ENTITY entity) {
        this.id = id;
        this.entity = entity;
    }

    public EsEntityVo(String id, ENTITY entity, Float score, Long version) {
        this.id = id;
        this.entity = entity;
        this.score = score;
        this.version = version;
    }

    public EsEntityVo(String id, ENTITY entity, Map<String, Object> extInfos, Float score, Long version) {
        this.id = id;
        this.entity = entity;
        this.extInfos = extInfos;
        this.score = score;
        this.version = version;
    }

    // 文档id
    private String id;

    // 文档对应实体
    private ENTITY entity;

    // 扩展信息
    private Map<String, Object> extInfos;

    // 打分
    private Float score;

    // 版本号
    private Long version;

    // sort values for search after
    private Object[] sortValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ENTITY getEntity() {
        return entity;
    }

    public void setEntity(ENTITY entity) {
        this.entity = entity;
    }

    public Map<String, Object> getExtInfos() {
        return extInfos;
    }

    public void setExtInfos(Map<String, Object> extInfos) {
        this.extInfos = extInfos;
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

    public Object[] getSortValues() {
        return sortValues;
    }

    public void setSortValues(Object[] sortValues) {
        this.sortValues = sortValues;
    }
}
