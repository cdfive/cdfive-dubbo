package com.cdfive.demo.springdatajpa.service;

import com.cdfive.demo.springdatajpa.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cdfive
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class MenuNPlusOneServiceTest {

    @Autowired
    private MenuNPlusOneService menuNPlusOneService;

    @Test
    public void testCase1() {
        menuNPlusOneService.case1();
    }

    @Test
    public void testCase1_1() {
        menuNPlusOneService.test1_1();
    }

    @Test
    public void testCase1_2() {
        menuNPlusOneService.case1_2();
    }

    @Test
    public void testCase1_3() {
        menuNPlusOneService.case1_3();
    }

    @Test
    public void testCase1_4() {
        menuNPlusOneService.case1_4();
    }

    @Test
    public void testCase3() {
        menuNPlusOneService.case3();
    }

    @Test
    public void testCase4() {
        menuNPlusOneService.case4();
    }
}
