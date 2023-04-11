package com.cdfive.search.eo;

import com.alibaba.fastjson.annotation.JSONField;
import com.cdfive.es.annotation.Document;
import com.cdfive.es.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiejihan
 * @date 2023-04-07
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(index = "appRestApiLog")
public class AppRestApiLogEo implements Serializable {

    private static final long serialVersionUID = 1498167530936786248L;

    @Id
    @JSONField(ordinal = 1)
    private String id;

    @JSONField(ordinal = 2)
    private String appName;

    @JSONField(ordinal = 3)
    private Integer serverPort;

    @JSONField(ordinal = 4)
    private String requestUri;

    @JSONField(ordinal = 5)
    private String remoteAddr;

    @JSONField(ordinal = 6)
    private Long costMs;

    @JSONField(ordinal = 7)
    private String requestBody;

    @JSONField(ordinal = 8)
    private String exClassName;

    @JSONField(ordinal = 9)
    private String exStackTrace;

    @JSONField(ordinal = 10, format = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;

    @JSONField(ordinal = 11, format = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updateTime;
}
