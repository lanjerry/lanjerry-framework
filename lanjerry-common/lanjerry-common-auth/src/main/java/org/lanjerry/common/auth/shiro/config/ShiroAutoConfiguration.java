package org.lanjerry.common.auth.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.lanjerry.common.auth.shiro.jwt.JwtAuthFilter;
import org.lanjerry.common.auth.shiro.jwt.JwtRealm;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * shiro配置
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-09
 */
public class ShiroAutoConfiguration {

    /**
     * 设置过滤器，将自定义的Filter加入
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 自定义过滤器
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", new JwtAuthFilter());
        factoryBean.setFilters(filterMap);

        // 拦截配置
        Map<String, String> filterChainDefinitions = new LinkedHashMap<>();
        // swagger的拦截
        filterChainDefinitions.put("/swagger-resources/**", "anon");
        filterChainDefinitions.put("/v2/api-docs", "anon");
        filterChainDefinitions.put("/v2/api-docs-ext", "anon");
        filterChainDefinitions.put("/doc.html", "anon");
        filterChainDefinitions.put("/webjars/**", "anon");

        // 不需要验证的api
        filterChainDefinitions.put("/sys/user/login", "anon");
        filterChainDefinitions.put("/test/**/**", "anon");//测试
        filterChainDefinitions.put("/upload/**/**", "anon");//上传

        // 其他全部需要鉴权
        filterChainDefinitions.put("/**", "authcToken"); // 默认进行用户鉴权
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        return factoryBean;
    }

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setRealm(realm);
        // 禁用session
        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO())
                .getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        securityManager.setSubjectFactory(new AgileSubjectFactory());
        return securityManager;
    }

    /**
     * 用于JWT token认证的realm
     */
    @Bean
    public Realm jwtRealm() {
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(new CredentialsMatcher());
        return jwtRealm;
    }

    /**
     * 开启shiro的注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager);
        return advisor;
    }
}
