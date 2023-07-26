package com.morewen.models.register.controller;

import com.morewen.common.core.anno.Log;
import com.morewen.common.core.domain.DTO.ServiceRegisterDTO;
import com.morewen.common.core.enums.BusinessType;
import com.morewen.common.core.exception.ServiceException;
import com.morewen.common.core.utils.AjaxResult;
import com.morewen.models.register.service.ServiceRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Wangbw
 */
@RestController
@RequestMapping("/register")
@Slf4j
public class ServiceRegisterController {

    @Autowired
    ServiceRegisterService serviceRegisterService;
    /**
     * 服务注册
     * @param serviceRegisterDTO 服务注册信息
     * @return 结果
     */
    @PostMapping("/index")
    @Log(title = "服务注册", businessType = BusinessType.INSERT)
    protected AjaxResult index(@RequestBody ServiceRegisterDTO serviceRegisterDTO) {
        checkDTO(serviceRegisterDTO);

        if (!serviceRegisterService.register(serviceRegisterDTO)) {
            throw new ServiceException("服务注册失败");
        }
        log.info("服务:{}, url:{} 注册成功", serviceRegisterDTO.getName(), serviceRegisterDTO.getUrl());
        return AjaxResult.success("服务注册成功");
    }

    /**
     * 服务发现
     * @param serviceName 服务名
     * @return 服务地址
     */
    @GetMapping("/discover")
    @Log(title = "服务发现", businessType = BusinessType.OTHER)
    protected AjaxResult discover(@RequestParam("serviceName") String serviceName) {
        ServiceRegisterDTO serviceRegisterDTO = serviceRegisterService.discover(serviceName);
        if (Objects.isNull(serviceRegisterDTO)) {
            throw new ServiceException("服务发现失败");
        }
        return AjaxResult.success(serviceRegisterDTO);
    }

    /**
     * 心跳检测
     * @return 结果
     */
    @PostMapping("/heartBeat")
    @Log(title = "心跳检测", businessType = BusinessType.OTHER)
    protected AjaxResult heartBeat(@RequestBody ServiceRegisterDTO serviceRegisterDTO) {
        checkDTO(serviceRegisterDTO);
        serviceRegisterService.delay(serviceRegisterDTO.getName(), serviceRegisterDTO.getUrl());
        return AjaxResult.success("心跳检测成功");
    }

    private static void checkDTO(ServiceRegisterDTO serviceRegisterDTO) {
        if (Objects.isNull(serviceRegisterDTO)) {
            throw new ServiceException("服务注册信息不能为空");
        }
        if (Objects.isNull(serviceRegisterDTO.getName())) {
            throw new ServiceException("服务名不能为空");
        }
        if (Objects.isNull(serviceRegisterDTO.getUrl())) {
            throw new ServiceException("服务地址不能为空");
        }
    }


}
