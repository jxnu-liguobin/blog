package cn.edu.jxnu.blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.edu.jxnu.blog.domin.Blog;

/**
 * @Description 博客Dao接口
 * 
 */
@Repository
public interface BlogDao {
	/**
	 * @Description 根据日期月份分组查询
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
	 * @Description 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);

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
	 * @Description 删除博客
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

	//
	// public Blog getPrevBlog(Integer id);
	//
	//
	// public Blog getNextBlog(Integer id);
}
