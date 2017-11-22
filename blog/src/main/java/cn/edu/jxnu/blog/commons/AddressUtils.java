package cn.edu.jxnu.blog.commons;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class AddressUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String ip = "0:0:0:0:0:0:0:1";

		String address = "";

		try {

			address = AddressUtils.getAddress("ip=" + ip, "utf-8");

		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println(address);

	}

	/**
	 * 获取地址
	 * 
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String getAddress(String params, String encoding)
			throws Exception {

		String path = "http://ip.taobao.com/service/getIpInfo.php";

		String returnStr = getRs(path, params, encoding); // 获取返回的结果

		JSONObject json = null;

		if (returnStr != null) {

			json = new JSONObject(returnStr);

			if ("0".equals(json.get("code").toString())) {

				StringBuffer buffer = new StringBuffer();

				buffer.append(decodeUnicode(json.optJSONObject("data")
						.getString("country")));// 国家
				buffer.append(" ");
				buffer.append(decodeUnicode(json.optJSONObject("data")
						.getString("region")));// 省份
				buffer.append(" ");
				buffer.append(decodeUnicode(json.optJSONObject("data")
						.getString("city")));// 市区

				String s = buffer.toString();
				String[] ss = s.split(" ");
				String scountry = null;
				@SuppressWarnings("unused")
				String sregion = null;
				String scity = null;
				scountry = ss[0];
				sregion = ss[1];
				scity = ss[2];
				if ("内网IP".equals(scountry) || "内网IP".equals(scity)) {
					return "内网IP";
				}
				return buffer.toString();

			} else {

				return "获取地址失败!";

			}

		}

		return null;

	}

	/**
	 * 从url获取结果
	 * 
	 * @param path
	 * @param params
	 * @param encoding
	 * @return
	 */
	public static String getRs(String path, String params, String encoding) {

		URL url = null;

		HttpURLConnection connection = null;

		try {

			url = new URL(path);

			connection = (HttpURLConnection) url.openConnection();// 新建连接实例

			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫 秒

			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒

			connection.setDoInput(true);// 是否打开输出 true|false

			connection.setDoOutput(true);// 是否打开输入流true|false

			connection.setRequestMethod("POST");// 提交方法POST|GET

			connection.setUseCaches(false);// 是否缓存true|false

			connection.connect();// 打开连接端口

			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());

			out.writeBytes(params);

			out.flush();

			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));

			StringBuffer buffer = new StringBuffer();

			String line = "";

			while ((line = reader.readLine()) != null) {

				buffer.append(line);

			}

			reader.close();

			return buffer.toString();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			connection.disconnect();// 关闭连接

		}

		return null;
	}

	/**
	 * 字符转码
	 * 
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {

		char aChar;

		int len = theString.length();

		StringBuffer buffer = new StringBuffer(len);

		for (int i = 0; i < len;) {

			aChar = theString.charAt(i++);

			if (aChar == '\\') {

				aChar = theString.charAt(i++);

				if (aChar == 'u') {

					int val = 0;

					for (int j = 0; j < 4; j++) {

						aChar = theString.charAt(i++);

						switch (aChar) {

						case '0':

						case '1':

						case '2':

						case '3':

						case '4':

						case '5':

						case '6':

						case '7':

						case '8':

						case '9':

							val = (val << 4) + aChar - '0';

							break;

						case 'a':

						case 'b':

						case 'c':

						case 'd':

						case 'e':

						case 'f':

							val = (val << 4) + 10 + aChar - 'a';

							break;

						case 'A':

						case 'B':

						case 'C':

						case 'D':

						case 'E':

						case 'F':

							val = (val << 4) + 10 + aChar - 'A';

							break;

						default:

							throw new IllegalArgumentException(

							"Malformed      encoding.");
						}

					}

					buffer.append((char) val);

				} else {

					if (aChar == 't') {

						aChar = '\t';
					}

					if (aChar == 'r') {

						aChar = '\r';
					}

					if (aChar == 'n') {

						aChar = '\n';
					}

					if (aChar == 'f') {

						aChar = '\f';

					}

					buffer.append(aChar);
				}

			} else {

				buffer.append(aChar);

			}

		}

		return buffer.toString();

	}

}