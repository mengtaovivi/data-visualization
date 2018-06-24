/*
 * Copyright (C), 2018
 * Author: itw_wangxl02
 * Date: 2018/3/7
 */

package com.taikang.dataVis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger-ui用来形成微服务API界面的配置类
 * 本类需放在CusApplication同级目录，随项目启动
 * @author 王新亮
 * @see
 */

@Configuration
@EnableSwagger2    // 启用Swagger2
public class Swagger2 {

    /**
     * 创建API基本信息 test
     * 注意事项是basePackage的配置：扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.taikang.dataVis"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建API的基本信息，这些信息会在Swagger UI中进行显示
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")    // API总的标题
                .description("taikang-cus提供的RESTful APIs")    // API描述
                .contact("aoteman@")    // 联系人
                .version("1.0")    // 版本号
                .build();
    }
}
