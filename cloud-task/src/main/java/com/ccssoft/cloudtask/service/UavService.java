package com.ccssoft.cloudtask.service;

import com.ccssoft.cloudcommon.entity.Uav;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "uav-server")
public interface UavService {
    @GetMapping("/uav/getUavById/{id}")
    Uav getUavById (@PathVariable("id") Long uavId);

    @GetMapping("/uav/getUavByUserId")
    List<Long> getUavIdByUserId (@RequestParam("userId") Long userId);
}
