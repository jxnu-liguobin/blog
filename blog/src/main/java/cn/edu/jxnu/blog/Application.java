package cn.edu.jxnu.blog;

import org.apache.shiro.SecurityUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	// 处理登陆页面
	@RequestMapping("/login")
	public String index() {
		return "index";
	}

//	// 测试页面
//	@RequestMapping("/home")
//	public String home() {
//		return "indexViews/home";
//	}

	// 主页直接访问不可以
	@RequestMapping("/main")
	public String main() {
		Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		if (blogger == null) {
			return "errors/403";
		} else {
			return "main";
		}
	}

	// 登录页面直接拒绝必须经过login
	@RequestMapping("/index")
	public String indexDir() {
		return "errors/403";
	}
/**
 * 3、使用自己的判断来实现跳转
 * UI中任何页面跳转都要通过这个Controller---------主要是左侧导航栏菜单点击
 * @param path
 * @return
 */
	@RequestMapping("/views/{path}")
	public String index11(@PathVariable String path) {
		Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		if (blogger == null) {
			return "errors/403";
		} else {
			return "views/" + path;
		}
	}

	// UI中任何页面跳转都要通过这个Controller---------主要是左侧导航栏菜单点击
	/**
	 * @param path
	 * @return
	 */
	@RequestMapping("/indexViews/{path}")
	public String indexViews(@PathVariable String path) {
		Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession()
				.getAttribute("blogger");
		if (blogger == null) {
			return "redirect:/index/home"; // 如果在访问其他页面之前，博主信息 是空值，则先跳转
		}
		return "indexViews/" + path;
	}

	/**
	 * 404 405特别处理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/405")
	public String errorsFor403() {
		return "errors/405";
	}

	@RequestMapping(value = "/404")
	public String errorsFor404() {
		return "errors/404";
	}

}
