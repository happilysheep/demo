package com.example.demo.domain;

/**
 * @author wangzhijun
 * @create 2018-09-25 10:07
 * @description
 */

public class User {
    private Integer id;
    private String name;
    private String pass;

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
