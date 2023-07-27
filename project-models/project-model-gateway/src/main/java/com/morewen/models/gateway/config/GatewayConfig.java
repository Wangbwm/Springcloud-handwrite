package com.morewen.models.gateway.config;

import com.morewen.models.gateway.entity.RouteConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "gateway")
public class GatewayConfig {
    private List<RouteConfig> routes;
    
    public List<RouteConfig> getRoutes() {
        return routes;
    }
    
    public void setRoutes(List<RouteConfig> routes) {
        this.routes = routes;
    }
}