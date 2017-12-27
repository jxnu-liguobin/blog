package cn.edu.jxnu.blog.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.jxnu.blog.commons.MD5Util;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.service.BloggerService;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description 博主信息管理控制层
 */
@Controller
@RequestMapping(value = "/admin/blogger")
public class BloggerAdminController {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(BloggerAdminController.class);
	@Resource
	private BloggerService bloggerService;

	// 获取博主信息
	@ResponseBody
	@RequestMapping(value = "getBloggerInfo")
	public String getBloggerData(HttpServletRequest request) throws Exception {
		Blogger blogger = null;
		log.info("当前请求获取博主信息。。。");
		if (request.getSession().getAttribute("currentUser") != null) {
			blogger = (Blogger) request.getSession() // 从session获取
					.getAttribute("currentUser");
		} else {
			blogger = bloggerService.getBloggerData();
		}

		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("blogger", blogger);
		return result.toJSONString();
	}

	// 更新博主信息
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveBlogger(Blogger blogger) throws Exception {
		log.info("当前请求更新博主信息。。。");
		int resultTotal = bloggerService.updateBlogger(blogger);
		JSONObject result = new JSONObject();
		if (resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result.toJSONString();
	}

	// 更新博主密码
	@ResponseBody
	@RequestMapping(value = "/modtifyPassword", method = RequestMethod.POST)
	public String modityBloggerPassword(Blogger blogger,
			@RequestParam(value = "token", required = true) String token)
			throws Exception {
		log.info("当前请求更新博主密码。。。");
		// 加密
		JSONObject result = new JSONObject();
		if (token == null || !MD5Util.TOKEN_FOR_MODIFYPASSWORD.equals(token)) {
			System.out.println("token:" + token);
			result.put("success", -1);
			return result.toJSONString();
		}
		log.info("正在更新密码......加密前的密码：" + blogger.getPassword());
		String newPassword = MD5Util.md5(blogger.getPassword(),
				blogger.getUserName());
		blogger.setPassword(newPassword);
		/** 此处是加用户名和密码进行MD5加密再保存 ***/
		int resultTotal = bloggerService.updateBlogger(blogger);
		log.info("更新后MD5加密的密码:" + newPassword);
		if (resultTotal > 0) {
			result.put("success", 1);
		} else {
			result.put("success", 0);
		}
		return result.toJSONString();
	}

	/**
	 * 注销
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout() throws Exception {
		log.info("您已经退出后台系统");
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}
}
