package cn.edu.jxnu.blog.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.edu.jxnu.blog.commons.AddressUtils;
import cn.edu.jxnu.blog.commons.StringUtil;
import cn.edu.jxnu.blog.commons.TreeMapComparatorForkinds;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.BlogType;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.lucene.BlogIndex;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.BlogTypeService;
import cn.edu.jxnu.blog.service.BloggerService;
import cn.edu.jxnu.blog.service.CommentService;

/**
 * @author liguobin
 * @Description 显示博客详情
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
	
	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(BlogController.class);
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

	@SuppressWarnings("unchecked")
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Integer id,
			HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// 获取id对于的博客
		Blog blog = blogService.getById(id);
		if (blog == null) {
			modelAndView.setViewName("errors/404");
			return modelAndView;
		}
		blog.setReleaseDateStr(new SimpleDateFormat("YYYY/MM/dd HH:MM:SS")
				.format(blog.getReleaseDate()));
		// 获取关键字
		String keyWords = blog.getKeyWord();
		if (StringUtil.isNotEmpty(keyWords)) {
			String[] StrArray = keyWords.split(" ");
			List<String> keyWordsList = StringUtil.filterWhite(Arrays
					.asList(StrArray));
			modelAndView.addObject("blogKeys", keyWordsList);

		} else {
			modelAndView.addObject("blogKeys", null);
		}
		// 修改图片的大小
		ServletContext application = RequestContextUtils
				.findWebApplicationContext(request).getServletContext();
		Map<Integer, Long> hashMap = new HashMap<>();
		Map<String, Map<Integer, Long>> userMap = null;
		String ip = AddressUtils.getRealIp(request);
		synchronized(this) {
		if (application.getAttribute("userMap") != null) {
			// 得到usermap
			userMap = (Map<String, Map<Integer, Long>>) application
					.getAttribute("userMap");
			// userMap中没有该ip，此时一个关系ip~blogId~时间都没有
			if (userMap.get(ip) == null || "".equals(userMap.get(ip))) {
				// 将该ip设置进去
				hashMap.put(blog.getId(), System.currentTimeMillis());
				// ip~blogId~时间
				userMap.put(ip, hashMap);
				application.setAttribute("userMap", userMap);
				blog.setClickHit(blog.getClickHit() + 1);
				// 更新博客数据到数据库
				blogService.updateBlog(blog);
				log.info("访客：" + ip + " 第1次阅文章！");
			} else {
				// 此时可能有多个ip~blogId~时间关系 ,遍历 UserMap
				int i = userMap.size();
				while (i > 0) {
					// 遍历ip~map
					// 遍历map
					if (userMap.containsKey(ip)) { // 遍历的时候发现存在该ip
						// 取得该ip下的blogId~时间
						Map<Integer, Long> hashMap2 = userMap.get(ip);
						// 遍历blog与当前blog是否同一篇
						int j = hashMap2.size();
						while (j > 0) {
							// 如果是同一篇
							if (hashMap2.containsKey(blog.getId())) {
								// 获得该id对于的时间进行修改
								long time = hashMap2.get(blog.getId());// 上次访问的时间
								if (System.currentTimeMillis() - time >= 1000 * 60 * 60 * 24) {
									// 已经过期、可以继续加1
									log.info("访客：" + ip+ " 非第1次阅读此文章，但是时间间隔超过一天了！");
									blog.setClickHit(blog.getClickHit() + 1);
									blogService.updateBlog(blog);
									// 重新覆盖此文章的时间
									hashMap2.put(blog.getId(),
											System.currentTimeMillis());
									// 修改全局map
									userMap.put(ip, hashMap2);
									application.setAttribute("userMap", userMap);
								} else {
									// 时间间隔没有超过一天
									log.info("访客："+ ip+ " 非第1次阅读此文章，而且时间间隔没有超过一天，不进行增加浏览量！");
									// 但是时间还是需要修改的
									hashMap2.put(blog.getId(),System.currentTimeMillis());
									userMap.put(ip, hashMap2);
									application.setAttribute("userMap", userMap);
								}
							} else {
								// 不是同一篇 添加
								hashMap2.put(blog.getId(),System.currentTimeMillis());
								userMap.put(ip, hashMap2);
								application.setAttribute("userMap", userMap);
								blog.setClickHit(blog.getClickHit() + 1);
								blogService.updateBlog(blog);
							}
							j--;
						}
					} else {
						// 第一次用户
						hashMap.put(blog.getId(), System.currentTimeMillis());
						userMap.put(ip, hashMap);
						application.setAttribute("userMap", userMap);
						blog.setClickHit(blog.getClickHit() + 1);
						blogService.updateBlog(blog);
					}
					i--;
				}
			}
		} 
	}
		modelAndView.addObject("blog", blog);
	/*	List<Comment> commentList = commentService.queryCommentsByBlogId(blog
				.getId());
		for (Comment message : commentList) {
			message.setAddress(message.getAddress() + "网友");
		}*/
		Blogger blogger = bloggerService.getBloggerData();
		Map<String, Object> mapForComment = new HashMap<String, Object>();
		List<Blog> bloglist = blogService.listBlog(mapForComment);// 所有博客数据
		Collections.sort(bloglist);// 以评论量排序，取出state=1的评论和博客文章比较。
		Map<String, Integer> m = new IdentityHashMap<String, Integer>();
		for (Blog b : bloglist) {
			String[] strings = b.getKeyWord().split(" ");
			for (String string : strings) {
				m.put(string, b.getId());// 保存所有id-关键字 可重复。
			}
		}
		if (m.size() > 20) {
			// Set<Integer> mapForrandom=
			// ProdRandom.getRandom(m.size());//生成随机数的范围0~m.size()
			// Iterator<Entry<String, Integer>> entries =
			// m.entrySet().iterator(); //得到所有标签
			// while (entries.hasNext()) {
			// Entry<String, Integer> entry = entries.next();
			//
			// }

		}
		// 大于1的时候，才可以比较
		TreeMapComparatorForkinds tForkinds = null;
		TreeMap<String, Object> sorted_map = null;
		if (m.size() > 1) {
			tForkinds = new TreeMapComparatorForkinds(m);
			sorted_map = new TreeMap<String, Object>(tForkinds);// 使用自己实现的比较器来构造treeMap
			sorted_map.putAll(m);
			modelAndView.addObject("keysMap", sorted_map);
		} else {
			modelAndView.addObject("keysMap", m); // 只有一个关键字不需要排序。
		}
		List<Blog> blogList = new ArrayList<Blog>();
		// 选出评论量最高的10篇文章
		if (bloglist.size() > 10) {
			for (int i = 0; i < 10; i++) {
				Blog blogTemp = bloglist.get(i);
				blogList.add(blogTemp);
			}
			modelAndView.addObject("blogList", blogList);

		} else {
			modelAndView.addObject("blogList", bloglist); // 评论排行 应该只需要state==1
		}
		List<BlogType> blogTypeList = blogTypeService.getBlogTypeData();
		/*modelAndView.addObject("commentList", commentList);*/
		modelAndView.addObject("blogger", blogger);
		modelAndView.addObject("blogTypeList", blogTypeList);
		modelAndView.setViewName("indexViews/detail");
		return modelAndView;

	}
}
