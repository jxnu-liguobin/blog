package cn.edu.jxnu.blog.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.jxnu.blog.dao.LinkDao;
import cn.edu.jxnu.blog.domin.Link;
import cn.edu.jxnu.blog.domin.PageBean;
import cn.edu.jxnu.blog.service.LinkService;

/**
 * 友情链接service实现类
 */
@Service
public class LinkServiceImpl implements LinkService{

    @Resource
    private LinkDao linkDao;//注入mapper dao接口,由spring提供bean，mapper代理

    @Override
    public List<Link> getTotalData() {
        return linkDao.getTotalData();
    }

    @Override
    public PageBean<Link> listByPage(PageBean<Link> pageBean) {
    	//分页，将分页数据和总数量保存到pageBean,并返回
        pageBean.setResult(linkDao.listByPage(pageBean.getStart(),pageBean.getEnd()));
        pageBean.setTotal(linkDao.getTotalCount());
        return pageBean;
    }
    //得到总数量
    @Override
    public Long getTotalCount() {
        return linkDao.getTotalCount();
    }
    //添加连接
    @Override
    public Integer addLink(Link link) {
        return linkDao.addLink(link);
    }
    //删除连接
    @Override
    public Integer deleteLink(Integer id) {
        return linkDao.deleteLink(id);
    }
    //更新连接
    @Override
    public Integer updateLink(Link link) {
        return linkDao.updateLink(link);
    }
}
