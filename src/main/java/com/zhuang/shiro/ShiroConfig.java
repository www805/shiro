package com.zhuang.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
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

        // 添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *  常用的过滤器：
         *      anon: 无需认证（登录）可以访问
         *      authc: 必须认证才可以访问
         *      user: 如果使用rememberMe功能可以直接访问
         *      perms: 该资源必须得到资源权限才可以访问
         *      role: 该资源必须得到角色权限才可以访问
         */
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/index", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/logout", "anon");

        //授权过滤器
        //注意：的那个授权拦截后，shiro会自动跳转到未授权页面
        filterMap.put("/add", "perms[user:add]");
        filterMap.put("/update", "perms[user:update]");

        filterMap.put("/logout", "logout");
        filterMap.put("/*", "authc");

        //修改调整的登录页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //设置未授权的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");


        return shiroFilterFactoryBean;
    }

    /**
     * shiro安全管理
     * @param userRealm
     * @return
     */
    @Bean(value = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("UserRealm")UserRealm userRealm, @Qualifier("ehCacheManager")EhCacheManager ehCacheManager){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(ehCacheManager);
        return securityManager;
    }

    /**
     * 设置缓存
     * @return
     */
    @Bean(value = "ehCacheManager")
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * 自定义认证
     * @return
     */
    @Bean(value = "UserRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
