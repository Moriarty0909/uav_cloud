package com.ccssoft.cloudauth.service;

import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.Airspace;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;

/**
 * 远程调用有关空域管理模块的服务
 * @author moriarty
 * @date 2020/6/8 23:33
 */
@Component
@FeignClient(value = "airspace-server")
public interface AirspaceService {
    /**
     * 远程调用有关空域管理模块的注册空域功能
     * @param airspace 空域详细信息
     * @return R
     */
    @PostMapping("/airspace/registerAS")
    R registerAirSpace (@Valid @RequestBody Airspace airspace);

    /**
     * 远程调用有关空域管理模块的修改空域信息功能
     * @param airspace 空域详情
     * @return 成功与否
     */
    @PostMapping("/airspace/updateInfo")
    R updateInfo (@Valid @RequestBody Airspace airspace);

    /**
     * 远程调用有关空域管理模块的批准空域功能
     * @param airspaceId 空域id
     * @return R
     */
    @GetMapping("/airspace/approval/{id}")
    R approval (@PathVariable("id") Long airspaceId);

    /**
     * 远程调用有关空域管理模块的获取所有待审批的空域功能
     * @param current 当前页数
     * @param size 每页数据量
     * @return R
     */
    @GetMapping("/airspace/getAllAirspaceNotAllow/{current}&{size}")
    R getAllAirspaceNotAllow (@PathVariable("current") int current, @PathVariable("size") int size);


    /**
     * 远程调用有关空域管理模块的批量查询符合条件的所有空域功能
     * @param userId 用户id
     * @param date 如果时间为null就直接查出和此用户相关的空域，如果有时间时，还需要比对是否在空域的起始范围内，直接传Date或null
     * @return R
     */
    @GetMapping("/airspace/getASByUserId/{id}&{time}")
    R getAirspaceByUserId (@PathVariable("id") Long userId,@PathVariable("time") Date date);

    /**
     * 远程调用有关空域管理模块的单个获取符合空域id的空域信息功能
     * @param airspaceId 空域id
     * @return R
     */
    @GetMapping("/airspace/getAirspaceByAirspaceId/{id}")
    R getAirspaceByAirspaceId (@PathVariable("id") Long airspaceId);

    /**
     * 远程调用有关空域管理模块的删除空域功能
     * @param airspaceId 空域id
     * @return 是否成功
     */
    @GetMapping("/airspace/deleteAirspace/{id}")
    R deleteAirspace (@PathVariable("id") Long airspaceId);
}
