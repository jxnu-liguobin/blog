package cn.edu.jxnu.blog.service;

import java.util.List;

import cn.edu.jxnu.blog.domin.Notice;
import cn.edu.jxnu.blog.domin.PageBean;

/**
 * 
 * @author： liguobin
 * @Description： 公告服务层接口
 * @时间： 2017-12-9 下午4:30:22
 * @version： V1.0
 * 
 */
public interface NoticeService {
	/**
	 * @Description 根据id删除公告
	 * @return
	 */
	Integer deleteByNoticeId(Integer id);

	/**
	 * @Description 保存公告
	 * @param notice
	 * @return
	 */
	Integer saveNotice(Notice notice);

	/**
	 * @Description 分页获取公告 后台用的api
	 * @param map
	 * @return
	 */
	PageBean<Notice> listNotice(PageBean<Notice> pageBean);

	/**
	 * @Description 修改公告
	 * @param notice
	 * @return
	 */
	Integer updateNotice(Notice notice);

	/**
	 * @Description 查询公告总数
	 * @return
	 */
	Long getTotal();

	/**
	 * @Description 获取所以公告数据 前台使用的api
	 * @return
	 */
	List<Notice> getAllNotices();

	/**
	 * @Description 根据id获取公告信息
	 * @return
	 */
	Notice getNoticeById(Integer id);

}
