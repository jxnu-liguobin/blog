package cn.edu.jxnu.blog;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edu.jxnu.blog.domin.Blog;
import cn.edu.jxnu.blog.service.BlogService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 每1小时，提交一次连接到百度爬虫
 * 
 * @author： liguobin
 * @Description：
 * @时间： 2018-1-2 下午7:59:19
 * @version： V1.0
 * 
 */
@Component
public class ThreadForPushTo implements Runnable,ServletContextListener {

	private static ApplicationContext applicationContext;
	private  static BlogService blogService;
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ThreadForPushTo.class);
	//通用文章链接前缀
	private static String STATE_URL = "https://www.dreamylost.cn/blog/articles/";
	// 网站的服务器连接
	private static String URL = "http://data.zz.baidu.com/urls?site=www.dreamylost.cn&token=SCgNccADFrd7M0W1";
	@Override
	public void run() {
		synchronized (this) {
			while (true) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				log.info("进入线程");
				// 获取所有的链接
				List<Blog> list = blogService.listBlog(new HashMap<String, Object>());
				// 不再需要同步
				StringBuffer lists = new StringBuffer(list.size());
				for (Blog blog : list) {
					lists.append(STATE_URL + blog.getId() + ',');
				}
				// 去除最后一个,
				String li = lists.toString().substring(0, lists.length() - 1);
				// 创建文章个数个字符串数组
				String[] listForParame = new String[list.size()];
				// 得到文章url数组
				listForParame = li.split(",");
				// 像百度爬虫发送url
				String json = PushLinkToBaidu.Post(URL, listForParame);
				// 解析JSON， 转换为json对象
				JSONObject jsonObject = JSONArray.parseObject(json); 
				int successLength = jsonObject.getInteger("success");
				if (successLength > 0) {
					 // 当天剩余可以退送
					int canPush = jsonObject.getInteger("remain");
					// 由于不是本站url而未处理的url列表
					JSONArray jsonArray_not_same_site = jsonObject.getJSONArray("not_same_site");
					// 由于不是本站url而未处理的url列表
					JSONArray jsonArray_not_valid = jsonObject.getJSONArray("not_valid");
					log.info("成功提交->" + successLength + "条url 剩余可以提交->"+ canPush);
					//正确提交的时候并不一定有这个返回参数
					if (jsonArray_not_same_site!=null) {
						log.info("由于不是本站url而未处理的url列表："+ jsonArray_not_same_site.toJSONString());
					}
					if (jsonArray_not_valid !=null ) {
						log.info("不合法的url列表："+ jsonArray_not_valid.toJSONString());
					}
				} else if (jsonObject.getInteger("error") > 0) {
					String jsonArray_message = jsonObject.getString("message");
					log.info("提交失败->" + successLength + "条url 错误描述->"+ jsonArray_message);
				}
				try {
					// 睡眠1小时
					TimeUnit.HOURS.sleep(1); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(application);
		ThreadForPushTo.blogService = applicationContext.getBean(BlogService.class); //初始化的时候得到bean
		//得到文章列表
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
