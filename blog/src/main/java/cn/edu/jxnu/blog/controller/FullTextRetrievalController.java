package cn.edu.jxnu.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.edu.jxnu.blog.commons.TreeMapComparator;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.lucene.BlogIndex;
import cn.edu.jxnu.blog.service.BlogTypeService;

/**
 * @Description 全文检索
 * @author liguobin
 * 
 */
@Controller
@RequestMapping("/blog")
public class FullTextRetrievalController {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(FullTextRetrievalController.class);
	// 初始化一个有序链表存放关键字
	private static Map<String, Integer> keyList = new HashMap<String, Integer>();
	@Resource
	private BlogIndex blogIndex;

	@Autowired
	private BlogTypeService blogTypeService;

	@RequestMapping("/search")
	public String search(
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "page", required = false) String page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int pageSize = 10;
		String keyString = null;
		if (page == null) {
			page = "1";// 为空表示第一次搜索
			log.info("当前第一次搜索");
			keyString = key;
		} else { // 第二页开始搜索
			keyString = (String) request.getSession().getAttribute("key");
			log.info("这是从ression中获取的搜索关键字");
		}
		List<Blog> blogIndexList = blogIndex.searchBlog(keyString);
		int fromIndex = (Integer.parseInt(page) - 1) * pageSize;// 开始索引
		int toIndex = blogIndexList.size() >= Integer.parseInt(page) * pageSize ? Integer
				.parseInt(page) * pageSize
				: blogIndexList.size();
		ServletContext application = RequestContextUtils
				.findWebApplicationContext(request).getServletContext();
		if (!keyList.containsKey(key)) { // 当前不存在key，则直接添加
			keyList.put(key, 1);
		} else {
			// 存在已知的key,直接加1吧
			keyList.put(key, keyList.get(keyString) + 1);
			log.info(key + "--搜索关键字热度加1");
		}
		TreeMap<String, Integer> sorted_map = null;
		if (keyList.size() > 1) {
			// 对hashmap进行封装排序。
			TreeMapComparator treeMapComparator = new TreeMapComparator(keyList);
			sorted_map = new TreeMap<String, Integer>(treeMapComparator);
			sorted_map.putAll(keyList);
			application.setAttribute("keyList", sorted_map);// 排序后的key

		} else {
			application.setAttribute("keyList", keyList);// 排序后的key
		}

		request.getSession().setAttribute("blogIndexList",
				blogIndexList.subList(fromIndex, toIndex));
		request.getSession().setAttribute("key", keyString);
		request.getSession().setAttribute("currentPage", page);
		request.getSession().setAttribute("searchCount", blogIndexList.size());
		log.info("关键字：" + key + "检索相关数量：" + blogIndexList.size());
		log.info("第" + page + "页" + "每页大小：" + pageSize);
		return "indexViews/article";
	}
}
