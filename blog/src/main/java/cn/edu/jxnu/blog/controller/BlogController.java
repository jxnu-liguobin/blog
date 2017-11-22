package cn.edu.jxnu.blog.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.jxnu.blog.commons.AddressUtils;
import cn.edu.jxnu.blog.commons.StringUtil;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.BlogType;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.domin.Comment;
import cn.edu.jxnu.blog.lucene.BlogIndex;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.BlogTypeService;
import cn.edu.jxnu.blog.service.BloggerService;
import cn.edu.jxnu.blog.service.CommentService;

/**
 * 博客前台访问控制器
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
	@Resource
	private BlogService blogService;
	@Resource
	private CommentService commentService;
	@Resource
	private BlogIndex blogIndex;

	@Autowired
	private BloggerService bloggerService;

	@Autowired
	private BlogTypeService blogTypeService;

	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Integer id,
			HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// 获取id对于的博客
		Blog blog = blogService.getById(id);
		blog.setReleaseDateStr(new SimpleDateFormat("YYYY/MM/dd HH:MM:SS")
				.format(blog.getReleaseDate()));
		// 获取关键字
		String keyWords = blog.getKeyWord();
		if (StringUtil.isNotEmpty(keyWords)) {
			String[] StrArray = keyWords.split(" ");
			List<String> keyWordsList = StringUtil.filterWhite(Arrays
					.asList(StrArray));
			modelAndView.addObject("keyWords", keyWordsList);

		} else {
			modelAndView.addObject("keyWords", null);
		}
		// 修改图片的大小
		modelAndView.addObject("blog", blog);

		blog.setClickHit(blog.getClickHit() + 1);// 博客访问量加1
		blogService.updateBlog(blog);// 更新博客

		// 查询评论
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blogId", blog.getId());
		System.out.println("blogId:" + blog.getId());
		map.put("state", 1);
		List<Comment> commentList = commentService.getCommentData(map);
		for (Comment comment : commentList) {
			String userIp = "";
			userIp = AddressUtils.getAddress("ip=" + comment.getUserIp(),
					"utf-8");
			if (!"获取地址失败！".equals(comment.getUserIp())
					|| !"内网Ip 内网Ip".equals(comment.getUserIp())) {
				String uuid = UUID.randomUUID().toString();
				String uid = uuid.replaceAll("-", " ");
				comment.setUserIp("匿名" + uid.substring(26) + "网友");

			} else {
				comment.setUserIp(userIp + "网友");
			}
		}
		Blogger blogger = bloggerService.getBloggerData();
		List<Blog> bloglist = blogService
				.listBlog(new HashMap<String, Object>());
		if (bloglist.size()>10) {
			List<Blog> blogList = bloglist.subList(0, 9);// 随机取出最近更新的10条
			modelAndView.addObject("blogList", blogList);

		} else {
			modelAndView.addObject("blogList", bloglist);
		}
		
		List<BlogType> blogTypeList = blogTypeService.getBlogTypeData();
		modelAndView.addObject("commentList", commentList);
		modelAndView.addObject("blogger", blogger);
		modelAndView.addObject("blogTypeList", blogTypeList);
		// modelAndView.addObject("commonPage",
		// "foreground/blog/blogDetail.jsp");
		// modelAndView.addObject("title", blog.getTitle() + " - xxx的博客");
		// 存入上一篇和下一篇的显示代码
		/*
		 * modelAndView.addObject("pageCode", PageUtil.getPrevAndNextPageCode(
		 * blogService.getPrevBlog(id), blogService.getNextBlog(id),
		 * request.getServletContext().getContextPath()));
		 */

		modelAndView.setViewName("/indexViews/detail");

		return modelAndView;

	}

	/*
	 * @RequestMapping("/search") public ModelAndView search(
	 * 
	 * @RequestParam(value = "q", required = false) String q,
	 * 
	 * @RequestParam(value = "page", required = false) String page,
	 * HttpServletRequest request) throws Exception { int pageSize = 10;
	 * ModelAndView modelAndView = new ModelAndView(); List<Blog> blogIndexList
	 * = blogIndex.searchBlog(q); if (page == null) { page = "1";// 为空表示第一次搜索 }
	 * int fromIndex = (Integer.parseInt(page) - 1) * pageSize;// 开始索引 int
	 * toIndex = blogIndexList.size() >= Integer.parseInt(page) * pageSize ?
	 * Integer .parseInt(page) * pageSize : blogIndexList.size();
	 * modelAndView.addObject("blogIndexList", blogIndexList.subList(fromIndex,
	 * toIndex)); modelAndView.addObject("pageCode",
	 * PageUtil.getUpAndDownPageCode( Integer.parseInt(page),
	 * blogIndexList.size(), q, pageSize,
	 * request.getServletContext().getContextPath()));
	 * modelAndView.addObject("q", q); // 用于数据的回显
	 * modelAndView.addObject("resultTotal", blogIndexList.size()); // 查询到的总记录数
	 * modelAndView .addObject("commonPage",
	 * "foreground/blog/searchResult.jsp"); modelAndView.addObject("title",
	 * "搜索'" + q + "'的结果 - XXX的博客"); modelAndView.setViewName("mainTemp");
	 * return modelAndView; }
	 */
}
