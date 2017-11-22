package cn.edu.jxnu.blog.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*1.LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。主要是AuthorizingRealm类的子类，以及EhCacheManager类。  
 2.HashedCredentialsMatcher，这个类是为了对密码进行编码的，防止密码在数据库里明码保存，当然在登陆认证的生活，这个类也负责对form里输入的密码进行编码。  
 3.ShiroRealm，这是个自定义的认证类，继承自AuthorizingRealm，负责用户的认证和权限的处理，可以参考JdbcRealm的实现。  
 4.EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。  
 5.SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。  
 6.ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。  
 7.DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。  
 8.AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。*/
@Configuration
public class ShiroConfiguration {
	private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

	// 自定义权限登录器，注入一个自定义密码匹配器
	@Bean
	public MyRealm getShiroRealm() {
		MyRealm myRealm = new MyRealm();
		return myRealm;
	}

	/**
	 * 缓存管理器
	 * 
	 * @return
	 */
	@Bean(name = "shiroEhcacheManager")
	public EhCacheManager getEhCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return em;
	}

	// 将缓存对象注入到SecurityManager中
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		// 设置realm
		dwsm.setRealm(getShiroRealm());
		// 注入缓存管理器
		dwsm.setCacheManager(getEhCacheManager());
		return dwsm;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(getDefaultWebSecurityManager());
		return new AuthorizationAttributeSourceAdvisor();
	}

	/**
	 * 记住我过滤器
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(
			org.apache.shiro.mgt.SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shiroFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置SecuritManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器
		// 配置退出过滤器,其中的具体代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// 配置记住我或认证通过可以访问的地址

		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		shiroFilterFactoryBean.setLoginUrl("/index");
		shiroFilterFactoryBean.setSuccessUrl("/main");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");// 对这个过滤不起作用
		filterChainDefinitionMap.put("/login", "anon");// 可以直接访问
														// (404.html)
														// 因为shiro自己带的setUnauthorizedUrl不生效
		filterChainDefinitionMap.put("/views/*", "authc");// 指定认证才可访问子页面
		filterChainDefinitionMap.put("/index/*", "anon");// 
		filterChainDefinitionMap.put("/static/**", "anon");// 静态资源不需要拦截
		filterChainDefinitionMap.put("/errors/**", "anon");// 静态资源不需要拦截
		filterChainDefinitionMap.put("/admin/**", "anon");
		filterChainDefinitionMap.put("/baiduShare/**", "anon");
		filterChainDefinitionMap.put("/blog/validateCode", "anon");
		//filterChainDefinitionMap.put("/**", "anon");// 静态资源不需要拦截

		shiroFilterFactoryBean
				.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;

	}

}
