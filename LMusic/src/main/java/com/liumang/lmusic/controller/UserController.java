package com.liumang.lmusic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liumang.lmusic.common.R;
import com.liumang.lmusic.entity.User;
import com.liumang.lmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户信息分页查询
     * @param page
     * @param pageSize
     * @param username
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String username){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //添加搜索条件//模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(username),User::getUsername,username);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(User::getUpdateTime);
        //执行查询
        userService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public R<String> save(@RequestBody User user){//@RequestBody将前端的json数据封装
        //将密码进行md5加密处理
        String password = user.getPassword();
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        //保存没有的内容
        user.setName("新用户");
        user.setPhone("18888888888");
        user.setSex("1");
        user.setAvatar("https://tse4-mm.cn.bing.net/th/id/OIP-C.V3_tPp-dPnOUKlCQLweoTQHaH-?pid=ImgDet&rs=1");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User emp = userService.getOne(queryWrapper);
        //3.查询不到则执行新增
        if(emp == null){
            userService.save(user);
            return R.success("用户注册成功");
        }
        return R.error("此账号已经存在");
    }

    /**
     * 用户登录
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user) {
        //1.将页面提交的密码进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User emp = userService.getOne(queryWrapper);//执行登录。。。getOne,数据库设置唯一，就可以通过这个方法
        //3.查询不到则返回失败
        if(emp == null){
            return R.error("没有此用户");
        }
        //4.密码不一致返回失败
        if (!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
        //5.被禁用无法登录
        Integer status = emp.getStatus();
        if(status != 1){
            return R.error("该账户已被禁用 请联系工作人员");
        }
        //6.登录成功，将id存入session并返回登录结果
        request.getSession().setAttribute("user",emp.getId());
        return R.success(emp);
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的员工的id
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody User user){//@RequestBody将前端的json数据封装HttpServletRequest request取当前登录用户
        //执行更新
        userService.updateById(user);
        return R.success("用户信息修改成功");
    }

    /**
     * 根据id查询用户信息
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id){//@PathVariable取请求路径里面的id
        //执行查询操作
        User user = userService.getById(id);
        if(user != null){
            return R.success(user);
        }
        return R.error("没有查询到对应用户信息");
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
            User user = new User();
            user.setId(Long.parseLong(id));
            user.setStatus(status);
            userService.updateById(user);
        }
        return R.success("更新状态成功");
    }

    /**
     * 根据id批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping
    public R delete(String ids){
        String[] list = ids.split(",");
        for (String id : list) {
            userService.removeById(Long.parseLong(id));
        }
        return R.success("删除成功");
    }
}
