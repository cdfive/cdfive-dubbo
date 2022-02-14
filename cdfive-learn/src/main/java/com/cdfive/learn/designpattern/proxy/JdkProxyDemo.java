package com.cdfive.learn.designpattern.proxy;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author cdfive
 */
public class JdkProxyDemo implements InvocationHandler {

    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("add user start");
        Object result = method.invoke(target, args);
        System.out.println("add user end");
        return result;
    }

    public static void main(String[] args) {
        test1();

        System.out.println(StringUtils.center("分隔线", 32, "#"));

        test2();
    }

    public static void test1() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        JdkProxyDemo jdkProxyDemo = new JdkProxyDemo();
        UserService userService = (UserService) jdkProxyDemo.bind(userServiceImpl);
        userService.addUser("cdfive");
    }

    public static void test2() {
        UserService userServiceImpl = new UserServiceImpl();
        JdkProxyDemo jdkProxyDemo = new JdkProxyDemo();
        UserService userService = (UserService) jdkProxyDemo.bind(userServiceImpl);
        userService.addUser("cdfive");
    }
}
