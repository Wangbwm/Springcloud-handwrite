package com.morewen.models.gateway.controller;

import com.morewen.common.core.anno.Log;
import com.morewen.common.core.enums.BusinessType;
import com.morewen.common.core.utils.StringUtils;
import com.morewen.models.gateway.config.GatewayConfig;
import com.morewen.models.gateway.entity.RouteConfig;
import com.morewen.models.gateway.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Wangbw
 */
@RestController
@Slf4j
public class GatewayController {
    @Autowired
    GatewayService gatewayService;
    boolean flag = false;

    @Autowired
    private GatewayConfig gatewayConfig;

    @PostConstruct
    public void init() {
        gatewayService.init();
        flag = true;
        log.info("网关服务启动成功");
    }

    @PreDestroy
    public void cleanup() {
        gatewayService.delete();
        flag = false;
        log.info("网关服务关闭成功");
    }


    @RequestMapping("/**")
    @Log(title = "网关服务", businessType = BusinessType.OTHER)
    public RedirectView gateway(HttpServletRequest request) {
        try {
            // 得到请求路径
            String path = request.getRequestURI();
            // 根据请求路径，得到服务
            String servicePath = path.split("/")[1];
            RouteConfig routeConfig = gatewayConfig.getRoutes().stream().filter(route -> route.getUri().contains(servicePath)).findFirst().get();
            // 得到服务ID
            String id = routeConfig.getId();
            // 得到服务地址
            String url = gatewayService.getUrl(id);
            if (StringUtils.isNotEmpty(url)) {
                // 重定向到服务地址
                return new RedirectView(url + path);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            log.error("服务转发失败", e);
            return null;
        }

    }

}
