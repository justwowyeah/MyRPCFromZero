package com.justwowyeah.MyRPCVersion2.service;

import com.justwowyeah.MyRPCVersion2.common.User;

public interface UserService {
    User getUserById(Integer id);
    Integer insertUser(User user);
}