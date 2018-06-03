package cn.edu.jxnu.blog.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.jxnu.blog.Application;
import cn.edu.jxnu.blog.domin.Great;
import cn.edu.jxnu.blog.service.GreatService;
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
public class TestGreatServiceImpl {
	@Autowired
	PictureService pictureService;

	@Autowired
	private GreatService greatService;

	@Test
	public void testIsClick() {
		greatService.isClick("111.25.5.1", 5);
	}

	@Test
	public void testAdd() {
		greatService.saveGreat(new Great(null, "195.5.5.8", 55));
	}

	@Test
	public void testDelete() {
		greatService.delGreat(1);
	}

}
