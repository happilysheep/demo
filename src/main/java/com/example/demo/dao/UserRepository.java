package com.example.demo.dao;

import com.example.demo.domain.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wangzhijun
 * @create 2018-09-25 10:10
 * @description
 */
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplate myJdbcTemplate;

    public String getPasswordByUserName(String userName){
        String sql="select pass from sys_user where name = ?";
        List<String> passs = jdbcTemplate.queryForList(sql, new Object[]{userName}, String.class);
        if(passs!=null&&passs.size()>0){
            return passs.get(0);
        }
        String sql1="select password from sys_user where username = ?";
        List<String> passs1 = myJdbcTemplate.queryForList(sql1, new Object[]{userName}, String.class);
        if(passs1!=null&&passs1.size()>0){
            return passs1.get(0);
        }
        return null;
    }

    public List<String> getRolesByUserName(String username) {
        //pgsql
        String sql="select t3.name from sys_user t1 left join sys_user_role t2 " +
                "on t1.id=t2.user_id left join sys_role t3 on t2.role_id=t3.id where t1.name=?";
        List<String> strs = jdbcTemplate.queryForList(sql, new Object[]{username},String.class);

        //mysql
        String sql2="select t3.name from sys_user t1 left join sys_user_role t2 on t1.id=t2.sys_user_id " +
                "left join sys_role t3 on t2.sys_role_id=t3.id where t1.username=?";
        List<String> strs2 = jdbcTemplate.queryForList(sql2, new Object[]{username},String.class);

        if(strs!=null&&strs.size()>0){
            return strs;
        }
        return strs2;
    }

    public List<String> getPermissionByUserName(List<String> roles) {
        //pgsql
        String sql="select t3.name from sys_role t1 left join sys_role_relm t2 " +
                "on t1.id=t2.role_id left join sys_relm t3 on t2.relm_id=t3.id where t1.name in (?)";
        List<String> strs1 = jdbcTemplate.queryForList(sql, new Object[]{StringUtils.join(roles,",")},String.class);
        //mysql
        String sql1="select t3.code from sys_role t1 left join sys_role_resource t2 on t1.id=t2.sys_role_id" +
                " left join sys_resource t3 on t2.sys_resource_id=t3.id where t1.name in(?)";
        List<String> strs3 = jdbcTemplate.queryForList(sql1, new Object[]{StringUtils.join(roles,",")},String.class);
        if(strs1!=null&&strs1.size()>0){
            return strs1;
        }
        return strs3;
    }
}
