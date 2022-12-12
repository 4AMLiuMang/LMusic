package com.liumang.lmusic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liumang.lmusic.entity.Song;
import com.liumang.lmusic.mapper.SongMapper;
import com.liumang.lmusic.service.SongService;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
}
