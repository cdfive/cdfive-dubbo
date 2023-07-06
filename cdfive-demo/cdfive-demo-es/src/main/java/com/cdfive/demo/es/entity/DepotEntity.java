package com.cdfive.demo.es.entity;

import com.cdfive.es.annotation.Document;
import com.cdfive.es.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(index = "depot")
public class DepotEntity implements Serializable {

    private static final long serialVersionUID = 914928899416540499L;

    @Id
    private String id;

    private String code;

    private String name;
}
