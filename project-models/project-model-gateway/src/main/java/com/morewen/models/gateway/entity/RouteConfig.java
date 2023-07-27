package com.morewen.models.gateway.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Wangbw
 */
@Data
public class RouteConfig {
    private String id;
    private String uri;
    private List<String> predicates;
    
    // getters and setters
}