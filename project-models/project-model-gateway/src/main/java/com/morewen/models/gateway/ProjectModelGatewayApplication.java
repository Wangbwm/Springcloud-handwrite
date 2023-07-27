package com.morewen.models.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @author Wangbw
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.morewen.models.gateway", "com.morewen.common.core"})
public class ProjectModelGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectModelGatewayApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
