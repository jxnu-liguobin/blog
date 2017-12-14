package cn.edu.jxnu.blog.filter;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 
 * @Description 自定义过滤器，用来判断IP访问次数是否超限。<br>
 *              如果前台用户访问网站的频率过快，则判定该IP为恶意刷新操作，限制该ip访问<br>
 *              默认限制访问时间为1小时，一小时后自定解除限制 20次/5秒将直接拦截。
 * 
 * @author liguobin
 */

public class IPFilter implements HandlerInterceptor {

	/**
	 * 默认限制时间（单位：ms）
	 */
	private static final long LIMITED_TIME_MILLIS = 60 * 60 * 1000;

	/**
	 * 用户连续访问最高阀值，超过该值则认定为恶意操作的IP，进行限制
	 */
	private static final int LIMIT_NUMBER = 19;

	/**
	 * 用户访问最小安全时间，在该时间内如果访问次数大于阀值，则记录为恶意IP，否则视为正常访问
	 */
	private static final int MIN_SAFE_TIME = 5000;

	/**
	 * @Description 是否是被限制的IP
	 * @author liguobin
	 * @param limitedIpMap
	 * @param ip
	 */
	private boolean isLimitedIP(Map<String, Long> limitedIpMap, String ip) {
		if (limitedIpMap == null || ip == null) {
			// 没有被限制
			return false;
		}
		Set<String> keys = limitedIpMap.keySet();
		Iterator<String> keyIt = keys.iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			if (key.equals(ip)) {
				// 被限制的IP
				return true;
			}
		}
		return false;
	}

	/**
	 * @Description 过滤受限的IP，剔除已经到期的限制IP 限制时间是一个小时。
	 * @author liguobin
	 * @param limitedIpMap
	 */
	private void filterLimitedIpMap(Map<String, Long> limitedIpMap) {
		if (limitedIpMap == null) {
			return;
		}
		Set<String> keys = limitedIpMap.keySet();
		Iterator<String> keyIt = keys.iterator();
		long currentTimeMillis = System.currentTimeMillis();
		while (keyIt.hasNext()) {
			long expireTimeMillis = limitedIpMap.get(keyIt.next());
			if (currentTimeMillis >= expireTimeMillis) {
				keyIt.remove();
			}
		}

	}

	/**
	 * 初始化用户访问次数和访问时间
	 * 
	 * @author liguobin
	 * @param ipMap
	 * @param ip
	 */
	private void initIpVisitsNumber(Map<String, Long[]> ipMap, String ip) {
		Long[] ipInfo = new Long[2];
		ipInfo[0] = 0L;// 访问次数
		ipInfo[1] = System.currentTimeMillis();// 初次访问时间
		ipMap.put(ip, ipInfo);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	/**
	 * 首次访问
	 * 
	 * @Description 核心处理代码
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		ServletContext context = RequestContextUtils.findWebApplicationContext(
				request).getServletContext();
		@SuppressWarnings("unchecked")
		// 得到context的限制ip名单
		Map<String, Long> limitedIpMap = (Map<String, Long>) context
				.getAttribute("limitedIpMap");
		// 过滤受限的IP，清除到期的ip
		filterLimitedIpMap(limitedIpMap);
		// 获取用户IP
		String ip = request.getRemoteAddr();
		// 判断是否是被限制的IP，如果是则跳到异常页面
		if (isLimitedIP(limitedIpMap, ip)) {
			long limitedTime = limitedIpMap.get(ip)
					- System.currentTimeMillis();
			// 剩余限制时间(从毫秒到秒转化的一定会存在些许误差，但基本可以忽略不计)
			System.out
					.println("剩余限制时间："
							+ ((limitedTime / 1000) + (limitedTime % 1000 > 0 ? 1
									: 0)));
			request.setAttribute("remainingTime",
					((limitedTime / 1000) + (limitedTime % 1000 > 0 ? 1 : 0)));
			modelAndView.setViewName("errors/406");
			modelAndView.addObject("currentIp", ip);
			modelAndView.addObject("currentTime",
					((limitedTime / 1000) + (limitedTime % 1000 > 0 ? 1 : 0)));
		}
		// 获取IP存储器
		@SuppressWarnings("unchecked")
		Map<String, Long[]> ipMap = (Map<String, Long[]>) context
				.getAttribute("ipMap");

		// 判断存储器中是否存在当前IP，如果没有则为初次访问，初始化该ip
		// 如果存在当前ip，则验证当前ip的访问次数
		// 如果大于限制阀值，判断达到阀值的时间，如果不大于[用户访问最小安全时间]则视为恶意访问，跳转到异常页面
		if (ipMap.containsKey(ip)) {
			Long[] ipInfo = ipMap.get(ip);
			ipInfo[0] = ipInfo[0] + 1;
			System.out.println("当前第[" + (ipInfo[0]) + "]次访问");
			// 访问次数限制
			if (ipInfo[0] > LIMIT_NUMBER) {
				Long ipAccessTime = ipInfo[1];// 第一次访问的时间。
				Long currentTimeMillis = System.currentTimeMillis();
				// 最小时间间隔5000ms
				if (currentTimeMillis - ipAccessTime <= MIN_SAFE_TIME) {
					limitedIpMap.put(ip, currentTimeMillis
							+ LIMITED_TIME_MILLIS);
					request.setAttribute("remainingTime", LIMITED_TIME_MILLIS);
					modelAndView.setViewName("errors/406");
					modelAndView.addObject("currentIp", ip);
					modelAndView.addObject("currentTime",
							LIMITED_TIME_MILLIS / 1000);
				} else {
					initIpVisitsNumber(ipMap, ip);
				}
			}
		} else {
			initIpVisitsNumber(ipMap, ip);
			System.out.println("您首次访问该网站");
		}
		context.setAttribute("ipMap", ipMap);
		context.setAttribute("limitedIpMap", limitedIpMap);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		response.setStatus(406);// 返回一个406 不接受访问了。
	}
}