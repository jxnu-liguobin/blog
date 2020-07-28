package cn.edu.jxnu.blog.controller.admin;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.jxnu.blog.domin.Link;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.LinkService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description 友情链接控制层
 */
@RestController
@RequestMapping(value = "/admin/link")
public class LinkAdminController {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(LinkAdminController.class);
	@Resource
	private LinkService linkService;

	// 分页显示
	@RequestMapping(value = "/list")
	public String listByPage(@RequestParam("page") String page,
			@RequestParam("limit") String limit) throws Exception {
		log.info("当前请求友情链接管理页面。。。");
		PageBean<Link> pageBean = new PageBean<Link>(Integer.parseInt(page),
				Integer.parseInt(limit));
		pageBean = linkService.listByPage(pageBean);
		// 得到分页数据
		String jsonStr = JSONObject.toJSONString(pageBean.getResult());
		JSONArray jsonArray = JSON.parseArray(jsonStr);
		JSONObject result = new JSONObject();
		// 保存到json中
		result.put("count", pageBean.getTotal());
		result.put("data", jsonArray);
		result.put("code", 0);
		// 写入输出流，返回给客户端
		return result.toJSONString();
	}

	// 新增or修改友情链接
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveLink(Link link) throws Exception {
		log.info("当前请求更新友情链接。。。");

		int totalCount = 0;
		// 修改操作
		if (link.getId() != null) {
			totalCount = linkService.updateLink(link);
		} else {
			// 新增操作
			totalCount = linkService.addLink(link);
		}
		// 同样是返回json数据
		JSONObject result = new JSONObject();
		if (totalCount > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result.toJSONString();
	}

	// 删除友情链接
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteLink(String ids) throws Exception {
		log.info("当前请求删除友情链接。。。");
		// 将ajax提交过来的进行处理，得到所有需要删除id，循环删除。
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			linkService.deleteLink(Integer.parseInt(idArray[i]));
		}
		// 返回结果
		JSONObject result = new JSONObject();
		result.put("success", true);
		return result.toJSONString();
	}

}
