package cn.edu.jxnu.blog.service;

import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.domin.Picture;

/**
 * 
 * @author： liguobin
 * @Description： 相册图片服务接口
 * @时间： 2017-12-15 上午11:27:04
 * @version： V1.0
 * 
 */
public interface PictureService {
	/**
	 * 查询所有图片信息，按照点赞排行
	 * 
	 * @return
	 */
//	List<Picture> getTotalData();

	/**
	 * 查询总记录数
	 * 
	 * @return
	 */
	Long getTotalCount();

	/**
	 * 新增图片
	 * 
	 * @param Picture
	 * @return
	 */
	Integer addPicture(Picture picture);

	/**
	 * 删除图片
	 * 
	 * @param id
	 * @return
	 */
	Integer deletePicture(Integer id);

	/**
	 * 更新图片
	 * 
	 * @param Picture
	 * @return
	 */
	Integer updatePicture(Picture picture);

	/**
	 * 分页查询
	 * 
	 * @param pageBean
	 * @return
	 */

	PageBean<Picture> listByPage(PageBean<Picture> pageBean);

	/**
	 * 根据id获取图片
	 * 
	 * @param id
	 * @return
	 */
	Picture getPictureByid(Integer id);
}
