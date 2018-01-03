package cn.edu.jxnu.blog;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.edu.jxnu.blog.filter.IPFilter;

/**
 * @description SpringBoot额外配置类
 * @author liguobin
 * 
 */
@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	/**
	 * 域名直接访问主页
	 */
	public static String LOGIN_USER = "loginUser";

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index/home");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

	/**
	 * 静态资源直接通过
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		// 使得静态js可以被浏览器直接访问
		// 此处主要用于修复百度分享无法找到js文件的bug
		registry.addResourceHandler("/static/").addResourceLocations("/**");
	}

	/**
	 * 添加tomcat支持https 将http强制重定向到https
	 * 
	 * @return
	 */

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint serConstraint = new SecurityConstraint();
				serConstraint.setUserConstraint("CONFINDENTIAL");
				SecurityCollection securityCollection = new SecurityCollection();
				securityCollection.addPattern("/*");
				serConstraint.addCollection(securityCollection);
				context.addConstraint(serConstraint);

			}
		};// 服务器上部署必须添加下面的
		tomcat.addAdditionalTomcatConnectors(httpConnector());
		tomcat.setBaseDirectory(new File("C:/web/")); // 虚拟路径映射// 访问图片路径：C:\web-->localhost
		// tomcat.setContextPath("/");//访问的url
		return tomcat;
	}
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //文件最大  
        factory.setMaxFileSize("5MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("5MB");  
        return factory.createMultipartConfig();  
    }  

	@Bean
	public Connector httpConnector() {
		Connector connector = new Connector(
				"org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(80);
		connector.setSecure(false);
		connector.setRedirectPort(443);
		return connector;
	}

	@Bean
	public IPFilter ipFilter() {
		return new IPFilter();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(ipFilter());
	}

	/**
	 * 2、500 错误 可以直接返回static下的一个静态页面
	 * 
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(
			final ConfigurableEmbeddedServletContainer Contain) {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage error500Page = new ErrorPage(
						HttpStatus.INTERNAL_SERVER_ERROR, "/500.html"); // 服务器错误
				Contain.addErrorPages(error500Page);
			}
		};
	}

}
