package cn.edu.jxnu.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.domin.Message;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.BlogTypeService;
import cn.edu.jxnu.blog.service.BloggerService;
import cn.edu.jxnu.blog.service.LinkService;
import cn.edu.jxnu.blog.service.MessageService;

/**
 * 
 * @author： liguobin
 * @Description： 留言控制器
 * @时间： 2017-12-5 下午1:22:07
 * @version： V1.0
 * 
 */
@Controller
@RequestMapping("/index")
public class AboutMeController {

	@Resource
	private BlogService blogService;
	@Resource
	private BloggerService bloggerService;
	@Resource
	private LinkService linkService;
	@Resource
	private BlogTypeService blogTypeService;

	@Resource
	private MessageService messageService;

	/**
	 * @Description 请求关于我们
	 * @return
	 */
	@RequestMapping("/about")
	public ModelAndView index(HttpServletRequest request) throws Exception {
		/**
		 * 携带留言信息到留言板。
		 * 
		 */
		// 查询评论
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		List<Message> messageList = messageService.getMessageData(map);
		for (Message message : messageList) {
			message.setAddress(message.getAddress() + "网友");
		}
		ModelAndView modelAndView = new ModelAndView();
		Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession()
				.getAttribute("blogger");
		if (blogger == null) {
			modelAndView.setViewName("indexViews/home");
			return modelAndView;
		} else {
			modelAndView.addObject("messageList", messageList);
			modelAndView.setViewName("indexViews/about");
		}
		return modelAndView;

	}
}
