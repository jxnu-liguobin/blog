package cn.edu.jxnu.blog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.jxnu.blog.commons.AddressUtils;
import cn.edu.jxnu.blog.commons.ResponseUtil;
import cn.edu.jxnu.blog.domin.Message;
import cn.edu.jxnu.blog.service.BlogService;
import cn.edu.jxnu.blog.service.MessageService;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description 前台评论控制器 此控制器使用SpringBoot的@RestController。 验证码为了方便放在java的session中
 */
@RestController
@RequestMapping(value = "/blog/message")
public class MessageController {

	@Resource
	private MessageService messageService;
	@Resource
	private BlogService blogService;

	// 评论更新或者添加
	@RequestMapping(value = "save")
	public String save(Message message, HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "email", required = false) String email)
			throws Exception {

		// 四位数的String形式
		JSONObject result = new JSONObject();
		// 得到后台session的验证码
		String sessionCode = (String) session.getAttribute("code");
		if (!StringUtils.equalsIgnoreCase(code, sessionCode)) { // 忽略验证码大小写
			System.out.println("验证码对应不上code=" + code + "  sessionCode="
					+ sessionCode);
			result.put("success", false);
			result.put("errorInfo", "验证码错误！");
			ResponseUtil.write(response, result);
			return null;
		} else {
			int resultTotal = 0; // 执行记录数
			// 获取评论者ip
//			String ip = request.getRemoteAddr();
			String ip = AddressUtils.getRealIp(request);
			String address = AddressUtils.getAddress("ip="+ip, "utf-8");
			System.out.println("留言者：ip=" + ip);
			message.setUserIp(ip);
			message.setAddress(address);
			message.setState(0);//默认设置0，待审核
			if (message.getId() == null) {
				resultTotal = messageService.saveMessage(message); // 添加留言
				// Blog blog = blogService.getById(comment.getBlog().getId());
				// // 更新一下博客的评论次数
				// blog.setReplyHit(blog.getReplyHit() + 1);//在这里不加1
				// blogService.updateBlog(blog);
			} else {
				// 更新操作 暂时不考虑
			}

			if (resultTotal > 0) {
				result.put("success", true);
			}
			return result.toJSONString();
		}
	}

}
