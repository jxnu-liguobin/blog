package cn.edu.jxnu.blog.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.edu.jxnu.blog.commons.ResponseUtil;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.BlogType;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.domin.Link;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.BlogTypeService;
import cn.edu.jxnu.blog.service.BloggerService;
import cn.edu.jxnu.blog.service.LinkService;

import com.alibaba.fastjson.JSONObject;

/**
 * 管理员系统Controller层
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Resource
	private BloggerService bloggerService;

	@Resource
	private BlogTypeService blogTypeService;

	@Resource
	private BlogService blogService;

	@Resource
	private LinkService linkService;
	//初始密码
	private static final String INITIAL_PASSWORD = "123456";

	/**
	 * 刷新系统缓存  这个有问题
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public String refreshSystem(HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		ServletContext application = RequestContextUtils
				.findWebApplicationContext(request).getServletContext();
		// 在4.2开始过时
		// ServletContext
		// application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		
		Blogger blogger = bloggerService.getBloggerData(); // 查询博主信息
		blogger.setPassword(INITIAL_PASSWORD); // 初始化密码
		application.setAttribute("blogger", blogger);
		// 刷新前台的数据
		List<BlogType> blogTypeCountList = blogTypeService.getBlogTypeData(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);

		List<Blog> blogCountList = blogService.countList(); // 根据日期分组查询博客
		application.setAttribute("blogCountList", blogCountList);

		List<Link> linkList = linkService.getTotalData(); // 获取所有友情链接
		application.setAttribute("linkList", linkList);
		
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}