package cn.edu.jxnu.blog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.jxnu.blog.domin.Notice;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.NoticeService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/notice")
public class NoticeAdminController {

	@Autowired
	private NoticeService noticeService;

	/**
	 * @description 分页查询
	 * @return
	 */
	@RequestMapping("/list")
	public Object listNotice(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "limit", required = false) String limit) {
		/**
		 * 这里没有使用pageBean所有需要令 limit(start,end) 分页start+1~end+start
		 */
		// 定义分页bean
		PageBean<Notice> pageBean = new PageBean<Notice>(
				Integer.parseInt(page), Integer.parseInt(limit));
		// 拿到分页结果已经记录总数的pageBean
		pageBean = noticeService.listNotice(pageBean);
		// 使用阿里巴巴的fastJson创建JSONObject
		// 通过fastJson序列化list为jsonArray
		String jsonArray = JSON.toJSONString(pageBean.getResult());
		JSONArray array = JSONArray.parseArray(jsonArray);
		// 使用阿里巴巴的fastJson创建JSONObject
		JSONObject result = new JSONObject();
		// 将序列化结果放入json对象中
		result.put("data", array);
		result.put("code", 0);
		result.put("count", pageBean.getTotal());

		return result;

	}

	/**
	 * @description 添加公告
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveNotice(Notice notice) {
		int resultTotal = 0; // 接收返回结果记录数
		if (notice.getId() == null) { // 说明是第一次插入
			resultTotal = noticeService.saveNotice(notice);
		} else { // 有id表示修改
			resultTotal = noticeService.updateNotice(notice);
		}
		JSONObject result = new JSONObject();
		if (resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result.toJSONString();

	}

	/**
	 * @description 删除公告
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteNotice(
			@RequestParam(value = "ids", required = false) String ids) {
		// 分割字符串得到id数组
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		int j = 0;
		for (int i = 0; i < idsStr.length; i++) {
			int id = Integer.parseInt(idsStr[i]);
			noticeService.deleteByNoticeId(id);
			j++;
		}
		// 全部删除成功
		if (j == idsStr.length) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result.toJSONString();

	}

}
