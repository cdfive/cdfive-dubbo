package com.cdfive.demo.springdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author cdfive
 */
@Data
@Entity
@Table(name = "menu")
public class MenuPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    // default fetch type is FetchType.EAGER
    @ManyToOne
    @JsonIgnore
    private MenuPo parent;

    // default fetch type is FetchType.LAZY
    @OneToMany(mappedBy = "parent")
    private List<MenuPo> children;
}
