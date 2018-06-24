package com.taikang.dataVis.core.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈拦截器〉
 * 〈功能详细描述〉
 *
 * @author [作者]（孟涛）
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
public class ProcessInterceptor implements HandlerInterceptor {

    /**
     *  进入controller层之前拦截请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length,Authorization,Accept,currentuser,keystonetoken,defaultrole,X-Requested-With");
        response.setHeader("Access-Control-Expose-Headers", "currentuser,keystonetoken,defaultrole,code,message");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("X-Powered-By", "Jetty");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
//        response.setHeader("Content-Type","application/x-www-form-urlencoded");
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            response.setStatus(200);
            return false;
        }
        System.out.println(method);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
