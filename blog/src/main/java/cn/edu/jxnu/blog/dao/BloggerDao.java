package cn.edu.jxnu.blog.dao;

import org.springframework.stereotype.Repository;

import cn.edu.jxnu.blog.domin.Blogger;

/**
 * @Description 博主dao接口
 */
@Repository //注册为持久层的bean
public interface BloggerDao {
    /**
     * 查询博主信息
     * @return
     */
    Blogger getBloggerData();
    /********************************************************/

    /**
     * 通过用户名查询博主信息
     * @param username
     * @return
     */
    Blogger getBloggerByName(String username);

    /**
     * 更新博主信息
     * @param blogger
     * @return
     */
    Integer updateBlogger(Blogger blogger);

}