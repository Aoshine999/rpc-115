package com.rpc115.example.provider;
import com.rpc115.example.common.model.User;
import com.rpc115.example.common.service.UserService;
public class UserServiceImpl implements UserService{

    public User getUser(User user) {
        System.out.println("用户名: " + user.getName());
        return user;
    }
}
