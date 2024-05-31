package com.justwowyeah.MyRPCVersion1.server;

import com.justwowyeah.MyRPCVersion1.common.User;
import com.justwowyeah.MyRPCVersion1.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        System.out.println("客户端查询了 id = " + id + " 的用户信息");
        return User.builder()
                .id(id)
                .usrName(String.valueOf(UUID.randomUUID()))
                .sex((new Random()).nextBoolean())
                .build();
    }

    @Override
    public Integer insertUser(User user) {
        System.out.println("数据插入成功：" + user);
        return 1;
    }
}
