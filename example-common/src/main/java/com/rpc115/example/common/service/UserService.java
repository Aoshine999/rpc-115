package com.rpc115.example.common.service;
import com.rpc115.example.common.model.User;
/*
用户服务
 */

public interface UserService {
    /**
     * 获取用户
     *
     * @params user
     * @return User对象
     */
    User getUser(User user);
}
