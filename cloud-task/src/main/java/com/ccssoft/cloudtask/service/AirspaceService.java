package com.ccssoft.cloudtask.service;

import com.ccssoft.cloudcommon.entity.Airspace;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
    List<Airspace> getAirspaceByAirspaceIds (@RequestParam("idList") ArrayList<Long> list);
}
