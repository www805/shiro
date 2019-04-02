package com.zhuang.shiro;

import com.zhuang.domain.User;
import com.zhuang.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        //从数据库中读取角色权限
        User dbUser = userService.findById(user.getId());

        //添加资源的授权字符串
        info.addStringPermission(dbUser.getPerms());

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");


        //假设数据库
        String name = "aa";
        String pwd = "123";

        //获取token对象
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //获取用户密码
        User user = userService.findByName(token.getUsername());

        if(null == user){
            //用户名不存在
            return null;
        }

        return new SimpleAuthenticationInfo(user, user.getAddress(), getName());
    }
}
