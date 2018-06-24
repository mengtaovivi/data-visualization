package com.taikang.dataVis.core.utils;

import com.taikang.dataVis.core.config.exception.DatavException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 〈处理向Keystone发送的请求〉
 * @author 王新亮
 * @date 2018-06-05
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
public class GatewayRequestHandler {

    public static final Logger logger = LoggerFactory.getLogger(GatewayRequestHandler.class);

    private String url = "http://gateway/";

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    // 添加请求头(添加认证token、当前用户、默认角色)
    private HttpHeaders getHeaders() throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String userId = UserMessage.getCurrentUserId();
        String keystoneToken = UserMessage.getKeystoneToken();
        String defaultRoleName = UserMessage.getDefaultRoleName();
        headers.add("keystonetoken", keystoneToken);
        headers.add("currentuser", userId);
        headers.add("defaultRole", URLEncoder.encode(defaultRoleName, "UTF-8"));
        return headers;
    }

    /**
     * 〈向gateway发送请求〉
     * @param [path, httpMethod, body, headers, responseType, uriVariables]
     * @return  org.springframework.http.ResponseEntity<T>
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
    public <T> ResponseEntity<T> exchange(String path, HttpMethod httpMethod, Object body, Class<T> responseType, Object... uriVariables) {
        try {

            return restTemplate.exchange(url + path, httpMethod,
                    new HttpEntity<>(body, getHeaders()), responseType, uriVariables);
        } catch (Exception e) {
            logger.error("请求gateway接口失败，path：" + path, e);
            throw new DatavException("500", "请求gateway接口失败，path：" + path);
        }
    }

}
