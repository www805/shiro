package com.zhuang.service;

import com.zhuang.domain.User;

public interface UserService {

    User findByName(String name);

    User findById(int id);

}
