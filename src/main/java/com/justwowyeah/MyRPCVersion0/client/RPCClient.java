package com.justwowyeah.MyRPCVersion0.client;

import com.justwowyeah.MyRPCVersion0.common.User;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {
        // 建立Socket连接，创建流
        try (Socket socket = new Socket("127.0.0.1", 8899);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            // 写入随机的 int 数据到缓冲区
            objectOutputStream.writeInt(new Random().nextInt());
            // 不等缓冲区满，手动清空以发送数据
            objectOutputStream.flush();

            // 将接收到的网络字节流强转为 User 类型
            User user = (User) objectInputStream.readObject();
            System.out.println("从服务端接收到的User：" + user);
        } catch (IOException | ClassNotFoundException e) {
            // 如果有 IO 异常，则打印堆栈追踪信息
            e.printStackTrace();
            System.out.println("客户端启动失败");
        }
    }
}
