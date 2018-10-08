package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wangzhijun
 * @create 2018-09-29 14:17
 * @description
 */
@Component
public class Test1 {
    @Value("${test.id}")
    private Integer id;

    @Value("${test.name}")
    private String name;

    @Value("${test.phone}")
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
