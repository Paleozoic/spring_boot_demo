package com.maxplus1.demo.config.shiro.chain.remote;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * shiro权限过滤链动态更新接口-抽象的实现
 */
public abstract class AbstractFilterChainDefinitionsService implements IFilterChainDefinitionsService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractFilterChainDefinitionsService.class);

    /**
     * XML文件中传入的权限配置
     */
    private String definitions = "";

    /**
     * shiro过滤器工场类
     */
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    /**
     * shiro过滤器
     */
    private AbstractShiroFilter shiroFilter;

    /**
     * 系统启动时调用，加载所有的权限配置信息到shiro中
     */
    @PostConstruct
    @Override
    public void initPermission() {

        shiroFilter = null;

        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // 获取过滤管理器
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

        shiroFilterFactoryBean.setFilterChainDefinitionMap(obtainPermissionMap());

        Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();

        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim()
                    .replace(" ", "");
            manager.createChain(url, chainDefinition);
        }
        logger.debug("initialize shiro permission success...");
    }

    /**
     * 系统运行中的时候，调用此方法，动态更新shiro系统权限过滤器。
     */
    @Override
    public void updatePermission() {

        synchronized (shiroFilterFactoryBean) {

            shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空初始权限配置
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

            // 重新构建生成
            shiroFilterFactoryBean.setFilterChainDefinitionMap(obtainPermission());
            // shiroFilterFactoryBean.setFilterChainDefinitions(definitions);
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();

            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            logger.debug("update shiro permission success...");
        }
    }

    // TODO Spring Boot版本去除了Shiro的XML配置，所以此类废用
    /** 读取配置资源 */
    @Deprecated
    private Section obtainPermission() {
        // 加载XMl中的权限配置
        Ini ini = new Ini();
        ini.load(getDefinitions()); // 加载资源文件节点串
        Section section = ini.getSection(IniFilterChainResolverFactory.URLS); // 使用默认节点
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME); // 如不存在默认节点切割,则使用空字符转换
        }
        // 加载第三方的权限配置
        Map<String, String> permissionMap = initOtherPermission();
        if (permissionMap != null && !permissionMap.isEmpty()) {
            section.putAll(permissionMap);
        }
        // 最后把未配置地址都统一加上需要登陆才可以查看的权限
        section.put("/**", "authc");
        return section;
    }

    private LinkedHashMap<String, String> obtainPermissionMap() {

        Map<String, String> filterChains = getFilterChains();
        // 加载第三方的权限配置
        LinkedHashMap<String, String> permissionMap = initOtherPermission();
        permissionMap.putAll(filterChains);

        // 最后把未配置地址都统一加上需要登陆才可以查看的权限
        permissionMap.remove("/**");
        permissionMap.put("/**", "authc");

        return permissionMap;
    }

    /**
     *
     * 得到XML中注入的权限定义
     *
     * @return
     * @see
     */
    public String getDefinitions() {
        return definitions;
    }

    /**
     *
     * XML注入权限定义时的方法
     *
     * @param definitions
     * @see
     */
    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    /**
     *
     * shiro过滤器工厂注入时使用的方法
     *
     * @param shiroFilterFactoryBean
     * @see
     */
    public void setShiroFilterFactoryBean(
            ShiroFilterFactoryBean shiroFilterFactoryBean) {
        this.shiroFilterFactoryBean = shiroFilterFactoryBean;
    }

    /**
     * 获取当前权限map
     * @return
     */
    public Map<String, String> getFilterChains(){
        return shiroFilterFactoryBean.getFilterChainDefinitionMap();
    }
}
