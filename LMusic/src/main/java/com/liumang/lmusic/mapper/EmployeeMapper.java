package com.liumang.lmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liumang.lmusic.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
