package com.cdfive.learn.designpattern.proxy;

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
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        JdkProxyDemo jdkProxyDemo = new JdkProxyDemo();
        UserService userService = (UserService) jdkProxyDemo.bind(userServiceImpl);
        userService.addUser("cdfive");
    }
}
