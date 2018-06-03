package cn.edu.jxnu.blog.test.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.jxnu.blog.Application;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.domin.Picture;
import cn.edu.jxnu.blog.service.PictureService;

/**
 * 
 * @author： liguobin
 * @Description： 图片接口单元测试类
 * @时间： 2017-12-15 下午12:47:41
 * @version： V1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestPictureServiceImpl {
	@Autowired
	PictureService pictureService;

	@Test
	public void testAdd() {
		Picture picture = new Picture(null, "192.5.5.54", "admin", new Date(),
				0, "图片名称");
		for (int j = 0; j < 10; j++) {
			pictureService.addPicture(picture);
		}
	}

	@Test
	public void testDelete() {
		int i = pictureService.deletePicture(1);
		System.out.println(i);
	}

	@Test
	public void testUpdate() {
		Picture picture = new Picture(2, "http:55.25.5.2.1", "admin",
				new Date(), 0, "图片");
		int i = pictureService.updatePicture(picture);
		System.out.println(i);

	}

	@Test
	public void testListByPage() {
		@SuppressWarnings("unused")
		Map<String, Object> map = new HashMap<String, Object>();
		PageBean<Picture> pageBean = pictureService
				.listByPage(new PageBean<Picture>(1, 5));
		List<Picture> list = pageBean.getResult();
		for (Picture picture : list) {
			System.out.println(picture.toString());
		}

	}
//
//	@Test
//	public void getAllPicture() {
//		/List<Picture> list = pictureService.getTotalData();
//		for (Picture picture : list) {
//			System.out.println(picture.toString());
//		}
//	}

}
