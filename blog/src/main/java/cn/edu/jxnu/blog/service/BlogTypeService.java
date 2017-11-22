package cn.edu.jxnu.blog.service;

import java.util.List;

import cn.edu.jxnu.blog.domin.BlogType;
import cn.edu.jxnu.blog.domin.PageBean;

/**
 * @Description 博客类别service接口
 */
public interface BlogTypeService {

	// 分页查询
	PageBean<BlogType> listByPage(PageBean<BlogType> pageBean);

	// 添加博客类别
	public Integer addBlogType(BlogType blogType);

	// 更新博客类别
	public Integer updateBlogType(BlogType blogType);

	// 删除博客类别
	public Integer deleteBlogType(Integer id);

	// 获取博客类型列表
	List<BlogType> getBlogTypeData();

}