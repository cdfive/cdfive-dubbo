package com.cdfive.user.po;

import com.cdfive.support.jpa.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cdfive_menu")
@org.hibernate.annotations.Table(appliesTo = "cdfive_menu", comment = "菜单表")
public class MenuPo extends BasePo<Integer> {

    @ManyToOne
    private MenuPo parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent")
//    @OrderBy(value = "sort")
    private List<MenuPo> subs;

    private String name;

    private String description;

    private String url;

    private String icon;

    @ManyToMany
    @JoinTable(name = "cdfive_role_menu",
            joinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "menu_id"})})
    private List<MenuPo> menus;
}
