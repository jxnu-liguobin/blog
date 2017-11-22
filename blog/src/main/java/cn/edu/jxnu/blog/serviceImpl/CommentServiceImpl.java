package cn.edu.jxnu.blog.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.jxnu.blog.dao.CommentDao;
import cn.edu.jxnu.blog.domin.Comment;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.CommentService;

/**
 * 实现评论service接口
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;


    public PageBean<Comment> listByPage(PageBean<Comment> pageBean) {
        pageBean.getMap().put("start",pageBean.getStart());
        pageBean.getMap().put("end",pageBean.getEnd());
        pageBean.setResult(commentDao.listByPage(pageBean.getMap()));
        pageBean.setTotal(commentDao.getTotal(pageBean.getMap()));
        return pageBean;
    }


    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }


    public Comment getById(Integer id) {
        return commentDao.getById(id);
    }


    public Integer saveComment(Comment comment) {
        return commentDao.saveComment(comment);
    }


    public Integer deleteComment(Integer id) {
        return commentDao.deleteComment(id);
    }


    public Integer updateComment(Comment comment) {
        return commentDao.updateComment(comment);
    }


    public Long deleteCommentByBlogId(Integer blogId) {
        return commentDao.deleteCommentByBlogId(blogId);
    }

    public List<Comment> getCommentData(Map<String, Object> map) {
        return commentDao.listByPage(map);
    }
}
