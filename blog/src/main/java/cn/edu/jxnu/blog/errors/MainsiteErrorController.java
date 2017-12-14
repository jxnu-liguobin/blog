package cn.edu.jxnu.blog.errors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 1、错误处理控制器，根据错误返回码进行处理，并不能直接返回静态页面 但是可以通过重定向
 * @author liguobin
 */
@Controller
public class MainsiteErrorController implements ErrorController {

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
			return "redirect:/405";
		} else {
			return "redirect:/404";
		}
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}