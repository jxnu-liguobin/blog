package cn.edu.jxnu.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.edu.jxnu.blog.domin.Link;

/**
 * 友情连接dao接口
 */
@Repository
public interface LinkDao {

    /**
     * 查询所有友情链接信息
     * @return
     */
    public List<Link> getTotalData();

    /**
     * 分页查询
     * @param start
     * @param end
     * @return
     */
    public List<Link> listByPage(@Param("start") Integer start, @Param("end") Integer end);

    /**
     * 查询总记录数
     * @return
     */
    public Long getTotalCount();

    /**
     * 新增友情链接
     * @param link
     * @return
     */
    public Integer addLink(Link link);

    /**
     * 删除友情链接
     * @param id
     * @return
     */
    public Integer deleteLink(Integer id);

    /**
     * 更新友情链接
     * @param link
     * @return
     */
    public Integer updateLink(Link link);
}
