package cn.edu.jxnu.blog.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.jxnu.blog.dao.PictureDao;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.domin.Picture;
import cn.edu.jxnu.blog.service.PictureService;


@Service
public class PictureServiceImpl implements PictureService {

	@Autowired
	private PictureDao pictureDao;

//	@Override
//	public List<Picture> getTotalData() {
//		return pictureDao.getTotalData();
//	}

	@Override
	public PageBean<Picture> listByPage(PageBean<Picture> pageBean) {

		pageBean.getMap().put("start", pageBean.getStart());
		pageBean.getMap().put("end", pageBean.getEnd());
		pageBean.setResult(pictureDao.listByPage(pageBean.getMap()));
		pageBean.setTotal(pictureDao.getTotalCount());
		return pageBean;
	}

	@Override
	public Long getTotalCount() {
		return pictureDao.getTotalCount();
	}

	@Override
	public Integer addPicture(Picture picture) {
		return pictureDao.addPicture(picture);
	}

	@Override
	public Integer deletePicture(Integer id) {
		return pictureDao.deletePicture(id);
	}

	@Override
	public Integer updatePicture(Picture picture) {
		return pictureDao.updatePicture(picture);
	}

	@Override
	public Picture getPictureByid(Integer id) {
		return pictureDao.getPictureById(id);
	}

}
