package com.taikang.dataVis.core.config.interceptor;

import com.taikang.dataVis.core.utils.UserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author 王新亮
 * @date 2018/6/5
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class UserMessageInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(UserMessageInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try {
            Map<String, String> map = new HashMap<String, String>();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String keystonetoken = request.getHeader("keystonetoken");
            String id = request.getHeader("id");
            String userId = request.getHeader("userId");
            String userName = request.getHeader("userName");
            String defaultRoleCode = request.getHeader("defaultRoleCode");
            String defaultRoleName = request.getHeader("defaultRoleName");
            map.put("keystonetoken", keystonetoken);
            map.put("id", id);
            map.put("userId", userId);
            map.put("userName", new String(userName.getBytes("iso-8859-1"), "utf-8"));
            map.put("defaultRoleCode", defaultRoleCode);
            map.put("defaultRoleName", new String(defaultRoleName.getBytes("iso-8859-1"), "utf-8"));
            UserMessage.userMessage.set(map);
        } catch (Exception e) {
            log.error("datav的过滤器获取用户信息失败", e);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
