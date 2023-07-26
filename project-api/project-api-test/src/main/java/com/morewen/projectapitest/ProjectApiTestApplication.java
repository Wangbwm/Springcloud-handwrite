package com.morewen.projectapitest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Wangbw
 */ // 关闭数据库自动配置
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.morewen.projectapitest", "com.morewen.projectcommoncore"})
public class ProjectApiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApiTestApplication.class, args);
    }

}
