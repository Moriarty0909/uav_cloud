package com.ccssoft.cloudauth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 远程调用有关空域管理模块的服务
 * @author moriarty
 * @date 2020/6/8 23:33
 */
@Component
@FeignClient(value = "airspace-server")
public interface AirspaceService {
}
