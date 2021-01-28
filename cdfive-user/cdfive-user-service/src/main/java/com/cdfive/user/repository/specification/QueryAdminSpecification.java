package com.cdfive.user.repository.specification;

import com.cdfive.user.enums.AdminStatusEnum;
import com.cdfive.user.po.AdminPo;
import com.cdfive.user.vo.admin.QueryAdminListPageReqVo;
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
public class QueryAdminSpecification implements Specification<AdminPo> {

    private QueryAdminListPageReqVo reqVo;

    @Override
    public Predicate toPredicate(Root<AdminPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();

        String name = reqVo.getName();
        if (StringUtils.isNotBlank(name)) {
            predicates.add(
                    cb.or(
                      cb.like(root.get("username").as(String.class), "%" + name + "%")
                    , cb.like(root.get("aliasname").as(String.class),"%" + name + "%")
                    ));
        }

        String mobile = reqVo.getMobile();
        if (StringUtils.isNotBlank(mobile)) {
            predicates.add(cb.like(root.get("mobile").as(String.class), "%" + mobile + "%"));
        }

        AdminStatusEnum status = reqVo.getStatus();
        if (status != null) {
            predicates.add(cb.equal(root.get("status").as(AdminStatusEnum.class), status));
        }

        predicates.add(cb.equal(root.get("deleted").as(Boolean.class), false));

        query.where(predicates.toArray(new Predicate[0]));

        query.orderBy(cb.desc(root.get("createTime")));
        return query.getRestriction();
    }
}
