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
	 * 
	 * @param pageBean
	 * @return
	 */
	PageBean<Comment> listByPage(PageBean<Comment> pageBean);

	/**
	 * 获取总记录数目
	 * 
	 * @param map
	 * @return
	 */
	Long getTotal(Map<String, Object> map);

	/**
	 * 根据id查询评论信息
	 * 
	 * @param id
	 * @return
	 */
	Comment getById(Integer id);

	/**
	 * 添加评论信息
	 * 
	 * @param comment
	 * @return
	 * @throws IllegalAccessException
	 */
	Integer saveComment(Comment comment);

	/**
	 * 根据id删除评论
	 * 
	 * @param id
	 * @return
	 */
	Integer deleteComment(Integer id);

	/**
	 * 更改评论审核状态
	 * 
	 * @param comment
	 * @return
	 */
	Integer updateComment(Comment comment);

	/**
	 * 删除评论信息通过博客id
	 * 
	 * @param blogId
	 * @return
	 */
	Long deleteCommentByBlogId(Integer blogId);

	/**
	 * 查询所有评论消息----仅仅这样直接输出不行。既需要分页，也需要条件查询
	 * 
	 * @param map
	 * @return
	 */
	@Deprecated
	List<Comment> getCommentData(Map<String, Object> map);

	/**
	 * 根据blogId查询所有评论 state=1的
	 * 
	 * @param blogId
	 * @return
	 */
	List<Comment> queryCommentsByBlogId(Integer blogId);

	/**
	 * 复杂查询，分页
	 * 
	 * @param map
	 * @return
	 */
	List<Comment> listComment(Map<String, Object> map);

}
