package com.taikang.dataVis.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
//			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
//			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("Exception:" + "http发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				logger.error("Exception:" + e2);
				e2.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 带header的get请求
	 * @MethodName: sendGetWithHeader
	 * @param url
	 * @param param
	 * @param headerMap
	 * @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings({ "resource", "unchecked", "rawtypes" })
	public static String sendGetWithHeader(String url, String param, Map<String, String> headerMap) {
		String result = "";
		String urlNameString = url;
		if(!"".equals(param)) {
			urlNameString = url + "?" + param;
		}
		HttpClient client = new DefaultHttpClient();
		// 发送get请求
		HttpGet request = new HttpGet(urlNameString);
		Iterator iteratorHeader = headerMap.entrySet().iterator();
		while (iteratorHeader.hasNext()) {
			Entry<String, String> elem = (Entry<String, String>) iteratorHeader.next();
			request.addHeader(elem.getKey(), elem.getValue());
		}
		HttpResponse response;
		try {
			response = client.execute(request);
			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			logger.error("Exception:" + "http发送 get 请求出现异常！" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Exception:" + "http发送 get 请求出现异常！" + e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("Exception:" + "http发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("Exception:" + ex);
				ex.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 请求接口通用方法 json格式的参数
	 * @MethodName: httpPostWithJSON
	 * @param url
	 * @param jsonParam
	 * @param headerMap
	 * @return
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String httpPostWithJSON(String url, JSONObject jsonParam, Map<String, String> headerMap)
			throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		// json方式
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);

		Iterator iteratorHeader = headerMap.entrySet().iterator();
		while (iteratorHeader.hasNext()) {
			Entry<String, String> elem = (Entry<String, String>) iteratorHeader.next();
			httpPost.setHeader(elem.getKey(), elem.getValue());
		}
		HttpResponse resp = client.execute(httpPost);
		logger.info("code=" + resp.getStatusLine().getStatusCode());
		HttpEntity he = resp.getEntity();
		respContent = EntityUtils.toString(he, "UTF-8");
		return respContent;
	}

	/**
	 * 请求接口通用方法 json格式的参数
	 * @MethodName: httpPostWithJSON
	 * @param url
	 * @param jsonParam
	 * @param headerMap
	 * @return
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String httpPutWithJSON(String url, JSONObject jsonParam, Map<String, String> headerMap)
			throws Exception {
		HttpPut httpPut = new HttpPut(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		// json方式
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPut.setEntity(entity);

		Iterator iteratorHeader = headerMap.entrySet().iterator();
		while (iteratorHeader.hasNext()) {
			Entry<String, String> elem = (Entry<String, String>) iteratorHeader.next();
			httpPut.setHeader(elem.getKey(), elem.getValue());
		}
		HttpResponse resp = client.execute(httpPut);
		logger.info("code=" + resp.getStatusLine().getStatusCode());
		HttpEntity he = resp.getEntity();
		respContent = EntityUtils.toString(he, "UTF-8");
		return respContent;
	}
}
