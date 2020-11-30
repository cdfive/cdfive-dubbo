package com.cdfive.search.eo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author cdfive
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "bizlogs", shards = 1, replicas = 0)
public class BizLogEo {

    @Id
    @Field(type = FieldType.Integer, store = true)
    private Integer id;

    private String info;

    @Field(type = FieldType.Integer)
    private Integer keyId;

    private String ip;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date updateTime;

    @Field(type = FieldType.Boolean)
    private Boolean deleted;
}
