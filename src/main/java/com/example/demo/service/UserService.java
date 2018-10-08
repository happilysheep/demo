package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangzhijun
 * @create 2018-09-25 10:11
 * @description
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    /**
     * 根据用户名查询密码
     */
    public String getPasswordByUserName(String userName){
        return userRepository.getPasswordByUserName(userName);
    }

    public List<String> getRolesByUserName(String username){
        return userRepository.getRolesByUserName(username);
    }

    public List<String> getPermissionByUserName(List<String> roles){
        return userRepository.getPermissionByUserName(roles);
    }
}
