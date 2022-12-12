package com.liumang.lmusic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liumang.lmusic.entity.User;
import com.liumang.lmusic.mapper.UserMapper;
import com.liumang.lmusic.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
