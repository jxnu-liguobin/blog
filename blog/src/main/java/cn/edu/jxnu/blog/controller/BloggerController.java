package cn.edu.jxnu.blog.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.edu.jxnu.blog.commons.AddressUtils;
import cn.edu.jxnu.blog.commons.MD5Util;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.BlogTypeService;
import cn.edu.jxnu.blog.service.BloggerService;
import cn.edu.jxnu.blog.service.CommentService;
import cn.edu.jxnu.blog.service.LinkService;
import cn.edu.jxnu.blog.service.MessageService;

import com.alibaba.druid.util.StringUtils;

/**
 * @Description 博主控制层前台 不需要shiro认证
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(BloggerController.class);
	@Resource
	private BloggerService bloggerService;
	@Resource
	private BlogService blogService;
	@Resource
	private BlogTypeService blogTypeService;
	@Resource
	private LinkService linkService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;


	@RequestMapping(value = "/login.html", method = RequestMethod.POST)
	public String login(Blogger blogger,
	/* @RequestParam(value = "rememberMe", required = false) boolean rememberMe, */
	HttpServletRequest request) throws Exception {

		// System.out.println("是否记住密码：" + rememberMe);
		Subject subject = SecurityUtils.getSubject();
		log.info("加密前用户名：" + blogger.getUserName()  + "加密前密码："
				+ blogger.getPassword());
		// 获取加密后密码
		String password = MD5Util.md5(blogger.getPassword(),
				blogger.getUserName());
		log.info("加密后的密码：" + password);
		// 获取用户密码登录token 将需要验证的用户名和密码的MD5值传给myrealm
		UsernamePasswordToken token = new UsernamePasswordToken(
				blogger.getUserName(), password);

		// token.setRememberMe(rememberMe);

		try {
			// 根据token登录 会调用MyRealm中的doGetAuthenticationInfo方法进行身份认证
			subject.login(token);
			log.info("验证密码成功......");
			String code = request.getParameter("code"); // 得到验证码
			// 得到后台session的验证码
			HttpSession session = request.getSession();
			String sessionCode = (String) redisTemplate.opsForValue().get("code");
			log.info("shiro控制的session---->当前session超时=" + session.getMaxInactiveInterval() + "S");
			log.info("当前rediscode过期时间："+redisTemplate.getExpire("code"));
			if (!StringUtils.equalsIgnoreCase(code, sessionCode)) { // 忽略验证码大小写
				log.info("验证码对应不上code=" + code + "  redisCode=" + sessionCode);
				request.setAttribute("errorInfo", "验证码错误！");
				return "index";
			}
			log.info("**********************************");
			// 获取博客总数量
			Map<String, Object> map = new HashMap<String, Object>();
			long articleCount = blogService.getTotal(map);
			// 获取博客总访问量
			Map<String, Object> mapBlogClick = new HashMap<>();
			List<Blog> blogClick = blogService.listBlog(mapBlogClick);
			int blogClickCount = 0;
			for (Blog blog : blogClick) {
				blogClickCount += blog.getClickHit();
			}

//			Map<String, Object> ismap = new HashMap<>();
//			Map<String, Object> unmap = new HashMap<>();
//			unmap.put("state", 0);// 待审核
//			Long commentCount0 = commentService.getTotal(unmap);// 待审核
//			Long commentCount = commentService.getTotal(ismap);// 评论总数量
			String ip = AddressUtils.getRealIp(request);
			log.info("博主：ip=" + ip);
			// String ip = request.getRemoteAddr(); // ip
			String address = AddressUtils.getAddress("ip=" + ip, "utf-8"); // 地址
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date = new java.util.Date(); // 登录的时间
			String str = sdf.format(date);
			ServletContext application = RequestContextUtils
					.findWebApplicationContext(request).getServletContext();
			Map<String, Object> map2 = new HashMap<>();
			Map<String, Object> map3 = new HashMap<>();
			map2.put("state", 0);// 待审核
			Long messageCount0 = messageService.getTotal(map2);
			Long messageCount = messageService.getTotal(map3);// 总留言
			application.setAttribute("articleCount", articleCount);
//			application.setAttribute("commentCount0", commentCount0);
//			application.setAttribute("commentCount", commentCount);
			application.setAttribute("blogClickCount", blogClickCount);
			application.setAttribute("messageCount0", messageCount0);
			application.setAttribute("messageCount", messageCount);
			application.setAttribute("ip", ip);
			application.setAttribute("address", address);
			application.setAttribute("str", str);
			return "main";
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			// request.setAttribute("blogger", blogger);
			// 提示信息
			request.setAttribute("errorInfo", "用户名或密码错误！");
			// return "index";
		} catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			// 提示信息
			request.setAttribute("errorInfo", "用户名或密码错误！");
			// return "index";
		} catch (ExcessiveAttemptsException e) {
			e.printStackTrace();
			request.setAttribute("errorInfo", "登录失败次数过多");
		}
		return "index";

	}
}
