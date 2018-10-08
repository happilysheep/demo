package com.example.demo.config;

import com.example.demo.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangzhijun
 * @create 2018-09-21 17:41
 * @description
 */

public class MyRelm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String primaryPrincipal = (String)principals.getPrimaryPrincipal();
        List<String> roles = userService.getRolesByUserName(primaryPrincipal);
        List<String> permissions = userService.getPermissionByUserName(roles);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        String username = token1.getUsername();
        if(StringUtils.isNotBlank(username)){
            String password= userService.getPasswordByUserName(username);
            if (password != null) {
                return new SimpleAuthenticationInfo(username, password, getName());
            }
        }
        return null;
    }
}
