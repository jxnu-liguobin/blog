package cn.edu.jxnu.blog.service;


import java.util.List;
import java.util.Map;

import cn.edu.jxnu.blog.domin.Comment;
import cn.edu.jxnu.blog.domin.PageBean;

/**
 * @Description 评论service接口
 */
public interface CommentService {
    /**
     * 分页查询评论信息
     * @param pageBean
     * @return
     */
    public PageBean<Comment> listByPage(PageBean<Comment> pageBean);

    /**
     * 获取总记录数目
     * @param map
     * @return
     */
    public Long getTotal(Map<String ,Object> map);

    /**
     * 根据id查询评论信息
     * @param id
     * @return
     */
    public Comment getById(Integer id);

    /**
     * 添加评论信息
     * @param comment
     * @return
     */
    public Integer saveComment(Comment comment);

    /**
     * 根据id删除评论
     * @param id
     * @return
     */
    public Integer deleteComment(Integer id);

    /**
     * 更改评论审核状态
     * @param comment
     * @return
     */
    public Integer updateComment(Comment comment);

    /**
     * 删除评论信息通过博客id
     * @param blogId
     * @return
     */
    public Long deleteCommentByBlogId(Integer blogId);

    /**
     * 查询所有评论消息
     * @param map
     * @return
     */
    public List<Comment> getCommentData(Map<String,Object> map);
}
