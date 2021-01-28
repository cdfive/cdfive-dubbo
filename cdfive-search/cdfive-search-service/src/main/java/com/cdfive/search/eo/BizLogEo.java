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
 * @author cdfive
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(index = "bizlog")
public class BizLogEo implements Serializable {

    @Id
    private Integer id;

    private String info;

    private Integer keyId;

    private String ip;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date updateTime;

    private Boolean deleted;
}
