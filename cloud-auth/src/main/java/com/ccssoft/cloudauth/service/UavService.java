package com.ccssoft.cloudauth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 远程调用有关无人机管理模块的服务
 * @author moriarty
 * @date 2020/6/8 23:33
 */
@Component
@FeignClient(value = "uav-server")
public interface UavService {
}
