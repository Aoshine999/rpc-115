package com.mglyy.rpc115.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{

    /**
     * 启动服务器
     * @param port
     */
    public void doStart(int port) {
        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();

        //创建HTTP服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        //监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        //启动HTTP服务其监听指定端口
        server.listen(port,result->{
           if(result.succeeded()) {
               System.out.println("Server is now listening on port" + port);
           }else {
               System.err.println("Failed to start server:" + result.cause());
           }
        });

    }
}
