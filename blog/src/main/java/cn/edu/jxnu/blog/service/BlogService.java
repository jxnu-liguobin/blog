package cn.edu.jxnu.blog.service;
import java.util.List;
import java.util.Map;

import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.domin.PageBean;

/**
 * @author xp
 * @Description 博客Service接口
 */
public interface BlogService {

	/**
	 * 根据日期月份分组查询
	 * @return
	 */
	public List<Blog> countList();
	
    // 分页查询博客
    public List<Blog> listBlog(Map<String,Object> map);

    // 分页查询博客
    public PageBean<Blog> listBlog(String title,PageBean<Blog> pageBean);

    // 根据博客类型的id查询该类型下的博客数量
    public Integer getBlogByTypeId(Integer typeId);

    //添加博客
    public Integer saveBlog(Blog blog);

    //更新博客
    public Integer updateBlog(Blog blog);

    //通过id删除博客
    public Integer deleteBlog(Integer id);

    //通过id获取博客
    public Blog getById(Integer id);

    long getTotal(Map<String, Object> map);

	public Blog getPrevBlog(Integer id);

	public Blog getNextBlog(Integer id);
}