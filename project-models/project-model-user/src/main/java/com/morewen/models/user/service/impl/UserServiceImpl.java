package com.morewen.models.user.service.impl;

import com.morewen.common.core.domain.DTO.ServiceRegisterDTO;
import com.morewen.common.core.exception.ServiceException;
import com.morewen.common.core.utils.AjaxResult;
import com.morewen.models.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Wangbw
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Value("${register.discovery.server-addr}")
    private String serverAddr;

    @Value("${register.discovery.service-name}")
    private String serviceName;

    @Value("${register.discovery.register-addr}")
    private String registerAddr;

    @Autowired
    RestTemplate restTemplate;

    private ServiceRegisterDTO serviceRegisterDTO;

    int count = 0;

    boolean flag = false;
    @Override
    public boolean register(String serverAddr, String serviceName, String registerAddr) {
        try {
            serviceRegisterDTO = new ServiceRegisterDTO();
            serviceRegisterDTO.setName(serviceName);
            serviceRegisterDTO.setUrl("http://" + serverAddr);
            serviceRegisterDTO.setDescription("用户服务");
            String url = "http://" + registerAddr + "/register/index";
            AjaxResult result = restTemplate.postForObject(url, serviceRegisterDTO, AjaxResult.class);
            return result.isSuccess();
        } catch (Exception e) {
            log.error("注册失败", e);
            return false;
        }
    }
    @Override
    public void init() {
        while (!flag) {
            flag = register(serverAddr, serviceName, registerAddr);
            if (!flag) {
                try {
                    Thread.sleep(5000);
                    // 五次之后还是失败，就不再注册，关闭程序
                    if (count++ > 5) {
                        log.error("注册失败");
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    log.error("注册失败");
                }
            }
        }
        // 注册成功，开启心跳检测发送
        new Thread(() -> {
            while (flag) {
                try {
                    // 1分钟发送一次
                    Thread.sleep(1000 * 60);
                    heartbeat(serviceRegisterDTO);
                } catch (InterruptedException e) {
                    log.error("心跳检测失败");
                }
            }
        }).start();
    }

    @Override
    public void heartbeat(ServiceRegisterDTO serviceRegisterDTO) {
        String url = "http://" + registerAddr + "/register/heartBeat";
        restTemplate.postForObject(url,serviceRegisterDTO, Object.class);
    }

    @Override
    public void delete() {
        String url = "http://" + registerAddr + "/register/delete";
        AjaxResult ajaxResult = restTemplate.postForObject(url, serviceRegisterDTO, AjaxResult.class);
        if (ajaxResult.isSuccess()) {
            flag = false;
        }else {
            throw new ServiceException("注销失败");
        }
    }
}
