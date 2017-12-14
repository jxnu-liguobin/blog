package cn.edu.jxnu.blog.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jxnu.blog.dao.BlogDao;
import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.BlogService;

/**
 * @Description 博客Service实现类
 * 
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource
	private BlogDao blogDao;

	public PageBean<Blog> listBlog(String title, PageBean<Blog> pageBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询条件
		map.put("title", title);
		// 总记录放入pageBean
		pageBean.setTotal(blogDao.getTotal(map));
		map.put("start", pageBean.getStart());
		map.put("end", pageBean.getEnd());
		// 把分页结果放入pageBean
		pageBean.setResult(blogDao.listBlog(map));
		return pageBean;
	}

	public List<Blog> listBlog(Map<String, Object> map) {
		return blogDao.listBlog(map);
	}

	public Integer getBlogByTypeId(Integer typeId) {
		return blogDao.getBlogByTypeId(typeId);
	}

	public Integer saveBlog(Blog blog) {
		return blogDao.saveBlog(blog);
	}

	@Transactional
	public Integer updateBlog(Blog blog) {
		return blogDao.updateBlog(blog);
	}

	@Transactional
	public Integer deleteBlog(Integer id) {
		return blogDao.deleteBlog(id);
	}

	public Blog getById(Integer id) {
		return blogDao.getById(id);
	}

	public long getTotal(Map<String, Object> map) {
		return blogDao.getTotal(map);
	}

	@Override
	public List<Blog> countList() {
		return blogDao.countList();
	}
	//
	// @Override
	// public Blog getPrevBlog(Integer id) {
	// return blogDao.getPrevBlog(id);
	// }
	//
	// // 添加一个上一篇，下一篇方法
	//
	// @Override
	// public Blog getNextBlog(Integer id) {
	// return blogDao.getNextBlog(id);
	// }

}