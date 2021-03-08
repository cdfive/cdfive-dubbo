package com.cdfive.demo.springdatajpa.repository;

import com.cdfive.demo.springdatajpa.entity.MenuPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cdfive
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuPo, Integer> {

}
