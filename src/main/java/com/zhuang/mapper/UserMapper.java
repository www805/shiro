package com.zhuang.mapper;

import com.zhuang.domain.User;

public interface UserMapper {

    User findByName(String name);

    User findById(int id);
}
