package cn.edu.jxnu.blog.commons;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @描述 可以用@RestController 写入对象。但是需要按api的指定格式
 * @功能 写入响应流
 * @author liguobin
 * 
 */
public class ResponseUtil {
	/**
	 * 向response对象写入数据
	 * 
	 * @param response
	 * @param obj
	 * @throws Exception
	 */
	public static void write(HttpServletResponse response, Object obj)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
		out.flush();
		out.close();
	}
}
