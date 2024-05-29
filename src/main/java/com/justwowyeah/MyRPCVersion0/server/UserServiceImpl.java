package com.justwowyeah.MyRPCVersion0.server;

import com.justwowyeah.MyRPCVersion0.common.User;
import com.justwowyeah.MyRPCVersion0.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        return User.builder()
                .id(id)
                .usrName(String.valueOf(UUID.randomUUID()))
                .sex((new Random()).nextBoolean())
                .build();
    }
}
