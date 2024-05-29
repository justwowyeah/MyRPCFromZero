package com.justwowyeah.MyRPCVersion0.client;

import com.justwowyeah.MyRPCVersion0.common.User;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {
        try {
            // 建立Socket连接
            Socket socket = new Socket("127.0.0.1", 8899);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            Random random = new Random();
            // 写入 int 类型到缓冲区
            objectOutputStream.writeInt(random.nextInt());
            // 清空缓冲区
            objectOutputStream.flush();
            // 将接收到的网络字节流强转为 User 类型
            User user = (User) objectInputStream.readObject();
            System.out.println("从服务端接收到的User：" + user);

        } catch (IOException | ClassNotFoundException e) {
            e.getStackTrace();
            System.out.println("客户端启动失败");
        }


    }
}
