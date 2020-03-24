package com.cdfive.learn.designpattern.singleton;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for {@link SingletonDemo1}.
 *
 * @author cdfive
 */
public class SingletonDemo1Test {

    @Test
    public void testGetInstance() {
        SingletonDemo1 obj1 = SingletonDemo1.getInstance();
        assertNotNull(obj1);

        SingletonDemo1 obj2 = SingletonDemo1.getInstance();
        assertSame(obj1, obj2);

        SingletonDemo1 obj3 = SingletonDemo1.getInstance();
        assertSame(obj1, obj3);
    }
}
