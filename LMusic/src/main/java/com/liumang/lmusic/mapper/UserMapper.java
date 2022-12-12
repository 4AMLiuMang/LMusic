package com.liumang.lmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liumang.lmusic.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
