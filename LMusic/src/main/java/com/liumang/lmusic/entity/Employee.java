package com.liumang.lmusic.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    //名字
    private String name;

    //账号
    private String username;

    //密码
    private String password;

    //手机
    private String phone;

    //性别 0 女 1 男
    private String sex;

    //身份证
    private String idNumber;

    //状态 0:禁用，1:正常
    private Integer status;

    //插入时填充字段
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
