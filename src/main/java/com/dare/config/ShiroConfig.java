package com.dare.config;

import com.dare.modules.shiro.authc.ShiroRealm;
import com.dare.modules.shiro.authc.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: shengyao
 * @Package: com.dare.config
 * @Date: 2020/11/9 15:22
 * @Description: shiro配置
 * @version: 1.0
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String redisPassword;


    /**
     * @param securityManager
     * @Author: shengyao
     * @Description: TODO
     * @Date: 2020/11/10 15:25
     * @Return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/sys/login", "anon");
        filterChainDefinitionMap.put("/sys/**", "anon");
        filterChainDefinitionMap.put("/sys/register", "anon");
        filterChainDefinitionMap.put("/sys/file/**", "anon");
        filterChainDefinitionMap.put("/hrm/**", "anon");
        filterChainDefinitionMap.put("/sign/**", "anon");
        filterChainDefinitionMap.put("/apqp/**", "anon");
        filterChainDefinitionMap.put("/project/**", "anon");
        filterChainDefinitionMap.put("/test/json", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
//        filterChainDefinitionMap.put("/test/list", "anon");

        // swagger
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

//        filterChainDefinitionMap.put("/hrm/employee/list", "authc");  // authc请求这个资源需要认证和授权

        // 添加自己的过滤器jwt
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


    /**
     * @param myRealm
     * @Author: shengyao
     * @Description: TODO
     * @Date: 2020/11/10 15:44
     * @Return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(ShiroRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //自定义缓存实现,使用redis
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;

    }


    /**
     * @param
     * @Author: shengyao
     * @Description: 添加注解支持
     * @Date: 2020/11/10 15:51
     * @Return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        /**
         * 解决重复代理问题 github#994
         * 添加前缀判断 不匹配 任何Advisor
         */
//        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
//        defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix("_no_advisor");
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * @param
     * @Author: shengyao
     * @Description: cacheManager 缓存 redis实现
     * @Date: 2020/11/10 15:52
     * @Return org.crazycake.shiro.RedisCacheManager
     */
    public RedisCacheManager redisCacheManager() {
        System.out.println("===============(1)创建缓存管理器RedisCacheManager");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }


    /**
     * @Author: shengyao
     * @Description: 配置shiro redisManager
     * @Date: 2020/11/10 15:54
     * @param
     * @Return org.crazycake.shiro.RedisManager
     */
    @Bean
    public RedisManager redisManager() {
        System.out.println("===============(2)创建RedisManager,连接Redis..URL= " + host + ":" + port);
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host + ":" + port);
//        redisManager.setPort(ConvertUtil.getInt(port));
        redisManager.setTimeout(0);
        if (!StringUtils.isEmpty(redisPassword)) {
            redisManager.setPassword(redisPassword);
        }
        return redisManager;
    }
}
