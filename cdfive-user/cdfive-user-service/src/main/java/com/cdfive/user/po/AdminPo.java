package com.cdfive.user.po;

import com.cdfive.support.jpa.po.BasePo;
import com.cdfive.user.enums.AdminStatusEnum;
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
@Table(name = "cdfive_admin", indexes = {@Index(name = "idx_username", columnList = "username")})
@org.hibernate.annotations.Table(appliesTo = "cdfive_admin", comment = "管理员表")
public class AdminPo extends BasePo<Integer> {

    @Column(length = 50, nullable = false)
    private String username;

    @Column(name = "pwd", length = 50, nullable = false)
    private String password;

    private String aliasname;

    @Column(length = 11)
    private String mobile;

    private AdminStatusEnum status;

    @ManyToMany
    @JoinTable(name = "cdfive_admin_role",
            joinColumns = {@JoinColumn(name = "admin_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"admin_id", "role_id"})})
    private List<RolePo> roles;
}
