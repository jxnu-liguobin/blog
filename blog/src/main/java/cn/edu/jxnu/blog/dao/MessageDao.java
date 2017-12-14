package cn.edu.jxnu.blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.edu.jxnu.blog.domin.Message;

/**
 * @Description 留言dao类
 */
@Repository
public interface MessageDao {

	/**
	 * 分页查询留言
	 * 
	 * @param map
	 * @return
	 */
	public List<Message> listByPage(Map<String, Object> map);

	/**
	 * 获取总留言数量
	 * 
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);

	/**
	 * 根据id查询留言
	 * 
	 * @param id
	 * @return
	 */
	public Message getById(Integer id);

	/**
	 * 添加留言
	 * 
	 * @param Message
	 * @return
	 */
	public Integer saveMessage(Message Message);

	/**
	 * 根据id删除留言
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteMessage(Integer id);

	/**
	 * 更新留言
	 * 
	 * @param message
	 * @return
	 */
	public Integer updateMessage(Message message);
}
