//package cn.edu.jxnu.blog.controller;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import cn.edu.jxnu.blog.commons.AddressUtils;
//import cn.edu.jxnu.blog.domin.Comment;
//import cn.edu.jxnu.blog.service.BlogService;
//import cn.edu.jxnu.blog.service.CommentService;
//
//import com.alibaba.druid.util.StringUtils;
//import com.alibaba.fastjson.JSONObject;
//
///**
// * @Description 前台评论控制器 此控制器使用SpringBoot的@RestController。 验证码为了方便放在java的session中
// */
//@RestController
//@RequestMapping(value = "/blog/comment")
//public class CommentController {
//
//	private static final Logger log = org.slf4j.LoggerFactory
//			.getLogger(CommentController.class);
//	@Resource
//	private CommentService commentService;
//	@Resource
//	private BlogService blogService;
//	@Autowired
//	private RedisTemplate<String, String> redisTemplate;
//
//	// 评论更新或者添加
//	@RequestMapping(value = "save")
//	public String save(Comment comment, HttpServletRequest request,
//			HttpSession session,
//			@RequestParam(value = "code", required = true) String code)
//			throws Exception {
//
//		// 四位数的String形式
//		JSONObject result = new JSONObject();
//		// 得到后台session的验证码
//		// String sessionCode = (String) session.getAttribute("code");
//		String sessionCode = redisTemplate.opsForValue().get("code");
//		log.info("当前rediscode过期时间："+redisTemplate.getExpire("code"));
//		log.info("redis的验证码：" + sessionCode);
//		if (!StringUtils.equalsIgnoreCase(code, sessionCode)) { // 忽略验证码大小写
//			log.info("验证码对应不上code=" + code + "  redisCode=" + sessionCode);
//			result.put("success", false);
//			result.put("errorInfo", "验证码错误！");
//			return result.toJSONString();
//		} else {
//			int resultTotal = 0; // 执行记录数
//			// 获取评论者ip
//			// String ip = request.getRemoteAddr();
//			String ip = AddressUtils.getRealIp(request);
//			log.info("评论者：ip=" + ip);
//			String address = AddressUtils.getAddress("ip=" + ip, "utf-8");
//			comment.setUserIp(ip);
//			comment.setAddress(address);
//			if (comment.getId() == null) {
//				resultTotal = commentService.saveComment(comment); // 添加评论
//				// Blog blog = blogService.getById(comment.getBlog().getId());
//				// // 更新一下博客的评论次数
//				// blog.setReplyHit(blog.getReplyHit() + 1);//在这里不加1
//				// blogService.updateBlog(blog);
//			} else {
//				// 更新操作 暂时不考虑
//			}
//
//			if (resultTotal > 0) {
//				result.put("success", true);
//			}
//			return result.toJSONString();
//		}
//	}
//
//}
