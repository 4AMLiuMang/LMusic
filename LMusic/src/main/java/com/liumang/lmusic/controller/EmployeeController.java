package com.liumang.lmusic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liumang.lmusic.common.R;
import com.liumang.lmusic.entity.Employee;
import com.liumang.lmusic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 员工登录
     * @RequestBody用于封装成json对象
     * HttpServletRequest登录成功后存入session一份，表示登录成功
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1.将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);//执行登录。。。getOne,数据库设置唯一，就可以通过这个方法
        //3.查询不到则无法登录
        if(emp == null){
            return R.error("无权限");
        }
        //4.密码不一致无法登录
        if (!emp.getPassword().equals(password)){
            return R.error("无权限");
        }
        //5.被禁用无法登录
        Integer status = emp.getStatus();
        if(status != 1){
            return R.error("无权限");
        }
        //6.登录成功，将id存入session并返回登录结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }


    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }


    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee){//@RequestBody将前端的json数据封装
        //设置初始密码123456，并且进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //保存当前更新和创建的时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //查询不到则执行新增
        if(emp == null){
            employeeService.save(employee);
            return R.success("新增员工成功");
        }
        return R.error("此员工号已经存在");
    }


    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加搜索条件//模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee){//@RequestBody将前端的json数据封装HttpServletRequest request取当前登录用户
        //执行更新
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){//@PathVariable取请求路径里面的id
        //执行查询操作
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }

    /**
     * 更改用户禁用状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R changeStatus(@PathVariable int status,String ids){
        String[] idList = ids.split(",");
        for (String id : idList) {
            Employee employee = new Employee();
            employee.setId(Long.parseLong(id));
            employee.setStatus(status);
            employeeService.updateById(employee);
        }
        return R.success("更新状态成功");
    }

    /**
     * 根据id批量删除员工
     * @param ids
     * @return
     */
    @DeleteMapping
    public R delete(String ids){
        String[] list = ids.split(",");
        for (String id : list) {
            employeeService.removeById(Long.parseLong(id));
        }
        return R.success("删除成功");
    }
}
