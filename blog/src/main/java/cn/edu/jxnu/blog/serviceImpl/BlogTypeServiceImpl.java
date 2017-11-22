package cn.edu.jxnu.blog.serviceImpl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.jxnu.blog.dao.BlogTypeDao;
import cn.edu.jxnu.blog.domin.BlogType;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.BlogTypeService;

/**
 * Created by xp on 2017/4/14.
 * @author xp
 * @Description 博客类别service接口实现类
 */
@Service
public class BlogTypeServiceImpl implements BlogTypeService{

    @Resource
    private BlogTypeDao blogTypeDao;

    public PageBean<BlogType> listByPage(PageBean<BlogType> pageBean) {
        //查询分页结果
        pageBean.setResult(blogTypeDao.listByPage(pageBean.getStart(),pageBean.getEnd()));
        //查询记录总数
        pageBean.setTotal(blogTypeDao.getTotal());
        return pageBean;
    }
    public Integer addBlogType(BlogType blogType) {
        return blogTypeDao.addBlogType(blogType);
    }

    public Integer updateBlogType(BlogType blogType) {
        return blogTypeDao.updateBlogType(blogType);
    }

    public Integer deleteBlogType(Integer id) {
        return blogTypeDao.deleteBlogType(id);
    }
	public List<BlogType> getBlogTypeData() {
		// TODO 自动生成的方法存根
		return blogTypeDao.getBlogTypeData();
	}
}