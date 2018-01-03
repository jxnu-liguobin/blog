package cn.edu.jxnu.blog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;

/**
 * HttpClient
 * 
 * @author： liguobin
 * @Description：
 * @时间： 2018-1-2 下午7:06:14
 * @version： V1.0
 * 
 */
public class PushLinkToBaidu {

	private static final Logger log = org.slf4j.LoggerFactory
			.getLogger(PushLinkToBaidu.class);

	public static String Post(String PostUrl, String[] Parameters) {
		if (null == PostUrl || null == Parameters || Parameters.length == 0) {
			return null;
		}
		String result = "";
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			// 建立URL之间的连接
			URLConnection conn = new URL(PostUrl).openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Host", "data.zz.baidu.com");
			conn.setRequestProperty("User-Agent", "curl/7.12.1");
			conn.setRequestProperty("Content-Length", "83");
			conn.setRequestProperty("Content-Type", "text/plain");

			// 发送POST请求必须设置如下两行
			conn.setDoInput(true);
			conn.setDoOutput(true);

			// 获取conn对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			String param = "";
			for (String s : Parameters) {
				param += s + "\n";
			}
			out.print(param.trim());
			// 进行输出流的缓冲
			out.flush();
			// 通过BufferedReader输入流来读取Url的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			log.error("发送post->请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		log.info("发送post->OK！");
		return result;
	}
}
