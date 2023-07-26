package com.morewen.models.user.service;

import com.morewen.common.core.domain.DTO.ServiceRegisterDTO;

/**
 * @author Wangbw
 */
public interface UserService {
    // 服务注册
    boolean register(String serverAddr, String serviceName, String registerAddr);
    void init();
    /**
     * 心跳检测
     */
    void heartbeat(ServiceRegisterDTO serviceRegisterDTO);
}
