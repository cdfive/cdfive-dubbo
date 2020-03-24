package com.cdfive.learn.designpattern.singleton;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Test cases for {@link SingletonDemo3}.
 *
 * @author cdfive
 */
public class SingletonDemo3Test {

    @Test
    public void testGetInstance() {
        SingletonDemo3 obj1 = SingletonDemo3.getInstance();
        assertNotNull(obj1);

        SingletonDemo3 obj2 = SingletonDemo3.getInstance();
        assertSame(obj1, obj2);

        SingletonDemo3 obj3 = SingletonDemo3.getInstance();
        assertSame(obj1, obj3);
    }
}
