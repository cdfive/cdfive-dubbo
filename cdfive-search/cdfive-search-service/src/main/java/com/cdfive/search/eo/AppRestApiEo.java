package com.cdfive.search.eo;

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
@Document(index = "bizlog")
public class AppRestApiEo implements Serializable {

    private static final long serialVersionUID = 1498167530936786248L;

    @Id
    private String id;

    private String appName;

    private Integer serverPort;

    private String requestUri;

    private String remoteAddr;

    private Long costMs;

    private String requestBody;

    private String exClassName;

    private String exStackTrace;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;
}
