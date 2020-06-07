package com.ccssoft.cloudtask.service;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moriarty
 * @date 2020/6/4 15:58
 */
@Component
@FeignClient(value = "airspace-server")//调用哪个微服务
public interface AirspaceService {
    @PostMapping(value = "/airspace/getAirspaceByAirspaceIds")
    public List getAirspaceByAirspaceIds (@RequestParam("idList") ArrayList<Long> list);
}
