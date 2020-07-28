package cn.edu.jxnu.blog.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.jxnu.blog.Application;
import cn.edu.jxnu.blog.dao.NoticeDao;
import cn.edu.jxnu.blog.domin.Notice;
import cn.edu.jxnu.blog.service.NoticeService;

/**
 * 
 * @author： liguobin
 * @Description： 公共的CRUD+分页+遍历接口
 * @时间： 2017-12-9 下午6:00:01
 * @version： V1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestNoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Autowired
	NoticeService noticeService;

	@Test
	public void getNoticeById() throws Exception {
		Notice notice = noticeDao.getNoticeById(1);
		System.out.println("打印：" + notice.toString());
	}

	@Test
	public void TestNoticeUpdate() {
		Notice notice = noticeDao.getNoticeById(1);
		notice.setContent("饿呢！");
		noticeService.updateNotice(notice);
		System.out.println(noticeDao.getNoticeById(1).toString());
	}

	@Test
	public void TestNoticeAdd() {
		Notice notice = new Notice();
		notice.setContent("很重要公告");
		notice.setLevel(2);
		notice.setNoticeDate(new Date());
		notice.setNoticePublisher("自己");
		noticeService.saveNotice(notice);
	}

	@Test
	public void TestNoticeDelete() {
		int i = noticeService.deleteByNoticeId(5);
		System.out.println(i == 1); // true;
	}

	@Test
	public void getAllNotices() {
		List<Notice> list = noticeService.getAllNotices();
		for (Notice notice : list) {
			System.out.println(notice.toString());
		}
	}
}
