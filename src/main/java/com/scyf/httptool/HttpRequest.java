package com.scyf.httptool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

/**
 * 发送http请求
 * 
 * @author 昊琦 @author WangYunfei
 * @version 1.0 创建时间 ：2018年3月15日09:46:02
 */
public class HttpRequest {

	/**
	 * 以get方式请求链接，最后返回json结果
	 * 
	 * @param strUrl
	 *            请求链接
	 * @return
	 */
	public static JSONObject sendGet(String strUrl) {

		// 要访问的链接
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(strUrl);

			// 打开连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置参数
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("GET");

			// 发送请求
			connection.connect();

			// 解析服务器返回的数据
			StringBuffer sBuffer = new StringBuffer();

			BufferedReader bReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String msg = "";
			while ((msg = bReader.readLine()) != null) {
				sBuffer.append(msg);
			}

			// 将数据转换成json返回
			JSONObject fromObject = JSONObject.fromObject(sBuffer.toString());

			bReader.close();

			return fromObject;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return JSONObject.fromObject("");
	}

	/**
	 * 以post方式请求链接，最后返回json数据
	 * 
	 * @param strUrl
	 *            请求链接
	 * @param param
	 *            请求参数
	 * @return
	 */
	public static JSONObject sendPost(String strUrl, Map<String, String> param) {

		// 要访问的链接
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(strUrl);

			// 打开连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置参数
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");

			// 发送请求
			connection.connect();

			// 发送数据
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
			// 遍历param,拼接参数
			Set<String> keySet = param.keySet();
			String p = "";
			for (String key : keySet) {
				p += (key + "=" + param.get(key) + "&");
			}
			p = p.substring(0, p.length() - 1);

			// 发送数据
			printWriter.print(p);
			printWriter.flush();

			printWriter.close();

			// 解析服务器返回的数据
			StringBuffer sBuffer = new StringBuffer();

			BufferedReader bReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String msg = "";
			while ((msg = bReader.readLine()) != null) {
				sBuffer.append(msg);
			}

			// 将数据转换成json返回
			JSONObject fromObject = JSONObject.fromObject(sBuffer.toString());

			bReader.close();

			return fromObject;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return JSONObject.fromObject("");

	}

	/**
	 * 以post方式请求链接，最后返回json数据
	 * 
	 * @param strUrl
	 *            请求链接
	 * @param json
	 *            json格式的参数
	 * @return
	 */
	public static JSONObject sendPost(String strUrl, String json) {

		// 要访问的链接
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(strUrl);

			// 打开连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置参数
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");

			// 发送请求
			connection.connect();

			// 发送数据
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));

			// 发送数据
			printWriter.print(json);
			printWriter.flush();

			printWriter.close();

			// 解析服务器返回的数据
			StringBuffer sBuffer = new StringBuffer();

			BufferedReader bReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String msg = "";
			while ((msg = bReader.readLine()) != null) {
				sBuffer.append(msg);
			}

			// 将数据转换成json返回
			JSONObject fromObject = JSONObject.fromObject(sBuffer.toString());

			bReader.close();

			return fromObject;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return JSONObject.fromObject("");

	}

	/**
	 * POST 发送文件
	 * 
	 * @param reqUrl
	 * @param file
	 * @param fileinfo
	 * @return JSONObject
	 */
	public static JSONObject sendPostFile(String reqUrl, File file, Map<String, Object> fileinfo) {
	
		if (!file.exists())
			return null;
		String result = null;
		try {
			URL url1 = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			//conn.setConnectTimeout(5000);
			//conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			String boundary = "-----------------------------" + System.currentTimeMillis();
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			OutputStream output = conn.getOutputStream();
			output.write(("--" + boundary + "\r\n").getBytes());
			output.write(
					String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName())
							.getBytes());
			output.write(String.format("Content-Type: \"%s\" \r\n\r\n",fileinfo.get("contentType")).getBytes());
			byte[] data = new byte[1024];
			int len = 0;
			FileInputStream input = new FileInputStream(file);
			while ((len = input.read(data)) > -1) {
				output.write(data, 0, len);
			}
			
			if (fileinfo.get("contentType").equals("video/mpeg4")) {
				if (fileinfo.get("title")!=null) {
					output.write(("--" + boundary + "\r\n").getBytes());
					output.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
					output.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}", fileinfo.get("title"), fileinfo.get("introduction")).getBytes());
				}
				
			}
			
			output.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
			output.flush();
			output.close();
			input.close();
			InputStream resp = conn.getInputStream();
			StringBuffer sb = new StringBuffer();
			while ((len = resp.read(data)) > -1)
				sb.append(new String(data, 0, len, "utf-8"));
			resp.close();
			result = sb.toString();
			//System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return JSONObject.fromObject(result);

	}
//	System.err.println(connection.getContentType()); 
	
	/**
	 * GET获取Content-Type
	 * @param strUrl
	 * @return  String
	 */
	public static String getContentType(String strUrl) {
		// 要访问的链接
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(strUrl);

			// 打开连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置参数
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("GET");

			// 发送请求
			connection.connect();

			// 解析服务器返回的数据
			// StringBuffer sBuffer = new StringBuffer();

	
			// 获取微信返回的输入流
			String in = connection.getContentType();
			return in;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * GET获取文件输入流
	 * @param strUrl
	 * @return  InputStream
	 */
	public static InputStream getFileSendGet(String strUrl) {
		// 要访问的链接
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(strUrl);

			// 打开连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置参数
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("GET");

			// 发送请求
			connection.connect();

			// 解析服务器返回的数据
			// StringBuffer sBuffer = new StringBuffer();

	
			// 获取微信返回的输入流
			InputStream in = connection.getInputStream();
			return in;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * POST获取文件输入流
	 * @param strUrl
	 * @return InputStream
	 */
	public static InputStream getFileSendPost(String strUrl, String json) {
		// 要访问的链接
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(strUrl);

			// 打开连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置参数
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");

			// 发送请求
			connection.connect();

			// 发送数据
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));

			// 发送数据
			printWriter.print(json);
			printWriter.flush();

			printWriter.close();

			// 解析服务器返回的数据
			
			// 获取微信返回的输入流
			System.err.println(connection.getContentType());
			InputStream in = connection.getInputStream();
			return in;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	// 测试
	public static void main(String[] args) {

		JSONObject sendGet = sendGet(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxd67bc72b62748ce1&secret=45ac99dfbd495179e4225ce37026a364");

		System.out.println(sendGet.toString());

		JSONObject sendPost = sendPost(
				"https://api.weixin.qq.com/cgi-bin/tags/create?access_token=7_VhAqrDAooGODE1tb3qX7WDScpigMm3FEFhVjn4b3hfFIckRsjxZDJHW5QvqMHCoTgBArxLld9O3U5kd_53SK_En2y9cA4N0Bt0VyODUtNDq-Cgua2tEl-cPrjWny0DnwStMue6ZdhBaEact6RNGfACAFBU",
				"{\"tag\":{\"name\":\"广东\"}}");
		System.out.println(sendPost.toString());

	}

}
