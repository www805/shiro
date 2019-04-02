package com.zhuang.service.impl;

import com.zhuang.domain.User;
import com.zhuang.mapper.UserMapper;
import com.zhuang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public User findById(int id) {
        return userMapper.findById(id);
    }
}
