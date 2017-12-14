package cn.edu.jxnu.blog.dao;

import java.util.List;
import java.util.Map;

import cn.edu.jxnu.blog.domin.Notice;

/**
 * 
 * @author： liguobin
 * @Description： 公告CRUD
 * @时间： 2017-12-9 下午4:22:33
 * @version： V1.0
 * 
 */
public interface NoticeDao {
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
	List<Notice> listNotice(Map<String, Object> map);

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
