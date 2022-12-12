package com.liumang.lmusic.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    //姓名
    private String name;

    //账号
    private String username;

    //密码
    private String password;

    //手机号
    private String phone;

    //性别 0 女 1 男
    private String sex;

    //头像
    private String avatar;

    //状态 0:禁用，1:正常
    private Integer status;

    //插入时填充字段
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
