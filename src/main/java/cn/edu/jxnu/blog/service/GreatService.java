package cn.edu.jxnu.blog.service;

import org.apache.ibatis.annotations.Param;

import cn.edu.jxnu.blog.domin.Great;

/**
 * 
 * @author： liguobin
 * @Description： 点赞服务接口
 * @时间： 2017-12-15 上午11:43:33
 * @version： V1.0
 * 
 */
public interface GreatService {
	/**
	 * 新增图片
	 * 
	 * @param Picture
	 * @return
	 */
	public Integer saveGreat(Great great);

	/**
	 * 删除图片
	 * 
	 * @param id
	 * @return
	 */
	public Integer delGreat(Integer id);

	/**
	 * 查看是否有imageId------>用户ip的关系
	 * 
	 * @param userIp
	 * @param imageId
	 * @return
	 */
	public Great isClick(@Param("userIp") String userIp,
			@Param("imageId") Integer imageId);
	/**
	 * 根据imageid 删除所有对应的记录
	 * @param id
	 * @return
	 */
	public Integer deleteByImageId(Integer id);

}
