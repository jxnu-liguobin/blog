//package cn.edu.jxnu.blog.controller.admin;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import cn.edu.jxnu.blog.domin.Blog;
//import cn.edu.jxnu.blog.domin.Comment;
//import cn.edu.jxnu.blog.domin.PageBean;
//import cn.edu.jxnu.blog.service.BlogService;
//import cn.edu.jxnu.blog.service.CommentService;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//
///**
// * @Description 博客访问控制层
// */
//@RestController
//@RequestMapping(value = "admin/comment")
//public class CommentAdminController {
//
//
//	private static final Logger log = org.slf4j.LoggerFactory
//			.getLogger(CommentAdminController.class);
//	@Resource
//	private BlogService blogService;
//	@Resource
//	private CommentService commentService;
//
//	// 评论分页显示
//	@RequestMapping(value = "/list")
//	public String listByPage(
//			@RequestParam(value = "page", required = false) String page,
//			@RequestParam(value = "limit", required = false) String limit,
//			@RequestParam(value = "state", required = false) String state)
//			throws Exception {
//		log.info("当前请求查看评论管理页面。。。");
//		PageBean<Comment> pageBean = new PageBean<Comment>(
//				Integer.parseInt(page), Integer.parseInt(limit));
//		pageBean.getMap().put("state", state);
//		pageBean = commentService.listByPage(pageBean);
//		System.out.println(pageBean.getStart() + pageBean.getEnd());
//		JSONObject result = new JSONObject();
//		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
//		String jsonStr = JSONObject.toJSONString(pageBean.getResult(),
//				SerializerFeature.WriteDateUseDateFormat,
//				SerializerFeature.DisableCircularReferenceDetect);
//		JSONArray jsonArray = JSON.parseArray(jsonStr);
//		result.put("count", pageBean.getTotal());
//		result.put("data", jsonArray);
//		result.put("code", 0);// 封装接口，成功返回0
//		return JSON.toJSONString(result);
//	}
//
//	// 删除评论
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public String deleteComment(
//			@RequestParam(value = "ids", required = false) String ids)
//			throws Exception {
//		log.info("当前请求删除评论。。。");
//		JSONObject result = new JSONObject();
//		String[] idStr = ids.split(",");
//		int delete =0;
//		for (int i = 0; i < idStr.length; i++) {
//			if (commentService.getById(Integer.parseInt(idStr[i])) != null) {
//				Comment comment = commentService.getById(Integer.parseInt(idStr[i]));
//				if (comment.getState().equals(1)) { //删除的是审核 通过的
//					// 删除评论的时候，如果是删除已经审核的评论，则需要减小文章的评论数
//					Blog blog = comment.getBlog();
//					if(blog != null) { //审核通过且博客存在，需要减
//						delete = commentService.deleteComment(Integer.parseInt(idStr[i]));
//						blog.setReplyHit(blog.getReplyHit() - 1);
//						blogService.updateBlog(blog);
//					}else { //审核通过但是博客不存在了。直接删除
//						delete = commentService.deleteComment(Integer.parseInt(idStr[i]));
//					}
//				} else {
//					delete = commentService.deleteComment(Integer.parseInt(idStr[i]));
//				}
//
//			}
//		}
//		if (delete>0) {
//			result.put("success", true); // 删除成功
//		}
//		return JSON.toJSONString(result);
//	}
//
//	/**
//	 * 注意：只有评论审核通过才将评论数量+1
//	 * 
//	 * @param ids
//	 * @param state
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "review", method = RequestMethod.POST)
//	public String reviewComment(
//			@RequestParam(value = "ids", required = false) String ids,
//			@RequestParam(value = "state", required = false) String state)
//			throws Exception {
//		log.info("当前请求审核评论。。。");
//		String[] idStr = ids.split(",");
//		for (int i = 0; i < idStr.length; i++) {
//			Comment comment = new Comment();
//			comment.setId(Integer.parseInt(idStr[i]));
//			comment.setState(Integer.parseInt(state));
//			commentService.updateComment(comment); // 根据id更新评论的状态
//			Comment commentTemp = commentService.getById(Integer.parseInt(idStr[i]));
//			Blog blog = commentTemp.getBlog();// 根据id获取文章
//			if (Integer.parseInt(state)==1) {
//				blog.setReplyHit(blog.getReplyHit() + 1);
//			}
//			System.out.println("blog reply:" + blog.getReplyHit());
//			blogService.updateBlog(blog);
//		}
//		JSONObject result = new JSONObject();
//		result.put("success", true);
//		return JSON.toJSONString(result);
//	}
//}
