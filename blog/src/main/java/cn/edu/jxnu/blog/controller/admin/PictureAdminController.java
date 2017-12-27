package cn.edu.jxnu.blog.controller.admin;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.domin.Picture;
import cn.edu.jxnu.blog.service.PictureService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author： liguobin
 * @Description： 图片后台控制器
 * @时间： 2017-12-15 下午1:01:25
 * @version： V1.0
 * 
 */
@RestController
@RequestMapping("/admin/picture")
public class PictureAdminController {
	
	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(PictureAdminController.class);
	@Autowired
	private PictureService pictureService;

	/**
	 * @description 分页查询
	 * @return
	 */
	@RequestMapping("/list")
	public String listNotice(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "limit", required = false) String limit) {
		log.info("当前照片管理页面。。。");
		// 定义分页bean
		PageBean<Picture> pageBean = new PageBean<Picture>(
				Integer.parseInt(page), Integer.parseInt(limit));
		// 拿到分页结果已经记录总数的pageBean
		pageBean = pictureService.listByPage(pageBean);
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

		return result.toJSONString();

	}

	/**
	 * @description 添加相片
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveNotice(Picture picture) {
		log.info("当前请求添加图片。。。");
		int resultTotal = 0; // 接收返回结果记录数
		if (picture.getId() == null) { // 说明是第一次插入
			resultTotal = pictureService.addPicture(picture);
		} else { // 有id表示修改
			resultTotal = pictureService.updatePicture(picture);
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
	 * @description 删除图片
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteNotice(
			@RequestParam(value = "ids", required = false) String ids) {
		log.info("当前请求删除图片。。。");
		// 分割字符串得到id数组
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();
		int j = 0;
		for (int i = 0; i < idsStr.length; i++) {
			int id = Integer.parseInt(idsStr[i]);
			pictureService.deletePicture(id);
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
