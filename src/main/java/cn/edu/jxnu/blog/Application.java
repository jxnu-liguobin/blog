package cn.edu.jxnu.blog;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.jxnu.blog.commons.PVFinalCount;
import cn.edu.jxnu.blog.domin.Blogger;

/**
 * 
 * @author： liguobin
 * @Description：
 * @时间： 2017-12-5 上午8:21:59
 * @version： V1.0
 * 
 */
@Controller
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.edu.jxnu.blog.dao")
@ServletComponentScan
public class Application {
	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(Application.class);
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		Thread thread = new Thread(new ThreadForPushTo());
		thread.start();
	}

	// 处理登陆页面
	@RequestMapping("/login.html")
	public String index() {
		log.info("登录页面------>/login");
		return "index";
	}

	// // 测试页面
	// @RequestMapping("/home")
	// public String home() {
	// return "indexViews/home";
	// }

	// 主页直接访问不可以
	@RequestMapping("/main")
	public String main() {
		Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		if (blogger == null) {
			log.info("无权限------->403.html");
			return "errors/403";
		} else {
			log.info("正常登录----->main.html");
			return "main";
		}
	}

	// 登录页面直接拒绝必须经过login
	@RequestMapping("/index")
	public String indexDir() {
		log.info("直接访问index.html被拒绝------->403.html");
		return "errors/403";
	}

	/**
	 * 3、使用自己的判断来实现跳转 UI中任何页面跳转都要通过这个Controller---------主要是左侧导航栏菜单点击
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping("/views/{path}")
	public String index11(@PathVariable String path) {
		Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		if (blogger == null) {
			log.info("直接访问/views/*.html被拒绝------>403.html");
			return "errors/403";
		} else {
			log.info("正常访问/views/*.html");
			return "views/" + path;
		}
	}

	// UI中任何页面跳转都要通过这个Controller---------主要是左侧导航栏菜单点击
	/**
	 * @param path
	 * @return
	 */
	@RequestMapping("/indexViews/{path}")
	public String indexViews(@PathVariable String path,
			HttpServletRequest request) {
		PVFinalCount.Count.incrementAndGet();
		log.info("正常访问/indexViews/*.html");
		return "indexViews/" + path;
	}
}
