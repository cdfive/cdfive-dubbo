package com.cdfive.mp3.repository.db.specification;

import com.cdfive.mp3.entity.po.CategoryPo;
import com.cdfive.mp3.vo.category.QueryCategoryListPageReqVo;
import com.google.common.collect.ImmutableList;
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
public class QueryCategorySpecification implements Specification<CategoryPo> {

    private static final List<String> CAN_SORT_FIELDS = ImmutableList.of("id", "sort", "createTime", "updateTime");

    private QueryCategoryListPageReqVo reqVo;

    @Override
    public Predicate toPredicate(Root<CategoryPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();

        String name = reqVo.getName();
        if (StringUtils.isNotBlank(name)) {
            predicates.add(cb.like(root.get("categoryName").as(String.class), "%" + name + "%"));
        }

        predicates.add(cb.equal(root.get("deleted").as(Boolean.class), false));

        query.where(predicates.toArray(new Predicate[0]));


        String sortField = reqVo.getSortField();
        String sortOrder = reqVo.getSortOrder();
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder) && CAN_SORT_FIELDS.contains(sortField)) {
            if ("ascending".equals(sortOrder)) {
                query.orderBy(cb.asc(root.get(sortField)));
            } else {
                query.orderBy(cb.desc(root.get(sortField)));
            }
        } else {
            query.orderBy(cb.desc(root.get("id")));
        }

        return query.getRestriction();
    }
}
