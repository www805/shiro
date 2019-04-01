package com.zhuang.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/index", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/*", "authc");

        //修改调整的登录页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");

        return shiroFilterFactoryBean;
    }

    @Bean(value = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("UserRealm")UserRealm userRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(value = "UserRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }


}
