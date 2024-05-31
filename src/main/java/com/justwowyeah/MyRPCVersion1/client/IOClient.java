package com.justwowyeah.MyRPCVersion1.client;

import com.justwowyeah.MyRPCVersion1.common.RPCRequest;
import com.justwowyeah.MyRPCVersion1.common.RPCResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;

public class IOClient {
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) {
        // 建立Socket连接，创建流
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream objectoutputstream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectinputstream = new ObjectInputStream(socket.getInputStream())) {
            // 写入随机的 int 数据到缓冲区
            objectoutputstream.writeObject(request);
            // 不等缓冲区满，手动清空以发送数据
            objectoutputstream.flush();
            // 将接收到的网络字节流强转为 RPCResponse 类型
            RPCResponse response = (RPCResponse)objectinputstream.readObject();
            // 返回到 proxy 解析数据
            return response;
        } catch (IOException | ClassNotFoundException e) {
            // 如果有 IO 异常，则打印堆栈追踪信息
            e.printStackTrace();
            // 返回 null ，统一返回格式，埋下 NullPointerException 隐患
            return null;
        }
    }
}
