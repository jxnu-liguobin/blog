package cn.edu.jxnu.blog;

import org.apache.shiro.SecurityUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.jxnu.blog.domin.Blogger;

@Controller
@SpringBootApplication
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

	// 测试页面
	@RequestMapping("/home")
	public String home() {
		return "/indexViews/home";
	}

	// 主页直接访问不可以
	@RequestMapping("/main")
	public String main() {
		Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		if (blogger == null) {
			return "errors/404";
		} else {
			return "/main";
		}
	}

	// 登录页面直接拒绝必须经过login
	@RequestMapping("/index")
	public String indexDir() {
		return "/errors/404";
	}

	// UI中任何页面跳转都要通过这个Controller---------主要是左侧导航栏菜单点击
	@RequestMapping("/views/{path}")
	public String index11(@PathVariable String path) {
		return "/views/" + path;
	}

	// UI中任何页面跳转都要通过这个Controller---------主要是左侧导航栏菜单点击
	@RequestMapping("/indexViews/{path}")
	public String indexViews(@PathVariable String path) {
		return "/indexViews/" + path;
	}
}
