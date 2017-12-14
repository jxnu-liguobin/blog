package cn.edu.jxnu.blog.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jxnu.blog.dao.BloggerDao;
import cn.edu.jxnu.blog.domin.Blogger;
import cn.edu.jxnu.blog.service.BloggerService;

/**
 * @Description 博主service实现类
 */
@Service
public class BloggerServiceImpl implements BloggerService {

	@Resource
	private BloggerDao bloggerDao;

	@Override
	public Blogger getBloggerData() {
		return bloggerDao.getBloggerData();
	}

	@Override
	public Blogger getBloggerByName(String username) {
		return bloggerDao.getBloggerByName(username);
	}

	@Transactional
	@Override
	public Integer updateBlogger(Blogger blogger) {
		return bloggerDao.updateBlogger(blogger);
	}
}
