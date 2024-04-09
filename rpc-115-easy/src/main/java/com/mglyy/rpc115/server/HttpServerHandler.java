package com.mglyy.rpc115.server;

import com.mglyy.rpc115.model.RpcRequest;
import com.mglyy.rpc115.model.RpcResponse;
import com.mglyy.rpc115.registry.LocalRegistry;
import com.mglyy.rpc115.serializer.JdkSerializer;
import com.mglyy.rpc115.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        //指定序列化器
        final Serializer serializer = new JdkSerializer();

        //记录日志
        System.out.println("Received request:" + httpServerRequest.method() + " " + httpServerRequest.uri());

        //异步处理Http请求
        httpServerRequest.bodyHandler(body->{
           byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try{
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            }catch (Exception e){
                e.printStackTrace();
            }


            //构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            //如果请求为null,直接返回
            if(rpcRequest == null){
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(httpServerRequest,rpcResponse,serializer);
                return;
            }

            try{
                //获取要调用的服务实现类，通过反射调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Constructor<?> constructor = implClass.getConstructor();
                Method method = implClass.getMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());
                Object result = method.invoke(constructor.newInstance(),rpcRequest.getArgs());
                //封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            }catch (Exception e){
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            //响应
            doResponse(httpServerRequest,rpcResponse,serializer);
        });
    }

    /**
     * 响应
     *
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    void doResponse(HttpServerRequest request,RpcResponse rpcResponse,Serializer serializer){
        HttpServerResponse httpServerResponse = request.response().putHeader("content-type","application/json");
        try{
            //序列化
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));

        }catch (IOException e){
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
