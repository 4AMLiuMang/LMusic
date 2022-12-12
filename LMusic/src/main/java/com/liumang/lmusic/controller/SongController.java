package com.liumang.lmusic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liumang.lmusic.common.R;
import com.liumang.lmusic.entity.Song;
import com.liumang.lmusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * 歌曲信息分页查询&条件查询
     * @param page
     * @param pageSize
     * @param songname
     * @param songtype
     * @param songlanguage
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String songname, String songtype, String songlanguage){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Song> queryWrapper = new LambdaQueryWrapper<>();
        //添加搜索条件//模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(songname),Song::getSongname,songname);
        queryWrapper.like(StringUtils.isNotEmpty(songtype),Song::getSongtype,songtype);
        queryWrapper.like(StringUtils.isNotEmpty(songlanguage),Song::getSonglanguage,songlanguage);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Song::getUpdateTime);
        //执行查询
        songService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改歌曲信息
     * @param song
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Song song){//@RequestBody将前端的json数据封装HttpServletRequest request取当前登录用户
        String musicurl = song.getMusicurl();
        song.setMusicurl("https://music.163.com/song/media/outer/url?id="+musicurl+".mp3");
        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        //保存当前更新人
        song.setUpdateUser(empId);
        //执行更新
        songService.updateById(song);
        return R.success("歌曲信息修改成功");
    }

    /**
     * 根据id查询歌曲信息
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Song> getById(@PathVariable Long id){//@PathVariable取请求路径里面的id
        //执行查询操作
        Song song = songService.getById(id);
        if(song != null){
            return R.success(song);
        }
        return R.error("没有查询到对应歌曲信息");
    }

    /**
     * 新增歌曲
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Song song){//@RequestBody将前端的json数据封装
        String musicurl = song.getMusicurl();
        song.setMusicurl("https://music.163.com/song/media/outer/url?id="+musicurl+".mp3");
        //保存当前更新和创建的时间
        song.setCreateTime(LocalDateTime.now());
        song.setUpdateTime(LocalDateTime.now());
        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        //保存当前更新人和创建人
        song.setCreateUser(empId);
        song.setUpdateUser(empId);
        //执行新增
        songService.save(song);
        return R.success("新增歌曲成功");
    }

    /**
     * 根据id批量删除歌曲
     * @param ids
     * @return
     */
    @DeleteMapping
    public R delete(String ids){
        String[] list = ids.split(",");
        for (String id : list) {
            songService.removeById(Long.parseLong(id));
        }
        return R.success("删除成功");
    }
}
