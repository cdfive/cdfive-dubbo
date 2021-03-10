package com.cdfive.demo.springdatajpa.service.impl;

import com.cdfive.demo.springdatajpa.entity.MenuEntity;
import com.cdfive.demo.springdatajpa.repository.MenuRepository;
import com.cdfive.demo.springdatajpa.service.MenuNPlusOneService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Test n+1, how many sql will be generated
 *
 * delete from menu;
 * insert into menu(name,parent_id) values('商品管理',null);
 * insert into menu(name,parent_id) values('商品列表',1);
 * insert into menu(name,parent_id) values('分类列表',1);
 *
 * @author cdfive
 */
@Transactional
@Service
public class MenuNPlusOneServiceImpl implements MenuNPlusOneService {

    @Autowired
    private MenuRepository menuRepository;

    /**
     * Only 1 sql
     * Hibernate:
     *     select
     *         menupo0_.id as id1_0_,
     *         menupo0_.name as name2_0_,
     *         menupo0_.parent_id as parent_i3_0_
     *     from
     *         menu menupo0_
     */
    @Override
    public void case1() {
        List<MenuEntity> menuEntities = menuRepository.findAll();
        for (MenuEntity menuEntity : menuEntities) {
            System.out.println(menuEntity.getId() + "=>name=" + menuEntity.getName());
        }
        System.out.println("case2 done!");
    }

    /**
     * Only 1 sql
     * Hibernate:
     *     select
     *         menupo0_.id as id1_0_,
     *         menupo0_.name as name2_0_,
     *         menupo0_.parent_id as parent_i3_0_
     *     from
     *         menu menupo0_
     */
    @Override
    public void case2() {
        List<MenuEntity> menuEntities = menuRepository.findAll();
        for (MenuEntity menuEntity : menuEntities) {
            System.out.println(menuEntity.getId() + "=>name=" + menuEntity.getName()
                + ",parent=" + Optional.ofNullable(menuEntity.getParent()).map(o -> o.getName()).orElse(null));
        }
        System.out.println("case3 done!");
    }

    /**
     * 2 sql
     * Hibernate:
     *     select
     *         menupo0_.id as id1_0_0_,
     *         menupo0_.name as name2_0_0_,
     *         menupo0_.parent_id as parent_i3_0_0_,
     *         menupo1_.id as id1_0_1_,
     *         menupo1_.name as name2_0_1_,
     *         menupo1_.parent_id as parent_i3_0_1_
     *     from
     *         menu menupo0_
     *     left outer join
     *         menu menupo1_
     *             on menupo0_.parent_id=menupo1_.id
     *     where
     *         menupo0_.id=?
     * Hibernate:
     *     select
     *         children0_.parent_id as parent_i3_0_0_,
     *         children0_.id as id1_0_0_,
     *         children0_.id as id1_0_1_,
     *         children0_.name as name2_0_1_,
     *         children0_.parent_id as parent_i3_0_1_
     *     from
     *         menu children0_
     *     where
     *         children0_.parent_id=?
     */
    @Override
    public void case3() {
        MenuEntity menuEntity = menuRepository.findById(1).get();
        System.out.println(menuEntity.getId() + "=>name=" + menuEntity.getName() + ",children size=" + menuEntity.getChildren().size());
        System.out.println("case4 done!");
    }

    /**
     * 4 sql
     * Hibernate:
     *     select
     *         menupo0_.id as id1_0_0_,
     *         menupo0_.name as name2_0_0_,
     *         menupo0_.parent_id as parent_i3_0_0_,
     *         menupo1_.id as id1_0_1_,
     *         menupo1_.name as name2_0_1_,
     *         menupo1_.parent_id as parent_i3_0_1_
     *     from
     *         menu menupo0_
     *     left outer join
     *         menu menupo1_
     *             on menupo0_.parent_id=menupo1_.id
     *     where
     *         menupo0_.id=?
     * Hibernate:
     *     select
     *         children0_.parent_id as parent_i3_0_0_,
     *         children0_.id as id1_0_0_,
     *         children0_.id as id1_0_1_,
     *         children0_.name as name2_0_1_,
     *         children0_.parent_id as parent_i3_0_1_
     *     from
     *         menu children0_
     *     where
     *         children0_.parent_id=?
     * Hibernate:
     *     select
     *         children0_.parent_id as parent_i3_0_0_,
     *         children0_.id as id1_0_0_,
     *         children0_.id as id1_0_1_,
     *         children0_.name as name2_0_1_,
     *         children0_.parent_id as parent_i3_0_1_
     *     from
     *         menu children0_
     *     where
     *         children0_.parent_id=?
     * Hibernate:
     *     select
     *         children0_.parent_id as parent_i3_0_0_,
     *         children0_.id as id1_0_0_,
     *         children0_.id as id1_0_1_,
     *         children0_.name as name2_0_1_,
     *         children0_.parent_id as parent_i3_0_1_
     *     from
     *         menu children0_
     *     where
     *         children0_.parent_id=?
     */
    @Override
    public void case4() {
        ObjectMapper mapper = new ObjectMapper();
        MenuEntity menuEntity = menuRepository.findById(1).get();
        try {
            // {"id":1,"name":"商品管理","children":[{"id":2,"name":"商品列表","children":[]},{"id":3,"name":"分类列表","children":[]}]}
            System.out.println(mapper.writeValueAsString(menuEntity));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
