package com.liumang.lmusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liumang.lmusic.entity.Song;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SongMapper extends BaseMapper<Song> {
}
