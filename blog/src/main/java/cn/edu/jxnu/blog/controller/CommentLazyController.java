//package cn.edu.jxnu.blog.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import cn.edu.jxnu.blog.domin.Comment;
//import cn.edu.jxnu.blog.domin.PageBean;
//import cn.edu.jxnu.blog.service.CommentService;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//
///**
// * 
// * @author： liguobin
// * @Description： 评论的懒加载分页
// * @时间： 2017-12-14 下午7:27:45
// * @version： V1.0
// * 
// */
//@RestController
//@RequestMapping(value = "/blog/commentLazy")
//public class CommentLazyController {
//
//	private static final Logger log = org.slf4j.LoggerFactory
//			.getLogger(CommentLazyController.class);
//	@Autowired
//	private CommentService commentService;
//
//	@RequestMapping(value = "/list")
//	public String listMessage(
//			@RequestParam(value = "page", required = false) String page,
//			@RequestParam(value = "blogId", required = false )
//			String blogId,
//			HttpServletRequest httpServletRequest) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("blogId", blogId);
//		map.put("state", 1); //只需已经审核的
//		PageBean<Comment> pageBean = new PageBean<>(Integer.parseInt(page), 10);
//		map.put("start", pageBean.getStart());
//		map.put("end", pageBean.getEnd());
//		log.info("当前请求懒加载分页....");
//		//分页
//		List<Comment>  comments= commentService.listComment(map);
//		pageBean.setResult(comments);
//		JSONObject result = new JSONObject();
//		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
//		String jsonStr = JSONObject.toJSONString(pageBean.getResult(),
//				SerializerFeature.WriteDateUseDateFormat,
//				SerializerFeature.DisableCircularReferenceDetect);
//		JSONArray jsonArray = JSON.parseArray(jsonStr);
//		result.put("count", pageBean.getCount());
//		result.put("total", pageBean.getTotal());
//		result.put("currentPage", pageBean.getCurrPage());
//		result.put("data", jsonArray);
//		result.put("code", 0);// 封装接口，成功返回0
//		return result.toJSONString();
//
//	}
//
//}
