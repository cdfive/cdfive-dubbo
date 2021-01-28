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
@Table(name = "cdfive_role")
@org.hibernate.annotations.Table(appliesTo = "cdfive_role", comment = "角色表")
public class RolePo extends BasePo<Integer> {

    private String name;

    private String description;

    private Boolean enable;

    @ManyToMany
    @JoinTable(name = "cdfive_admin_role",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "admin_id", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "admin_id"})})
    private List<AdminPo> admins;

    @ManyToMany
    @JoinTable(name = "cdfive_role_menu",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "menu_id"})})
    private List<MenuPo> menus;
}
