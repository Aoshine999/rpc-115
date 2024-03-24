package com.mglyy.rpc115.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{

    public void doStart(int port) {
        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();

        //创建HTTP服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        //监听端口并处理请求
        server.requestHandler(request -> {
           //处理HTTP请求
            System.out.println("Received request: " + request.method() + " " + request.uri());
        });
    }
}
