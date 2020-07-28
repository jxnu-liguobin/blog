package cn.edu.jxnu.blog.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.BlogType;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.domin.Link;
import cn.edu.jxnu.blog.domin.Notice;
import cn.edu.jxnu.blog.lucene.BlogIndex;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.BlogTypeService;
import cn.edu.jxnu.blog.service.BloggerService;
import cn.edu.jxnu.blog.service.LinkService;
import cn.edu.jxnu.blog.service.NoticeService;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description 管理员系统Controller层
 * 
 * @author Administrator
 * 
 */
@RestController
@RequestMapping("/admin/system")
public class SystemAdminController {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(SystemAdminController.class);
	@Resource
	private BloggerService bloggerService;

	@Resource
	private BlogTypeService blogTypeService;

	@Resource
	private BlogService blogService;

	@Resource
	private LinkService linkService;

	@Resource
	private BlogIndex blogIndex;

	@Resource
	private NoticeService noticeService;

	// 初始密码
	// private static final String INITIAL_PASSWORD = "123456";

	/**
	 * 刷新系统缓存 这个有问题
	 * 
	 * @decription 这里只是从数据库获取 链接，日期，分类，公告，博主信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public String refreshSystem(HttpServletRequest request) throws Exception {
		log.info("当前请求刷新系统缓存。。。");
		ServletContext application = RequestContextUtils
				.findWebApplicationContext(request).getServletContext();
		// 在4.2开始过时
		// ServletContext
		// application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		Blogger blogger = bloggerService.getBloggerData(); // 查询博主信息
		//blogger.setPassword(INITIAL_PASSWORD); // 初始化密码  十分钟后生效,而博主前台信息及时生效
		application.setAttribute("blogger", blogger);
		// 刷新前台的数据
		List<BlogType> blogTypeCountList = blogTypeService.getBlogTypeData(); // 查询博客类别
		application.setAttribute("blogTypeCountList", blogTypeCountList);

		List<Blog> blogCountList = blogService.countList(); // 日期分组查询博客
		application.setAttribute("blogCountList", blogCountList);

		List<Notice> list = noticeService.getAllNotices(); // 等级降序公告
		application.setAttribute("notice", list);

		List<Link> linkList = linkService.getTotalData(); // 友情链接
		application.setAttribute("linkList", linkList);
		// 删除所有索引
		// blogIndex.dellAllIndex();
		// 清空关键字
		if (application.getAttribute("keyList") != null)
			application.removeAttribute("keyList");
		JSONObject result = new JSONObject();
		result.put("success", true);
		return result.toJSONString();
	}
}