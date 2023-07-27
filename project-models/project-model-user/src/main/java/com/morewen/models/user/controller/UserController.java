package com.morewen.models.user.controller;

import com.morewen.common.core.anno.Log;
import com.morewen.common.core.enums.BusinessType;
import com.morewen.common.core.utils.AjaxResult;
import com.morewen.models.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Wangbw
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    boolean flag = false;

    @Autowired
    UserService userService;
    @PostConstruct
    public void init() {
        userService.init();
        flag = true;
        log.info("用户服务启动成功");
    }

    @PreDestroy
    public void cleanup() {
        userService.delete();
        flag = false;
        log.info("用户服务关闭成功");
    }

    @GetMapping("/test")
    @Log(title = "用户服务测试", businessType = BusinessType.OTHER)
    public AjaxResult test() {
        if (!flag) {
            userService.init();
            flag = true;
        }
        return AjaxResult.success("Hello World!");
    }

}
