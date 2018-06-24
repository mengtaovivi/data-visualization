package com.taikang.dataVis.core.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 *
 *
 * @author 孟涛
 * @description http请求工具类
 * @date 2018/4/16 15:09
 * @return
 */
public class HttpGitLabUtil {

    private static CloseableHttpClient buildSSLCloseableHttpClient() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    public static String doStringPost(JSONObject params, String url) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        try {
            httpclient = buildSSLCloseableHttpClient();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
                    .build();// 设置请求和传输超时时间
            // 创建httppost
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);

            String param = params.toString();

            param = param.replaceAll("\\\\r", "");
            param = param.replaceAll("\\\\n", "");

            System.out.println("请求的参数是:" + param);
            StringEntity entity = new StringEntity(param, ContentType.APPLICATION_FORM_URLENCODED);
            httppost.setEntity(entity); // 发送字符串参数
            System.out.println("executing request url" + httppost.getURI());
            System.out.println("executing request param " + param);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "UTF-8");
                    System.out.println("Response content: " + result);
                    return result;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}