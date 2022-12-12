package com.liumang.lmusic.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Song  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    //歌曲名称
    private String songname;

    //歌手名字
    private String singername;

    //歌曲类型
    private String songtype;

    //歌曲语言
    private String songlanguage;

    //歌曲封面地址
    private String img;

    //歌曲外链接地址
    private String musicurl;

    //插入时填充字段
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //插入时填充字段
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
