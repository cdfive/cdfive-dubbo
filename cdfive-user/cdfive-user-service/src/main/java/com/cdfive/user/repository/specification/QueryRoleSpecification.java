package com.cdfive.user.repository.specification;

import com.cdfive.user.po.RolePo;
import com.cdfive.user.vo.role.QueryRoleListPageReqVo;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author cdfive
 */
@AllArgsConstructor
public class QueryRoleSpecification implements Specification<RolePo> {

    private QueryRoleListPageReqVo reqVo;

    @Override
    public Predicate toPredicate(Root<RolePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();

        String name = reqVo.getName();
        if (StringUtils.isNotBlank(name)) {
            predicates.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
        }

        Boolean enable = reqVo.getEnable();
        if (enable != null) {
            predicates.add(cb.equal(root.get("enable").as(Boolean.class), enable));
        }

        predicates.add(cb.equal(root.get("deleted").as(Boolean.class), false));

        query.where(predicates.toArray(new Predicate[0]));

        query.orderBy(cb.desc(root.get("createTime")));
        return query.getRestriction();
    }
}
