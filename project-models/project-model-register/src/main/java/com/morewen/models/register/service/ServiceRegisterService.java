package com.morewen.models.register.service;

import com.morewen.common.core.domain.DTO.ServiceRegisterDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Wangbw
 */
public interface ServiceRegisterService {
    boolean register(ServiceRegisterDTO serviceRegisterDTO);

    ServiceRegisterDTO discover(String serviceName);
    // 心跳检测
    void heartBeat();
    // 延长服务存活时间
    void delay(String serviceName, String url);

    void delete(String name, String url);
}
