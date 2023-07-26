package com.morewen.models.register.service.impl;

import com.morewen.common.core.domain.DTO.ServiceRegisterDTO;
import com.morewen.models.register.mannager.KeepAliveMannager;
import com.morewen.models.register.service.ServiceRegisterService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Wangbw
 * @date 2023/7/25
 * @description 服务注册实现类
 */
@Service
public class ServiceRegisterServiceImpl implements ServiceRegisterService {
    // 服务注册表 key: 服务名 value: 自定超时删除容器 服务地址 + 超时时间 用于心跳检测
    private static Map<String, Map<String, KeepAliveMannager>> serviceMap;
    static {
        serviceMap = new HashMap<>();
    }
    /**
     * 服务注册
     * @param serviceRegisterDTO 服务注册信息
     * @return 结果
     */
    @Override
    public boolean register(ServiceRegisterDTO serviceRegisterDTO) {
        heartBeat();
        // 得到相同名称的服务，遍历查询url是否相同
        Map<String, KeepAliveMannager> stringKeepAliveMannagerMap = serviceMap.get(serviceRegisterDTO.getName());
        if (stringKeepAliveMannagerMap != null && stringKeepAliveMannagerMap.size() > 0) {
            for (Map.Entry<String, KeepAliveMannager> entry : stringKeepAliveMannagerMap.entrySet()) {
                if (entry.getKey().equals(serviceRegisterDTO.getUrl())) {
                    return false;
                }
            }
        }
        // 如果没有相同的服务，或者有相同的服务但是url不同，就注册
        KeepAliveMannager keepAliveMannager = new KeepAliveMannager(new Date(), 60 * 5);
        Map<String, KeepAliveMannager> map = new HashMap<>();
        map.put(serviceRegisterDTO.getUrl(), keepAliveMannager);
        serviceMap.put(serviceRegisterDTO.getName(), map);
        return true;
    }
    /**
     * 服务发现
     * @param serviceName 服务名
     * @return 服务地址
     */
    @Override
    public ServiceRegisterDTO discover(String serviceName) {
        heartBeat();
        // 根据服务名获取服务注册表
        Map<String, KeepAliveMannager> stringKeepAliveMannagerMap = serviceMap.get(serviceName);
        if (stringKeepAliveMannagerMap == null || stringKeepAliveMannagerMap.size() == 0) {
            return null;
        }
        // 负载均衡 从服务注册表中随机获取一个服务地址
        List<String> urls = new ArrayList<>(stringKeepAliveMannagerMap.keySet());
        Random random = new Random();
        String url = urls.get(random.nextInt(urls.size()));
        return new ServiceRegisterDTO(serviceName, url, null);
    }

    /**
     * 心跳检测
     */
    @Override
    public void heartBeat() {
        // 遍历服务注册表，删除超时的服务
        for (Map.Entry<String, Map<String, KeepAliveMannager>> entry : serviceMap.entrySet()) {
            Map<String, KeepAliveMannager> value = entry.getValue();
            Iterator<Map.Entry<String, KeepAliveMannager>> iterator = value.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, KeepAliveMannager> next = iterator.next();
                KeepAliveMannager keepAliveMannager = next.getValue();
                if (keepAliveMannager.isTimeout()) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 延长服务存活时间
     * @param serviceName 服务名
     * @param url 服务地址
     */
    @Override
    public void delay(String serviceName, String url) {
        // 根据服务名获取服务注册表
        Map<String, KeepAliveMannager> stringKeepAliveMannagerMap = serviceMap.get(serviceName);
        if (stringKeepAliveMannagerMap == null || stringKeepAliveMannagerMap.size() == 0) {
            return;
        }
        // 根据url获取自定超时删除容器
        KeepAliveMannager keepAliveMannager = stringKeepAliveMannagerMap.get(url);
        if (keepAliveMannager == null) {
            return;
        }
        // 更新最后一次心跳时间
        keepAliveMannager.updateLastKeepAliveTime();
    }

    @Override
    public void delete(String name, String url) {
        // 根据服务名获取服务注册表
        Map<String, KeepAliveMannager> stringKeepAliveMannagerMap = serviceMap.get(name);
        if (stringKeepAliveMannagerMap == null || stringKeepAliveMannagerMap.size() == 0) {
            return;
        }
        // 根据url获取自定超时删除容器
        stringKeepAliveMannagerMap.remove(url);
    }
}
