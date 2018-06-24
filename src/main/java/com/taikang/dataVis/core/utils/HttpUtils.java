package com.taikang.dataVis.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author 孟涛
 * @description http工具类
 *
 * 针对上诉json数据结构解析示例代码如下：
    String responseText = EntityUtils.toString(response.getEntity());
    Map responseMap = JSON.parseObject(responseText, Map.class);
    JSONObject resObj = (JSONObject) responseMap.get("result"); //再转换一次json
    Map<String,Object> resMap = JSON.parseObject(resObj.toJSONString(),Map.class);
    String resRet = resMap.get("res").toString();
 * @date 2018/4/26 19:19
 * @return
 */
public class HttpUtils {

  //设置编码格式
  public String Content_Type = "application/x-www-form-urlencoded; charset=UTF-8" ;

  /**
   * get 请求
   */
  public static String doGet(String host, String path, String method,
      Map<String, String> headers,
      Map<String, String> querys)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpGet request = new HttpGet(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }
    HttpResponse response = httpClient.execute(request) ;
    return EntityUtils.toString(response.getEntity());
  }

  /**
   * post form
   */
  public static String doPost(String host, String path, String method,
      Map<String, String> headers,
      Map<String, String> querys,
      Map<String, String> bodys)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpPost request = new HttpPost(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    if (bodys != null) {
      List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

      for (String key : bodys.keySet()) {
        nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
      }
      UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
      formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
      request.setEntity(formEntity);
    }
    HttpResponse response = httpClient.execute(request) ;
    return EntityUtils.toString(response.getEntity());
  }

  /**
   * Post String
   */
  public static String doPost(String host, String path, String method,
      Map<String, String> headers,
      Map<String, String> querys,
      String body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpPost request = new HttpPost(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    if (StringUtils.isNotBlank(body)) {
      request.setEntity(new StringEntity(body, "utf-8"));
    }
    HttpResponse response = httpClient.execute(request) ;
    return EntityUtils.toString(response.getEntity());
  }

  /**
   * Post stream
   */
  public static String doPost(String host, String path, String method,
      Map<String, String> headers,
      Map<String, String> querys,
      byte[] body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpPost request = new HttpPost(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    if (body != null) {
      request.setEntity(new ByteArrayEntity(body));
    }
    HttpResponse response = httpClient.execute(request) ;
    return EntityUtils.toString(response.getEntity());
  }

  /**
   * Put String
   */
  public static String doPut(String host, String path, String method,
      Map<String, String> headers,
      Map<String, String> querys,
      String body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpPut request = new HttpPut(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    if (StringUtils.isNotBlank(body)) {
      request.setEntity(new StringEntity(body, "utf-8"));
    }
    HttpResponse response = httpClient.execute(request) ;
    return EntityUtils.toString(response.getEntity());
  }

  /**
   * Put stream
   */
  public static String doPut(String host, String path, String method,
      Map<String, String> headers,
      Map<String, String> querys,
      byte[] body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpPut request = new HttpPut(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    if (body != null) {
      request.setEntity(new ByteArrayEntity(body));
    }
    HttpResponse response = httpClient.execute(request) ;
    return EntityUtils.toString(response.getEntity());
  }

  /**
   * Delete
   */
  public static String doDelete(String host, String path, String method,
      Map<String, String> headers,
      Map<String, String> querys)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }
    HttpResponse response = httpClient.execute(request) ;
    return EntityUtils.toString(response.getEntity());
  }

  private static String buildUrl(String host, String path, Map<String, String> querys)
      throws UnsupportedEncodingException {
    StringBuilder sbUrl = new StringBuilder();
    sbUrl.append(host);
    if (!StringUtils.isBlank(path)) {
      sbUrl.append(path);
    }
    if (null != querys) {
      StringBuilder sbQuery = new StringBuilder();
      for (Map.Entry<String, String> query : querys.entrySet()) {
        if (0 < sbQuery.length()) {
          sbQuery.append("&");
        }
        if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
          sbQuery.append(query.getValue());
        }
        if (!StringUtils.isBlank(query.getKey())) {
          sbQuery.append(query.getKey());
          if (!StringUtils.isBlank(query.getValue())) {
            sbQuery.append("=");
            sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
          }
        }
      }
      if (0 < sbQuery.length()) {
        sbUrl.append("?").append(sbQuery);
      }
    }
    return sbUrl.toString();
  }

  private static HttpClient wrapClient(String host) {
    HttpClient httpClient = new DefaultHttpClient();
    if (host.startsWith("https://")) {
      sslClient(httpClient);
    }
    return httpClient;
  }

  private static void sslClient(HttpClient httpClient) {
    try {
      SSLContext ctx = SSLContext.getInstance("TLS");
      X509TrustManager tm = new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] xcs, String str) {

        }

        public void checkServerTrusted(X509Certificate[] xcs, String str) {

        }
      };
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLSocketFactory ssf = new SSLSocketFactory(ctx);
      ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager ccm = httpClient.getConnectionManager();
      SchemeRegistry registry = ccm.getSchemeRegistry();
      registry.register(new Scheme("https", ssf, 443));
    } catch (KeyManagementException ex) {
      throw new RuntimeException(ex);
    } catch (NoSuchAlgorithmException ex) {
      throw new RuntimeException(ex);
    }
  }

}
