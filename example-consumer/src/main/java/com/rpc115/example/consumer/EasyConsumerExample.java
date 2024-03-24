package com.rpc115.example.consumer;
import com.rpc115.example.common.model.User;
import com.rpc115.example.common.service.UserService;
import com.mglyy.rpc115.proxy.*;


/**
 * 简易服务消费者示例
 */

public class EasyConsumerExample {
    public static void main(String[] args) {
        // todo 获取UserService的实体类对象
        UserService userService = null;
        User user = new User();
        user.setName("115");
        //调用
        User newUser = userService.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        } else {
            System.out.println("user==null");
        }
    }
}
