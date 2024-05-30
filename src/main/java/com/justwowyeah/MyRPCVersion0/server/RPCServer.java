package com.justwowyeah.MyRPCVersion0.server;

import com.justwowyeah.MyRPCVersion0.common.User;
import com.justwowyeah.MyRPCVersion0.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserService userservice = new UserServiceImpl();
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
                        // 从流中读取 int 数据，转换为 Integer 对象
                        // 如果不转换，JVM 也会在 (userservice 为 id 赋值时) 隐式转换
                        Integer id = ois.readInt();
                        User user = userservice.getUserById(id);
                        oos.writeObject(user);
                        // 不等缓冲区满，手动清空以发送数据
                        oos.flush();
                    } catch (IOException e) {
                        // 如果有 IO 异常，则打印 run() 堆栈追踪信息
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
