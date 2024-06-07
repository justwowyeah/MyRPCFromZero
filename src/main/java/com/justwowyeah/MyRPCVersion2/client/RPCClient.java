package com.justwowyeah.MyRPCVersion2.client;

import com.justwowyeah.MyRPCVersion2.common.User;
import com.justwowyeah.MyRPCVersion2.service.UserService;

public class RPCClient {
    public static void main(String[] args) {
        // 建立接口对象的代理
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService proxy = clientProxy.getProxy(UserService.class);
        // 反射调用方法，将 Object 类型转换为 User 类型
        User userById = proxy.getUserById(10);
        System.out.println("查询 id = 10 的用户信息");
        System.out.println("服务端返回的 User 数据：" + userById);
        // 反射调用方法，构建 User 类型数据
        User user = User.builder().id(100).usrName("张三").sex(true).build();
        Integer id = proxy.insertUser(user);
        System.out.println("向服务端插入数据成功：" + id);
    }
}