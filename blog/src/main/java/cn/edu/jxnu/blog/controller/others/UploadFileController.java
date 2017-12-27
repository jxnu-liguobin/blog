package cn.edu.jxnu.blog.controller.others;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.domin.Picture;
import cn.edu.jxnu.blog.service.PictureService;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description 文件上传
 * @author liguobin
 * 
 */
@RestController
public class UploadFileController {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(UploadFileController.class);
	@Autowired
	private PictureService pictureService;

	@RequestMapping(value = "/uploadFile")
	public String uploadFile(
			HttpServletRequest request,
			@Param("file") MultipartFile file,
			@RequestParam(value = "flag", required = false) Integer flag,
			@RequestParam(value = "isUploads", required = false) String isUploads)
			throws IOException {
		InputStream is = file.getInputStream();
		BufferedImage bi = ImageIO.read(is);
		int width = bi.getWidth();
		if (width > 1920) {
			Map<String, Object> map = new HashMap<>();
			map.put("code", 1);// 0表示成功，1失败
			map.put("msg", "图片宽不能大于1920px");// 提示消息
			String result = new JSONObject(map).toString();
			return result;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String res = sdf.format(new Date())
				+ UUID.randomUUID().toString().substring(21); // 日期
		// 服务器上使用 tomcat映射
		// String rootPath =
		// request.getServletContext().getRealPath("/uploads/");// target的目录
		// 本地使用
		String rootPath = "C:/web/uploads/";
		// 原始名称
		String originalFilename = file.getOriginalFilename();
		// 新的文件名称
		String newFileName = res
				+ originalFilename.substring(originalFilename.lastIndexOf("."));
		/*
		 * // 创建年月文件夹 Calendar date = Calendar.getInstance(); File dateDirs =
		 * new File(date.get(Calendar.YEAR) + File.separator +
		 * (date.get(Calendar.MONTH) + 1));
		 */
		// 新文件
		/*
		 * File newFile = new File(rootPath + File.separator + dateDirs +
		 * File.separator + newFileName);
		 */
		File newFile = new File(rootPath + File.separator + File.separator
				+ newFileName);
		// 判断目标文件所在的目录是否存在
		if (!newFile.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			newFile.getParentFile().mkdirs();
		}
		log.debug("文件保存：" + newFile);
		// 将内存中的数据写入磁盘
		file.transferTo(newFile);
		// 完整的url 因为文章详情页面属第三级目录，不推荐这么写死。
		String fileUrl = null;
		if (flag != null) {
			fileUrl = "../uploads/" + newFileName;
		} else {
			fileUrl = "../../uploads/" + newFileName;
		}
		if (isUploads != null) {
			// 专门处理图片上传
			Blogger blogger = (Blogger) SecurityUtils.getSubject().getSession()
					.getAttribute("currentUser");
			Picture picture = new Picture(null, fileUrl, blogger.getUserName(),
					new Date(), 0, newFileName);
			pictureService.addPicture(picture);

		}
		log.debug("访问路径：" + fileUrl);
		/*
		 * String fileUrl = "/uploads/" + date.get(Calendar.YEAR) + "/" +
		 * (date.get(Calendar.MONTH) + 1) + "/" + newFileName;
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map.put("code", 0);// 0表示成功，1失败
		map.put("msg", "上传成功");// 提示消息
		map.put("data", map2);
		map2.put("src", fileUrl);// 图片url
		map2.put("title", newFileName);// 图片名称，这个会显示在输入框里
		String result = new JSONObject(map).toString();
		return result;
	}
}