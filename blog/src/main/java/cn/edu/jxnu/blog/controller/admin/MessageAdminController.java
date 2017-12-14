package cn.edu.jxnu.blog.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.jxnu.blog.commons.ResponseUtil;
import cn.edu.jxnu.blog.domin.Message;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.MessageService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description 博客访问控制层
 */
@Controller
@RequestMapping(value = "admin/message")
public class MessageAdminController {

	@Resource
	private MessageService messageService;

	// 分页显示
	@RequestMapping(value = "/list")
	public String listByPage(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "state", required = false) String state,
			HttpServletResponse response) throws Exception {
		PageBean<Message> pageBean = new PageBean<Message>(
				Integer.parseInt(page), Integer.parseInt(limit));
		pageBean.getMap().put("state", state);
		pageBean = messageService.listByPage(pageBean);
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
	public String deleteMessage(
			@RequestParam(value = "ids", required = false) String ids,
			HttpServletResponse response) throws Exception {
		String[] idStr = ids.split(",");
		for (int i = 0; i < idStr.length; i++) {
			messageService.deleteMessage(Integer.parseInt(idStr[i]));
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

	@RequestMapping(value = "review",method=RequestMethod.POST)
	public String reviewMessage(
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "state", required = false) String state,
			HttpServletResponse response) throws Exception {
		String[] idStr = ids.split(",");
		for (int i = 0; i < idStr.length; i++) {
			Message message = new Message();
			message.setId(Integer.parseInt(idStr[i]));
			message.setState(Integer.parseInt(state));
			messageService.updateMessage(message);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}
