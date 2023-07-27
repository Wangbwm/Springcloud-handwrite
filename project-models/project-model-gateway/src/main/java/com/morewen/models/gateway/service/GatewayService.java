package com.morewen.models.gateway.service;

import com.morewen.common.core.domain.DTO.ServiceRegisterDTO;

/**
 * @author Wangbw
 */
public interface GatewayService {
    boolean register(String serverAddr, String serviceName, String registerAddr);

    void init();

    void heartbeat(ServiceRegisterDTO serviceRegisterDTO);

    void delete();

    String getUrl(String id);
}
