package cn.edu.jxnu.blog.serviceImpl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.jxnu.blog.dao.GreatDao;
import cn.edu.jxnu.blog.domin.Great;
import cn.edu.jxnu.blog.service.GreatService;

/**
 * 
 * @author： liguobin
 * @Description： 点赞service实现
 * @时间： 2017-12-15 下午12:49:38
 * @version： V1.0
 * 
 */
@Service
public class GreatServiceImpl implements GreatService {

	@Autowired
	private GreatDao greatDao;

	@Override
	public Integer saveGreat(Great great) {
		return greatDao.addGreat(great);
	}

	@Override
	public Integer delGreat(Integer id) {
		return greatDao.deleteGreat(id);
	}

	@Override
	public Great isClick(@Param("userIp") String userIp,
			@Param("imageId") Integer imageId) {
		return greatDao.getByUserIpAndImageId(userIp, imageId);
	}

}
