package com.cdfive.learn.designpattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author cdfive
 */
public class CglibProxyDemo implements MethodInterceptor {

    private Object target;

    private Object bind(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("add user start");
        Object result = method.invoke(target, args);
        System.out.println("add user end");
        return result;
    }

    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        CglibProxyDemo cglibProxyDemo = new CglibProxyDemo();
        UserService userService = (UserService) cglibProxyDemo.bind(userServiceImpl);
        userService.addUser("cdfive");
    }
}
