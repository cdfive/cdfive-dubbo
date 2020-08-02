package com.cdfive.search.eo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author cdfive
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "bizlogs", type = "bizLog", shards = 1, replicas = 0)
public class BizLogEo {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private Integer id;

    private String info;

    @Field(type = FieldType.Integer)
    private Integer keyId;

    private String ip;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date)
    private Date updateTime;

    @Field(type = FieldType.Boolean)
    private Boolean deleted;
}
