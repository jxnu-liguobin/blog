package cn.edu.jxnu.blog.druid;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.springframework.stereotype.Component;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * 
 * @author： liguobin
 * @Description：
 * @时间： 2017-12-26 下午12:35:05
 * @version： V1.0
 * 
 */
@Component
@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/druid/*", initParams = {
		@WebInitParam(name = "allow", value = ""),// IP白名单 (没有配置或者为空，则允许所有访问)
		@WebInitParam(name = "deny", value = ""),// IP黑名单 (存在共同时，deny优先于allow)
		@WebInitParam(name = "loginUsername", value = "liguobin"),// 用户名
		@WebInitParam(name = "loginPassword", value = "liguobin"),// 密码
		@WebInitParam(name = "resetEnable", value = "false") // 禁用HTML页面上的“Reset// All”功能
})
public class DruidStatViewServlet extends StatViewServlet {

}