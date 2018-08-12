package com.maxplus1.demo.config.shiro;

import com.maxplus1.demo.config.shiro.realm.LoginRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * Created by qxloo on 2017/9/4.
 */
@Configuration
public class ShiroConfig {

    @Value("${mock.test}")
    private Boolean TEST = false;

    private LoginRealm loginRealm;

    //拦截器.
    public static final LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

    static {
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        //swagger
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs/**", "anon");



        filterChainDefinitionMap.put("/api/login/captchaImage", "anon");
        filterChainDefinitionMap.put("/api/login/login", "anon");
        filterChainDefinitionMap.put("/api/login/testLogin", "anon");
        //验证码路径
        filterChainDefinitionMap.put("/core/start_captcha", "anon");
        //OA接口
        filterChainDefinitionMap.put("/api/OA/genToken", "anon");
        filterChainDefinitionMap.put("/api/OA/callBack", "anon");
        //ACP base url
        filterChainDefinitionMap.put("/api/ficc/access/getACPBaseUrl", "anon");

        //单点登录
        filterChainDefinitionMap.put("/api/ssoLogin", "anon");

        //web service
        filterChainDefinitionMap.put("/api/**", "perms");
        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");


        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/api/login/logout", "logout");

    }

    @Bean
    public LoginRealm buildLoginRealm() {
        loginRealm.setAuthenticationCachingEnabled(false);
        return loginRealm;
    }

    @Bean
    public DefaultWebSecurityManager buildDefaultWebSecurityManager(@Autowired LoginRealm loginRealm, @Autowired DefaultWebSessionManager defaultWebSessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(loginRealm);
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);
        return defaultWebSecurityManager;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(@Autowired DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
//        shiroFilterFactoryBean.setLoginUrl("/core/login_view");
        shiroFilterFactoryBean.setLoginUrl("/api/login/login");
        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/core/unauthorized");
        if (TEST) {
            filterChainDefinitionMap.put("/api/**", "anon");
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置filters
        /*Map<String,Filter> filterMap = new HashMap<>();
        Filter filter = new AccessControlFilter() {
            @Override
            protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
                //获取url

                //获取menu


                //获取登录用户有访问权限的url

                //返回匹配结果

                return false;
            }

            @Override
            protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
                return false;
            }
        };
        filterMap.put("filter",filter);
        shiroFilterFactoryBean.setFilters(filterMap);*/
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSessionManager buildDefaultWebSessionManager(@Value("${shiro.globalSessionTimeout}") long globalSessionTimeout,
                                                                  @Value("${shiro.sessionValidationInterval}") long sessionValidationInterval,
                                                                  @Value("${shiro.sessionValidationSchedulerEnabled}") boolean sessionValidationSchedulerEnabled) {
        RestSessionManager defaultWebSessionManager = new RestSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(globalSessionTimeout);
        defaultWebSessionManager.setSessionValidationInterval(sessionValidationInterval);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(sessionValidationSchedulerEnabled);
        return defaultWebSessionManager;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Autowired DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}

