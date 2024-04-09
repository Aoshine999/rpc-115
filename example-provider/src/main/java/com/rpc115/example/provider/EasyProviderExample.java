package com.rpc115.example.provider;
import com.mglyy.rpc115.registry.LocalRegistry;
import com.mglyy.rpc115.server.HttpServer;
import com.mglyy.rpc115.server.VertxHttpServer;
import com.rpc115.example.common.service.UserService;

public class EasyProviderExample {
    public static void main(String[] args) {
        //注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        //提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
