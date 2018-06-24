package com.taikang.dataVis.core.config.interceptor;

import com.taikang.dataVis.DataVISApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 〈拦截器〉
 * 〈功能详细描述〉
 *
 * @author [作者]（孟涛）
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
@ComponentScan(basePackageClasses = DataVISApplication.class, useDefaultFilters = true)
public class WebAppConfig extends WebMvcConfigurerAdapter {
    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ProcessInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new UserMessageInterceptor()).addPathPatterns("/**");
    }
}
