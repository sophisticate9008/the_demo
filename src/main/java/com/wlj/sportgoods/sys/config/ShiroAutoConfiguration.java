package com.wlj.sportgoods.sys.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.SessionListenerAdapter;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.wlj.sportgoods.sys.common.Constast;
import com.wlj.sportgoods.sys.common.WebSocketManager;
import com.wlj.sportgoods.sys.common.WebUtils;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.realm.UserRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(value = { SecurityManager.class })
@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroAutoConfiguration {

    private static final String SHIRO_DIALECT = "shiroDialect";
    private static final String SHIRO_FILTER = "shiroFilter";
    // 加密方式
    private String hashAlgorithmName = "md5";
    // 散列次数
    private int hashIterations = Constast.HASHITERATIONS;
    // 默认的登陆页面
    private String loginUrl = "/index.html";

    private String[] anonUrls;
    private String logOutUrl;
    private String[] authcUrls;

    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
        
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置会话超时时间，单位毫秒
        sessionManager.setGlobalSessionTimeout(18000000); // 30分钟
        // 是否删除过期的会话
        sessionManager.setDeleteInvalidSessions(true);
        // 设置定时检查失效的会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 配置会话监听器
        Collection<SessionListener> sessionListeners = new ArrayList<>();
        sessionListeners.add(new MySessionListener()); // 自定义会话监听器
        sessionManager.setSessionListeners(sessionListeners);

        return sessionManager;
    }

    public class MySessionListener extends SessionListenerAdapter {
        private final Logger logger = LoggerFactory.getLogger(MySessionListener.class);
        @Override
        public void onStop(org.apache.shiro.session.Session session) {
            // 在会话停止时执行注销后台内容的操作，可以根据需要调用相应的服务或方法
            User user = (User) session.getAttribute("user");
            logger.info(user.getAccount() + " > 会话结束执行操作....");
            WebSocketManager webSocketManager = new WebSocketManager();
            webSocketManager.removeSel(user.getAccount());
            webSocketManager.removeuuid(user.getAccount());
            // 调用后台注销方法，例如清除缓存、关闭连接等
        }
    }

    /**
     * 声明凭证匹配器
     */
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        credentialsMatcher.setHashIterations(hashIterations);
        return credentialsMatcher;
    }

    /**
     * 声明userRealm
     */
    @Bean("userRealm")
    public UserRealm userRealm(CredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        // 注入凭证匹配器
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    /**
     * 配置SecurityManager
     */
    @Bean("securityManager")
    public SecurityManager securityManager(UserRealm userRealm, DefaultWebSessionManager sessionManager) {
        
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入userRealm
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager); // 设置自定义的会话管理器
        return securityManager;
    }

    /**
     * 配置shiro的过滤器
     */
    @Bean(SHIRO_FILTER)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        factoryBean.setSecurityManager(securityManager);
        // 设置未登陆的时要跳转的页面
        factoryBean.setLoginUrl(loginUrl);
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        // 设置放行的路径
        if (anonUrls != null && anonUrls.length > 0) {
            for (String anon : anonUrls) {
                filterChainDefinitionMap.put(anon, "anon");
            }
        }
        // 设置登出的路径
        if (null != logOutUrl) {
            filterChainDefinitionMap.put(logOutUrl, "logout");
        }
        // 设置拦截的路径
        if (authcUrls != null && authcUrls.length > 0) {
            for (String authc : authcUrls) {
                filterChainDefinitionMap.put(authc, "authc");
            }
        }
        Map<String, Filter> filters = new HashMap<>();
        // filters.put("authc", new ShiroLoginFilter());
        // 配置过滤器
        factoryBean.setFilters(filters);
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    /**
     * 注册shiro的委托过滤器，相当于之前在web.xml里面配置的
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName(SHIRO_FILTER);
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /* 加入注解的使用，不加入这个注解不生效--开始 */
    /**
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /* 加入注解的使用，不加入这个注解不生效--结束 */

    /**
     * 这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
     *
     * @return
     */
    @Bean(name = SHIRO_DIALECT)
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
