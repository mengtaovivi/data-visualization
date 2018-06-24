package com.taikang.dataVis.core.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *  
 * 项目名称:	[cus]
 * 包:		[com.taikang.cus.core.utils]
 * 类名称:	[HttpsClientUtil]
 * 类描述:	[利用HttpClient进行post请求的工具类，https]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年3月21日 下午6:13:39]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年3月21日 下午6:13:39]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 *
 */
@SuppressWarnings("deprecation")
public class HttpsClientUtil extends DefaultHttpClient {
	/**
	 * 
	 * @MethodName: doGet
	 * @Description: get请求方法
	 * @param url
	 * @param charset
	 * @param headerMap
	 * @return
	 * @return String
	 * @throws
	 *
	 */
	@SuppressWarnings("unchecked")
	public String doGet(String url,String charset,Map<String, String> headerMap){
		if (null == charset) {
			charset = "utf-8";
		}
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String result = null;
		try {
			httpClient = new HttpsClientUtil();
			httpGet = new HttpGet(url);
			// header设置参数
			Iterator<?> iteratorHeader = headerMap.entrySet().iterator();
			while (iteratorHeader.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iteratorHeader.next();
				httpGet.setHeader(elem.getKey(), elem.getValue());
			}
			HttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @MethodName: doPost
	 * @Description: post请求方法
	 * @param url
	 * @param map
	 * @param headerMap
	 * @param charset
	 * @return
	 * @return String
	 * @throws
	 *
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String doPost(String url, Map<String, String> map, Map<String, String> headerMap, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new HttpsClientUtil();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			Iterator iteratorHeader = headerMap.entrySet().iterator();
			while (iteratorHeader.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iteratorHeader.next();
				httpPost.setHeader(elem.getKey(), elem.getValue());
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @Title:
	 * @Description: 客户端
	 * @throws Exception
	 *
	 */
	public HttpsClientUtil() throws Exception {
		super();
		// 传输协议需要根据自己的判断
		SSLContext ctx = SSLContext.getInstance("TLSv1.2");
		X509TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		ctx.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = this.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, ssf));
	}
}
