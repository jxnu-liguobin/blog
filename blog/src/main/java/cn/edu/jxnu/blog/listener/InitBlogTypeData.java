package cn.edu.jxnu.blog.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edu.jxnu.blog.domin.BlogType;
import cn.edu.jxnu.blog.service.BlogTypeService;

@Component
/**
 * @Description 监听程序初始化
 */
public class InitBlogTypeData implements ServletContextAttributeListener,
		ServletContextListener {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(InitBlogTypeData.class);
	private static ApplicationContext applicationContext;

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(application);
		BlogTypeService blogTypeService = applicationContext
				.getBean(BlogTypeService.class);
		List<BlogType> blogTypeList = blogTypeService.getBlogTypeData();
		application.setAttribute("blogTypeList", blogTypeList);
		log.info("ServletContextListener---（contextInitialized）...");
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent args) {
	}

	/**
	 * 后台更新分类数据的时候将清空ServletContext,此方法将监听，并重新添加blogTypeList
	 */
	@Override
	public void attributeRemoved(ServletContextAttributeEvent args) {

		// 先获取servlet上下文
		ServletContext application = args.getServletContext();
		// 获取spring web上下文
		applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(application);
		// System.out.println("上下文："+applicationContext);
		BlogTypeService blogTypeService = applicationContext
				.getBean(BlogTypeService.class);
		// System.out.println("blogTypeService="+blogTypeService);
		List<BlogType> blogTypeList = blogTypeService.getBlogTypeData();
		application.setAttribute("blogTypeList", blogTypeList);
		log.info("ServletContextAttributeListener---（attributeRemoved）...");
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent args) {
	}

}