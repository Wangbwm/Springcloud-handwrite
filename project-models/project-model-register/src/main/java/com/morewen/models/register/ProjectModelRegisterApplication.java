package com.morewen.models.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Wangbw
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.morewen.models.register", "com.morewen.common.core"})
public class ProjectModelRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectModelRegisterApplication.class, args);
    }

}
