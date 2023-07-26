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

    @GetMapping("/index")
    @Log(title = "用户服务注册", businessType = BusinessType.OTHER)
    public AjaxResult index() {
        try {
            userService.init();
        } catch (Exception e) {
            log.error("注册失败", e);
            return AjaxResult.error("注册失败");
        }
        flag = true;
        return AjaxResult.success();
    }

    /**
     * 服务注销
     * @return 结果
     */
    @DeleteMapping("/delete")
    protected AjaxResult delete() {
        if (!flag) {
            return AjaxResult.error("服务未注册");
        }
        try {
            userService.delete();
        } catch (Exception e) {
            log.error("注销失败", e);
            return AjaxResult.error("注销失败");
        }
        flag = false;
        return AjaxResult.success();
    }

    @GetMapping("/test")
    @Log(title = "用户服务测试", businessType = BusinessType.OTHER)
    public AjaxResult test() {
        if (!flag) {
            userService.init();
        }
        return AjaxResult.success("Hello World!");
    }

}
