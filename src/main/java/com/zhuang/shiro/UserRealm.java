package com.zhuang.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        //假设数据库
        String name = "aa";
        String pwd = "123";

        //判断用户名密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        if(!name.equals(token.getUsername())){
            //用户名不存在
            return null;
        }

        return new SimpleAuthenticationInfo("", pwd, "");
    }
}
