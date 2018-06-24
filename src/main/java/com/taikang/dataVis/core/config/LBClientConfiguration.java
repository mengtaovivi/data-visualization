package com.taikang.dataVis.core.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LBClientConfiguration {
	
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @LoadBalanced
    @Bean
    RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }
}
