package com.example.demo.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wangzhijun
 * @create 2018-09-28 18:22
 * @description
 */
@Component
public class Tasker {
    @Scheduled(cron = "* *  * * * * ")
    public void pri(){
        System.out.println(new Date());
    }
}
