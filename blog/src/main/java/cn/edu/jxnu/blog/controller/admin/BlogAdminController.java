package cn.edu.jxnu.blog.controller.admin;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.jxnu.blog.commons.ResponseUtil;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.lucene.BlogIndex;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.CommentService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description 此处不需要@ResponseBody 或者@RestController
 * 
 * @Description 管理员博客Controller层
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Resource
	private BlogService blogService;
	@Resource
	private BlogIndex blogIndex;

	@Autowired
	private CommentService commentService;

	// 后台分页查询博客信息
	@RequestMapping("/listBlog")
	public String listBlog(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "title", required = false) String title,
			Blog s_blog, HttpServletResponse response) throws Exception {

		PageBean<Blog> pageBean = null;
		// 创建json对象
		JSONObject result = new JSONObject();
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		String jsonStr = null;
		// 处理title作为一个参数进行的查询
		if (title != null && !"".equals(title)) {
			// 根据标题模糊查询
			pageBean = new PageBean<Blog>(Integer.parseInt(page),
					Integer.parseInt(limit)); // 构建分页bean
			pageBean = blogService.listBlog(title, pageBean);// 根据分页条件进行查询
			if (pageBean.getResult() != null) { // 查询到有关标题数据
				jsonStr = JSONObject.toJSONString(pageBean.getResult(),
						SerializerFeature.DisableCircularReferenceDetect,
						SerializerFeature.WriteDateUseDateFormat);
			} else {
				result.put("count", 0);// 无数据
				result.put("code", 0);// 封装接口，成功返回0
				ResponseUtil.write(response, result);
				return null;
			}
		} else { // title 为空,正常分页
			pageBean = new PageBean<Blog>(Integer.parseInt(page),
					Integer.parseInt(limit));
			pageBean = blogService.listBlog(s_blog.getTitle(), pageBean);
			// 禁止对象循环引用
			jsonStr = JSONObject.toJSONString(pageBean.getResult(),
					SerializerFeature.DisableCircularReferenceDetect,
					SerializerFeature.WriteDateUseDateFormat);
		}
		// 把json字符串转换成得到json数组
		JSONArray array = JSON.parseArray(jsonStr);
		result.put("data", array);
		result.put("count", pageBean.getTotal());
		result.put("code", 0);// 封装接口，成功返回0
		// 返回 一个Blog
		ResponseUtil.write(response, result);
		return null;
	}

	// 更新或者新增博客
	@RequestMapping(value = "/save",method=RequestMethod.POST)
	public String saveBlog(Blog blog, HttpServletResponse response)
			throws Exception {
		int resultTotal = 0;
		//博客id 不为空则是对博客的修改
		if (blog.getId() != null) {
			Date time = new Date();
			blog.setReleaseDate(time);
			// 更新操作
			resultTotal = blogService.updateBlog(blog);
			// 更新索引
			blogIndex.updateIndex(blog);
		} else {
			// 新增操作
			resultTotal = blogService.saveBlog(blog);
			// 添加索引
			blogIndex.addIndex(blog);
		}
		JSONObject result = new JSONObject();
		if (resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}

	// 删除博客
	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	public String deleteBlog(@RequestParam("ids") String ids,
			HttpServletResponse response) throws Exception {
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			int id = Integer.parseInt(idsStr[i]);
			// 先删除博客所关联的评论 现在没有完成评论的功能 先注释
			commentService.deleteCommentByBlogId(id);
			blogService.deleteBlog(id);
			blogIndex.deleteIndex(String.valueOf(id));// 删除索引
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

	// 通过id获取博客
	@RequestMapping(value = "/get", produces = "application/json;charset=UTF-8")
	public String getById(@RequestParam("id") String id,
			HttpServletResponse response) throws Exception {

		Blog blog = blogService.getById(Integer.parseInt(id));
		String jsonStr = JSONObject.toJSONString(blog);
		JSONObject result = JSONObject.parseObject(jsonStr);
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

}