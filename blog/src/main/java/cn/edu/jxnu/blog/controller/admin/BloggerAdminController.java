package cn.edu.jxnu.blog.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.jxnu.blog.commons.MD5Util;
import cn.edu.jxnu.blog.commons.ResponseUtil;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.service.BloggerService;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description 博主信息管理控制层
 */
@Controller
@RequestMapping(value = "/admin/blogger")
public class BloggerAdminController {

	@Resource
	private BloggerService bloggerService;

	// 获取博主信息
	@RequestMapping(value = "getBloggerInfo")
	public String getBloggerData(HttpServletResponse response) throws Exception {
		Blogger blogger = bloggerService.getBloggerData();
		String jsonStr = JSONObject.toJSONString(blogger);
		JSONObject object = JSONObject.parseObject(jsonStr);
		ResponseUtil.write(response, object);
		return null;
	}

	// 更新博主信息
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveBlogger(Blogger blogger, HttpServletResponse response)
			throws Exception {
		int resultTotal = bloggerService.updateBlogger(blogger);
		JSONObject result = new JSONObject();
		if (resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}

	// 更新博主密码
	@RequestMapping(value = "/modtifyPassword", method = RequestMethod.POST)
	public String modityBloggerPassword(Blogger blogger,
			HttpServletResponse response) throws Exception {
		// 加密
		System.out.println("正在更新密码......加密前的密码：" + blogger.getPassword());
		String newPassword = MD5Util.md5(blogger.getPassword(), blogger.getUserName());
		blogger.setPassword(newPassword);
		/** 此处是加用户名和密码进行MD5加密再保存 ***/
		int resultTotal = bloggerService.updateBlogger(blogger);
		System.out.println("更新后MD5加密的密码:" + newPassword);
		JSONObject result = new JSONObject();
		if (resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 注销
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout() throws Exception {
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}
}
