package com.justwowyeah.MyRPCVersion1.client;

import com.justwowyeah.MyRPCVersion1.common.RPCRequest;
import com.justwowyeah.MyRPCVersion1.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
// 实现 Invocationhandler 接口的 invoke() 以使用 reflect
public class ClientProxy implements InvocationHandler {
    private String host;
    private int port;
    //  invoke() 会在 proxy 运行时调用，获取 proxy 的调用数据，将数据封装成 RPCRequest 类型
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder()
                // 从 method 得到类名
                .interfaceName(method.getDeclaringClass().getName())
                // 从 method 得到方法名
                .methodName(method.getName())
                .params(args)
                // 从 method 得到未知参数类型
                .paramsType(method.getParameterTypes())
                .build();
        // 将封装后的数据通过 sendRequest() 传输至服务端，并接收 response
        RPCResponse response = IOClient.sendRequest(host, port, request);
        // 返回解封装后的 response 数据
        return response.getData();
    }
    // 建立参数对象的 proxy ，由于参数对象类型未知，使用泛型方法/根据实参/缺省匹配/类型
    <T>T getProxy(Class<T> clazz) {
        // 通过类加载器 ClassLoader 、未知类型接口数组、InvocationHandler实例，建立 proxy
        // 此处: 1.接口只有 clazz ，类加载器可以顺利加载类 2. invoke() 与 newProxyInstance() 位于同一类下
        // proxy 建立后，每次 proxy 运行代理对象的方法时都会进入 invoke()
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}