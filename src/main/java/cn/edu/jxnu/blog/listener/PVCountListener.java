package cn.edu.jxnu.blog.listener;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import cn.edu.jxnu.blog.commons.PVFinalCount;

/**
 * 
 * @author： liguobin
 * @Description： 负责读入，写入count
 * @时间： 2018-1-5 下午4:06:40
 * @version： V1.0
 * 
 */

@Component
public class PVCountListener implements ServletContextListener {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(PVCountListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 启动项目的时候读入count
		Long c = null;
		try {
			c = PVFinalCount.readFileString();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (c != null) {
			try {
				PVFinalCount.Count=new AtomicLong(c);
				log.info("当前读入文件count值：" + c);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			PVFinalCount.writeFileString(PVFinalCount.Count.get());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
