package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.Test1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = new SpringApplication(DemoApplication.class).run(args);
		Object test = run.getBean("test1");
		System.out.println("args = [" + JSON.toJSONString(test) + "]");
		//System.out.println("*******************ConfigurableApplicationContext run  = [" + JSON.toJSONString(run) + "]");
	}
}
