package com.justwowyeah.MyRPCVersion2.server;

import com.justwowyeah.MyRPCVersion2.common.RPCResponse;
import com.justwowyeah.MyRPCVersion2.common.RPCRequest;
import com.justwowyeah.MyRPCVersion2.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        // 使用 try-with-resources 建立 ServerSocket 连接
        try (ServerSocket serverSocket = new ServerSocket(8899)) {
            System.out.println("服务端已启动");
            while (true) {
                // ServerSocket 以 BIO 监听 8899 端口，一旦获取到连接就创建 Socket 对象
                Socket socket = serverSocket.accept();
                // 异步创建 runnable 线程，使用 lamda 表达式重写 run()
                new Thread(() -> {
                    // 创建流
                    try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                         ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                        // 读取字节流数据，转换为 RPCRequest 对象处理
                        RPCRequest request = (RPCRequest)ois.readObject();
                        // 根据请求获取被调用的方法
                        Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamsType());
                        // 调用对象/触发方法，保存返回对象
                        Object invoke = method.invoke(userService, request.getParams());
                        // 封装数据，以字节流写入缓冲区
                        oos.writeObject(RPCResponse.success(invoke));
                        // 不等缓冲区满，手动清空以发送数据
                        oos.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                             InvocationTargetException e) {
                        // 如果有异常，则打印 run() 堆栈追踪信息
                        e.printStackTrace();
                        System.out.println("IO错误");
                    }
                // 使用 start() 调用 run() 运行新线程
                }).start();
            }
        } catch (IOException e) {
            // 如果有 IO 异常，则打印 serverSocket 堆栈追踪信息
            e.printStackTrace();
            System.out.println("服务端启动失败");
        }
    }
}