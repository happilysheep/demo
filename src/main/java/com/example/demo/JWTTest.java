package com.example.demo;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.demo.domain.User;
import com.example.demo.util.JWTUtil;
import org.junit.Test;

/**
 * @author wangzhijun
 * @create 2018-09-29 16:04
 * @description
 */

public class JWTTest {
    @Test
    public void test(){
        User user = new User();
        user.setId(111);
        user.setPass("aaaaa");
        user.setName("WANG");
        String sign = JWTUtil.sign(user/*, 100000*/);
        System.out.println(sign);
    }

    @Test
    public void test2(){
        String str="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiMTExIiwiV0FORyJdfQ.QymsSOXUFInp-NeaycBQDl5o0ww_98RTgCwLMj8cTU";
        Object o = JWTUtil.unSign(str);
        System.out.println(JSONUtils.toJSONString(o));
    }
}
