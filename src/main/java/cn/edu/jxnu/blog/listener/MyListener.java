package cn.edu.jxnu.blog.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @Description 自定义监听器，项目启动时初始化两个全局的map， ipMap(ip存储器，记录IP的访问次数、访问时间)
 *              limitedIpMap(限制IP存储器)用来存储每个访问用户的IP以及访问的次数
 * @author liguobin
 */
@Component
public class MyListener implements ServletContextListener {


	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(MyListener.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		// IP存储器
		Map<String, Long[]> ipMap = new HashMap<String, Long[]>();
		context.setAttribute("ipMap", ipMap);
		// 限制IP存储器：存储被限制的IP信息
		Map<String, Long> limitedIpMap = new HashMap<String, Long>();
		context.setAttribute("limitedIpMap", limitedIpMap);

		Map<String, Map<Integer, Long>> userMap = new HashMap<String, Map<Integer, Long>>();
		context.setAttribute("userMap", userMap);
		log.info("完成初始化ip拦截和浏览量拦截所需要的map");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}