package com.morewen.models.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wangbw
 */
@Configuration
@EnableConfigurationProperties(GatewayConfig.class)
public class MyConfiguration {
    
    // 注入 GatewayConfig 对象
    @Autowired
    private GatewayConfig gatewayConfig;
    
    // 在需要的地方使用 gatewayConfig.getRoutes() 获取路由列表
}