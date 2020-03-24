package com.cdfive.learn.designpattern.singleton;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Test cases for {@link SingletonDemo2}.
 *
 * @author cdfive
 */
public class SingletonDemo2Test {

    @Test
    public void testGetInstance() {
        SingletonDemo2 obj1 = SingletonDemo2.getInstance();
        assertNotNull(obj1);

        SingletonDemo2 obj2 = SingletonDemo2.getInstance();
        assertSame(obj1, obj2);

        SingletonDemo2 obj3 = SingletonDemo2.getInstance();
        assertSame(obj1, obj3);
    }
}
