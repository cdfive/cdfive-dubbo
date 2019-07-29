package com.cdfive.learn.designpattern.proxy;

/**
 * @author cdfive
 */
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(String name) {
        System.out.println("add user: name=" + name);
    }
}
