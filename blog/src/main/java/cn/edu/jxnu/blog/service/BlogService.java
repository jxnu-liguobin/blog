package cn.edu.jxnu.blog.service;

import java.util.List;
import java.util.Map;

import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.PageBean;

/**
 * @Description 博客Service接口
 */
public interface BlogService {

	/**
	 * @Description 根据日期月份分组查询
	 * 
	 * @return
	 */
	public List<Blog> countList();

	/**
	 * @Description 分页查询博客
	 * @param map
	 * @return
	 */
	public List<Blog> listBlog(Map<String, Object> map);

	/**
	 * @Description 分页查询博客
	 * @param title
	 * @param pageBean
	 * @return
	 */
	public PageBean<Blog> listBlog(String title, PageBean<Blog> pageBean);

	/**
	 * @Description 根据博客类型的id查询该类型下的博客数量
	 * @param typeId
	 * @return
	 */
	public Integer getBlogByTypeId(Integer typeId);

	/**
	 * @Description 添加博客
	 * @param blog
	 * @return
	 */
	public Integer saveBlog(Blog blog);

	/**
	 * @Description 更新博客
	 * @param blog
	 * @return
	 */
	public Integer updateBlog(Blog blog);

	/**
	 * @Description 通过id删除博客
	 * @param id
	 * @return
	 */
	public Integer deleteBlog(Integer id);

	/**
	 * @Description 通过id获取博客
	 * @param id
	 * @return
	 */
	public Blog getById(Integer id);

	/**
	 * @Description 获取总数
	 * @param map
	 * @return
	 */
	long getTotal(Map<String, Object> map);
	//
	// public Blog getPrevBlog(Integer id);
	//
	// public Blog getNextBlog(Integer id);
}