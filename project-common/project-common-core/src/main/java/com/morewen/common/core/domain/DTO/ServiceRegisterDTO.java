package com.morewen.common.core.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Wangbw
 * @date 2020/12/17 23:00
 * @description 服务注册DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRegisterDTO {
    /**
     * 服务名称
     */
    @NonNull
    private String name;
    /**
     * 服务地址
     */
    @NonNull
    private String url;
    /**
     * 服务描述
     */
    private String description;

}
