package com.liumang.lmusic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liumang.lmusic.entity.Employee;
import com.liumang.lmusic.mapper.EmployeeMapper;
import com.liumang.lmusic.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
