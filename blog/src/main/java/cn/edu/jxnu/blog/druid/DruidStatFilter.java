package cn.edu.jxnu.blog.druid;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.springframework.stereotype.Component;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 
 * @author： liguobin
 * @Description： druid servlet
 * @时间： 2017-12-26 下午12:37:00
 * @version： V1.0
 * 
 */
@Component
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", initParams = { @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*") // 忽略资源
})
public class DruidStatFilter extends WebStatFilter {

}