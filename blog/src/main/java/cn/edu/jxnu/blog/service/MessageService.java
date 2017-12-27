package cn.edu.jxnu.blog.service;

import java.util.List;
import java.util.Map;

import cn.edu.jxnu.blog.domin.Message;
import cn.edu.jxnu.blog.domin.PageBean;

/**
 * @Description 留言service接口 修改留言
 * 
 */
public interface MessageService {
	/**
	 * 分页查询留言信息
	 * 
	 * @param pageBean
	 * @return
	 */
	public PageBean<Message> listByPage(PageBean<Message> pageBean);

	/**
	 * 获取总记录数目
	 * 
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);

	/**
	 * 根据id查询留言信息
	 * 
	 * @param id
	 * @return
	 */
	public Message getById(Integer id);

	/**
	 * 添加留言信息
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
	 * 查询所有留言消息
	 * 
	 * @param map
	 * @return
	 */
	public List<Message> getMessageData(Map<String, Object> map);

	/**
	 * 更新留言
	 * 
	 * @param message
	 * @return
	 */
	public Integer updateMessage(Message message);

	/**
	 * 前台专用分页，懒加载
	 * 
	 * @param map
	 * @return
	 */
	public List<Message> listByMessages(Map<String, Object> map);
}
