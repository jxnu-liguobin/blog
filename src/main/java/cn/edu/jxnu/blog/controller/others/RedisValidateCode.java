package cn.edu.jxnu.blog.controller.others;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.jxnu.blog.commons.VaildataCodeUtil;

/**
 * 
 * @author： liguobin
 * @Description： 使用redis保存验证码
 * @时间： 2017-12-21 下午6:59:15
 * @version： V1.0
 * 
 */
@Controller
@RequestMapping("/blog")
public class RedisValidateCode {
	
	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(RedisValidateCode.class);
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@RequestMapping(value = "/redisValidateCode")
	public String validateCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 将验证码以<key,value>形式缓存到redis
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		VaildataCodeUtil vCode = new VaildataCodeUtil(120, 38, 5, 100);
		// 60s 使用redis代替session保存
		log.info("生成了验证码并保存在redis中："+vCode.getCode());
		redisTemplate.opsForValue().set("code", vCode.getCode(), 60,
				TimeUnit.SECONDS);
		vCode.write(response.getOutputStream());
		return null;
	}
}