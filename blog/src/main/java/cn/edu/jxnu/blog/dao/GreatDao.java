package cn.edu.jxnu.blog.dao;

import org.apache.ibatis.annotations.Param;

import cn.edu.jxnu.blog.domin.Great;

/**
 * 
 * @author： liguobin
 * @Description： 点赞dao类
 * @时间： 2017-12-15 上午11:27:04
 * @version： V1.0
 * 
 */
public interface GreatDao {

	/**
	 * 新增图片
	 * 
	 * @param Picture
	 * @return
	 */
	public Integer addGreat(Great great);

	/**
	 * 删除图片
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteGreat(Integer id);

	/**
	 * 查看是否有imageId------>用户ip的关系
	 * 
	 * @param userIp
	 * @param imageId
	 * @return
	 */
	public Great getByUserIpAndImageId(@Param("userIp") String userIp,
			@Param("imageId") Integer imageId);
}
