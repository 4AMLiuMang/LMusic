package com.liumang.lmusic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan//用于过滤器资源放行生效
@EnableTransactionManagement//开启事务处理的注解
public class LMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(LMusicApplication.class, args);

        //加入注解后输出日志
        log.info("项目启动成功");
    }


}
//输出日志
