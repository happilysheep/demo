package com.example.demo;


import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author wangzhijun
 * @create 2018-09-20 14:25
 * @description
 */

public class ShiroTest {
    private static final transient Logger log =LoggerFactory.getLogger(ShiroTest.class);
    @Test
    public void testShiro(){
        //testRoleByIni("zhang","23456");
        //testRoleAndPermiteByIniTest("zhang","123456");
        //testRoleAndPermiteByIniSql("admin","e3eb23cb815846372f40f908eaddbceb","shiro-mysql.ini");
        testRoleAndPermiteByIniSql("wang","123456","shiro-pgsql.ini");
    }

    public void  testRoleByIni(String name,String password){
        //1. 这里的SecurityManager是org.apache.shiro.mgt.SecurityManager
        // 而不是java.lang.SecurityManager
        // 加载配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.解析配置文件，并且返回一些SecurityManger实例
        SecurityManager instance = factory.getInstance();
        //3.将SecurityManager绑定给SecurityUtils
        SecurityUtils.setSecurityManager(instance);
        // 安全操作，Subject是当前登录的用户
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try{
            subject.login(token);
            Assert.assertEquals(true,subject.isAuthenticated());
            subject.logout();
        }catch (UnknownAccountException uae) {
            log.info(token.getPrincipal() + "账户不存在");
        } catch (IncorrectCredentialsException ice) {
            log.info(token.getPrincipal() + "密码不正确");
        } catch (LockedAccountException lae) {
            log.info(token.getPrincipal() + "用户被锁定了 ");
        } catch (AuthenticationException ae) {
            //无法判断是什么错了
            log.info(ae.getMessage());
        }
    }
    public void testRoleAndPermiteByIniTest(String name,String password){
        //1. 这里的SecurityManager是org.apache.shiro.mgt.SecurityManager
        // 而不是java.lang.SecurityManager
        // 加载配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.解析配置文件，并且返回一些SecurityManger实例
        SecurityManager instance = factory.getInstance();
        //3.将SecurityManager绑定给SecurityUtils
        SecurityUtils.setSecurityManager(instance);
        // 安全操作，Subject是当前登录的用户
        Subject currentUser  = SecurityUtils.getSubject();
        // 测试在应用的当前会话中设置属性
        Session session = currentUser.getSession();
        //放进去一个key和一个value
        session.setAttribute("cookId","aValue");
        //根据key拿到value
        String value = (String)session.getAttribute("cookId");
        if(value.equals("aValue")){//比较拿到的值和原来的值是否一致
            log.info("检测到session的值为："+value);
        }
        //尝试进行登录用户，如果登录失败了，我们进行一些处理
        if (!currentUser.isAuthenticated()) {//如果用户没有登录过
            log.info("你未登录请登录");
            UsernamePasswordToken token = new UsernamePasswordToken(name, password);
            token.setRememberMe(true);//是否记住用户
            try{
                currentUser.login(token);
                //当我们获登录用户之后
                log.info("用户 [" + currentUser.getPrincipal() + "] 登陆成功");
                currentUser.checkRole("admin");
                // 查看用户是否有指定的角色
                if(currentUser.hasRole("guess")){
                    log.info("用户包含角色:guess");
                }else{
                    log.info("用户不包含角色:guess");
                }
                if(currentUser.hasRole("admin")){
                    log.info("用户包含角色:admin");
                }else{
                    log.info("用户不包含角色:admin");
                }
                // 查看用户是否有某个权限
                if(currentUser.isPermitted("au3")){
                    log.info("用户包含权限:au3");
                }else{
                    log.info("用户不包含权限:au3");
                }
                if(currentUser.isPermitted("au1")){
                    log.info("用户包含权限:au1");
                }else{
                    log.info("用户不包含权限:au1");
                }
                currentUser.checkPermission("au2");
                currentUser.logout();
            }catch (UnknownAccountException uae) {
                log.info(token.getPrincipal() + "账户不存在");
            } catch (IncorrectCredentialsException ice) {
                log.info(token.getPrincipal() + "密码不正确");
            } catch (LockedAccountException lae) {
                log.info(token.getPrincipal() + "用户被锁定了 ");
            } catch (AuthenticationException ae) {
                //无法判断是什么错了
                log.info(ae.getMessage());
            }
        }
    }

    public void testRoleAndPermiteByIniSql(String name,String password,String resource){
        //1. 这里的SecurityManager是org.apache.shiro.mgt.SecurityManager
        // 而不是java.lang.SecurityManager
        // 加载配置文件
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:"+resource);
        //2.解析配置文件，并且返回一些SecurityManger实例
        SecurityManager securityManager = securityManagerFactory.getInstance();
        //3.将SecurityManager绑定给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        // 安全操作，Subject是当前登录的用户
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try{
            token.setRememberMe(true);
            currentUser.login(token);
            Assert.assertEquals(true,currentUser.isAuthenticated());
            //currentUser.checkRole("admin");
            if(currentUser.hasRole("系统管理员")){
                log.info("用户包含角色:系统管理员");
            }else{
                log.info("用户不包含角色:系统管理员");
            }
            if(currentUser.hasRole("admin")){
                log.info("用户包含角色:admin");
            }else{
                log.info("用户不包含角色:admin");
            }
            if(currentUser.isPermitted("au3")){
                log.info("用户包含权限:au3");
            }else{
                log.info("用户不包含权限:au3");
            }
            if(currentUser.isPermitted("finance.refund.info")){
                log.info("用户包含权限:finance.refund.info");
            }else{
                log.info("用户不包含权限:finance.refund.info");
            }
            currentUser.checkPermission("merchant.mark.add");
            currentUser.checkPermission("merchant.mark.addd");
            currentUser.logout();
        }catch (UnknownAccountException uae) {
            log.info(token.getPrincipal() + "账户不存在");
        } catch (IncorrectCredentialsException ice) {
            log.info(token.getPrincipal() + "密码不正确");
        } catch (LockedAccountException lae) {
            log.info(token.getPrincipal() + "用户被锁定了 ");
        } catch (AuthenticationException ae) {
            //无法判断是什么错了
            log.info(ae.getMessage());
        }

    }
    @Test
    public void testpqsql(){
        String driver="org.postgresql.Driver";
        String url="jdbc:postgresql://127.0.0.1:5432/test";
        String user="postgres";
        String pass="123456";
        String sql="select * from sys_user";
        Connection connection =null;
        Statement statement =null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
            connection.setHoldability(2);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            log.info(JSON.toJSONString(resultSet));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
