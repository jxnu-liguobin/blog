package cn.edu.jxnu.blog.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jxnu.blog.dao.MessageDao;
import cn.edu.jxnu.blog.domin.Message;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.MessageService;

/**
 * @Description 实现留言 service接口
 * @author liguobin
 * 
 */
@Service
public class MessageServiceImpl implements MessageService {
	@Resource
	MessageDao messageDao;

	@Override
	public PageBean<Message> listByPage(PageBean<Message> pageBean) {
		pageBean.getMap().put("start", pageBean.getStart());
		pageBean.getMap().put("end", pageBean.getEnd());
		pageBean.setResult(messageDao.listByPage(pageBean.getMap()));
		pageBean.setTotal(messageDao.getTotal(pageBean.getMap()));
		return pageBean;
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return messageDao.getTotal(map);
	}

	@Override
	public Message getById(Integer id) {
		return messageDao.getById(id);
	}

	@Transactional
	@Override
	public Integer saveMessage(Message Message) {
		return messageDao.saveMessage(Message);
	}

	@Transactional
	@Override
	public Integer deleteMessage(Integer id) {
		return messageDao.deleteMessage(id);
	}

	@Deprecated
	@Override
	public List<Message> getMessageData(Map<String, Object> map) {
		return messageDao.listByPage(map);
	}

	@Transactional
	@Override
	public Integer updateMessage(Message message) {
		return messageDao.updateMessage(message);
	}

	@Override
	public List<Message> listByMessages(Map<String, Object> map) {
		return messageDao.listByPage(map);
	}

}
