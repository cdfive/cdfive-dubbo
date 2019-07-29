package com.cdfive.learn.designpattern.proxy;

/**
 * @author cdfive
 */
public class StaticProxyDemo implements UserService {

    private UserServiceImpl userServiceImpl;

    public StaticProxyDemo(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void addUser(String name) {
        System.out.println("add user start");
        userServiceImpl.addUser(name);
        System.out.println("add user end");
    }

    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserService userService = new StaticProxyDemo(userServiceImpl);
        userService.addUser("cdfive");
    }
}
