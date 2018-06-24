package com.taikang.dataVis.core.utils;

import org.apache.log4j.Logger;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 日志工具类
 * Created by itw_mengtao on 2018/3/7.
 */
public class CommonUtils {


    private static Logger logger = Logger.getLogger(CommonUtils.class);

    /**
     *
     * 获取traceid
     * @author itw_mengtao
     * @date 2018/3/8 15:32
     * @param [tracer]
     * @return java.lang.String
     */
    public static String extractTraceId(Tracer tracer) {
        Span span = tracer.getCurrentSpan();
        String traceId = (span == null ? null : span.traceIdString());
        if (traceId == null || traceId.isEmpty()) {
            traceId = "[NoClientTraceId]" + UUID.randomUUID().toString();
        }
        return traceId;
    }

    /**
     *
     * 获取错误信息
     * @author itw_mengtao
     * @date 2018/3/8 15:32
     * @param [result]
     * @return java.lang.String
     */
    public static String getBindErrorMsg(BindingResult result) {
        String errorMsg = "";
        if (result != null && result.hasErrors()) {
            ObjectError objectError;
            List<ObjectError> list = result.getAllErrors();
            Iterator<ObjectError> it = list.iterator();
            while (it.hasNext()) {
                objectError = it.next();
                logger.error(objectError.getCode()
                        + "---"
                        + objectError.getArguments()
                        + "----"
                        + objectError.getDefaultMessage());
                errorMsg = objectError.getDefaultMessage() + "|" + errorMsg;
            }
        }
        return errorMsg;
    }

    /**
     *
     * 获取请求来源地址
     * @author itw_mengtao
     * @date 2018/3/8 15:32
     * @param [request]
     * @return java.lang.String
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
