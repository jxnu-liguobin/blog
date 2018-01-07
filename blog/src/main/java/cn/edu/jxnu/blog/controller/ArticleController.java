package cn.edu.jxnu.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.edu.jxnu.blog.commons.PVFinalCount;
import cn.edu.jxnu.blog.commons.StringUtil;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.domin.Link;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.BlogTypeService;
import cn.edu.jxnu.blog.service.BloggerService;
import cn.edu.jxnu.blog.service.LinkService;

/**
 * @Description 文章主题
 */
@Controller
@RequestMapping("/index")
public class ArticleController {
	
	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(ArticleController.class);
	@Resource
	private BlogService blogService;
	@Resource
	private BloggerService bloggerService;
	@Resource
	private LinkService linkService;
	@Resource
	private BlogTypeService blogTypeService;

	@RequestMapping("/article.html")
	public String index(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr,
			HttpServletRequest request) throws Exception {

		PVFinalCount.Count.incrementAndGet();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		// 获取分页的bean
		PageBean<Blog> pageBean = new PageBean<Blog>(Integer.parseInt(page), 10); // 每页显示10条数据

		// map中封装起始页和每页的记录，按条件分类
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("end", pageBean.getEnd());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		map.put("orderBy", "releaseDate");
		// 获取博客信息
		List<Blog> blogList = blogService.listBlog(map);
		if (releaseDateStr != null) {
			Map<String, Object> map2 = new HashMap<>();
			map2.put("releaseDateStr", releaseDateStr);
			pageBean.setTotal(blogService.listBlog(map2).size());// 给前台总的记录数
		} else if (typeId != null) {
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("typeId", typeId);
			pageBean.setTotal(blogService.listBlog(map3).size());// 给前台总的记录数
		} else {
			pageBean.setTotal(blogService.listBlog(
					new HashMap<String, Object>()).size());// 给前台总的记录数
		}
		pageBean.setResult(blogList);
		for (Blog blog : blogList) {
			List<String> imageList = blog.getImageList();
			String blogInfo = blog.getContent(); // 获取博客内容
			Document doc = Jsoup.parse(blogInfo); // 将博客内容(网页中也就是一些html)转为jsoup的Document
			Elements jpgs = doc.select("img[src~=(jpg|jpeg|bmp|png)$]");// 获取<img>标签中所有后缀是.jpg的元素
			for (int i = 0; i < jpgs.size(); i++) {
				Element jpg = jpgs.get(i); // 获取到单个元素
				// System.out.println(jpg.toString().replace(">",
				// " style='width: 180px;height: 50px;'>"));
				imageList.add(jpg.toString().replace(">",
						" style='width: 184px;height: 97px;'>")); // 把图片信息存到imageList中
				if (i == 2)
					break; // 只存三张图片信息
			}
		}
		log.info("当前请求的是文章专栏 ip:" + request.getRemoteAddr());
		// 分页
		List<Link> linkList = linkService.getTotalData();
		Blogger blogger = bloggerService.getBloggerData();
		ServletContext application = RequestContextUtils
				.findWebApplicationContext(request).getServletContext();
		Map<String, Object> readMap = new HashMap<>();
		readMap.put("orderBy", "clickHit"); // 设置排序方式 设置方法=${}
		List<Blog> bloglist = blogService.listBlog(readMap);
		List<Blog> blogCountList = blogService.countList();
		application.setAttribute("bloglist", bloglist); // 阅读排行
		application.setAttribute("blogger", blogger); // 博主信息
		application.setAttribute("linkList", linkList); // 友情链接
		application.setAttribute("blogCountList", blogCountList); // 日期分档博客信息
		application.setAttribute("pageBean", pageBean);
		return "indexViews/article";

	}
}
