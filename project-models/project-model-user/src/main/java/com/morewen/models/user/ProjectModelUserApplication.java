package com.morewen.models.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @author Wangbw
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.morewen.models.user", "com.morewen.common.core"})
public class ProjectModelUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectModelUserApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
