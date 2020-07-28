package cn.edu.jxnu.blog.errors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 1、错误处理控制器，根据错误返回码进行处理，并不能直接返回静态页面 但是可以通过重定向
 *              也可以使用SpringBoot的ControllerAdvice建言 这里只是简单判断405,404
 * @author liguobin
 */
@Controller
public class MainsiteErrorController implements ErrorController {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(MainsiteErrorController.class);
	private static final String ERROR_PATH = "/error";

	/**
	 * url错误
	 * 
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH)
	public String handleError(HttpServletResponse httpServletResponse) {
		if (httpServletResponse.getStatus() == 405) {
			log.info("当前状态码：405---->405.html");
			return "errors/405";
		} else {
			log.info("当前状态码：404---->404.html");
			return "errors/404";
		} 
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}