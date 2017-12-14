package cn.edu.jxnu.blog.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.jxnu.blog.commons.ResponseUtil;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.Comment;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.CommentService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description 博客访问控制层
 */
@Controller
@RequestMapping(value = "admin/comment")
public class CommentAdminController {

	@Resource
	private BlogService blogService;
	@Resource
	private CommentService commentService;

	// 评论分页显示
	@RequestMapping(value = "/list")
	public String listByPage(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "state", required = false) String state,
			HttpServletResponse response) throws Exception {
		PageBean<Comment> pageBean = new PageBean<Comment>(
				Integer.parseInt(page), Integer.parseInt(limit));
		pageBean.getMap().put("state", state);
		pageBean = commentService.listByPage(pageBean);
		System.out.println(pageBean.getStart() + pageBean.getEnd());
		JSONObject result = new JSONObject();
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		String jsonStr = JSONObject.toJSONString(pageBean.getResult(),
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.DisableCircularReferenceDetect);
		JSONArray jsonArray = JSON.parseArray(jsonStr);
		result.put("count", pageBean.getTotal());
		result.put("data", jsonArray);
		result.put("code", 0);// 封装接口，成功返回0
		ResponseUtil.write(response, result);
		return null;
	}

	// 删除评论
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteComment(
			@RequestParam(value = "ids", required = false) String ids,
			HttpServletResponse response) throws Exception {
		String[] idStr = ids.split(",");
		for (int i = 0; i < idStr.length; i++) {
			commentService.deleteComment(Integer.parseInt(idStr[i]));
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 注意：只有评论审核通过才将评论数量+1
	 * 
	 * @param ids
	 * @param state
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "review",method=RequestMethod.POST)
	public String reviewComment(
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "state", required = false) String state,
			HttpServletResponse response) throws Exception {
		String[] idStr = ids.split(",");
		for (int i = 0; i < idStr.length; i++) {
			Comment comment = new Comment();
			comment.setId(Integer.parseInt(idStr[i]));
			comment.setState(Integer.parseInt(state));
			commentService.updateComment(comment); // 根据id更新评论的状态
			Comment commentTemp = commentService.getById(Integer.parseInt(idStr[i]));
			Blog blog = commentTemp.getBlog();// 根据id获取文章
			blog.setReplyHit(blog.getReplyHit() + 1);
			System.out.println("blog reply:"+blog.getReplyHit());
			blogService.updateBlog(blog);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}
