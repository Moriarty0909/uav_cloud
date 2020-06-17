package com.ccssoft.cloudauth.service;

import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.Uav;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * 远程调用有关无人机管理模块的服务
 * @author moriarty
 * @date 2020/6/8 23:33
 */
@Component
@FeignClient(value = "uav-server")
public interface UavService {
    /**
     * 远程调用无人机模块的注册无人机功能
     * @param uav 无人机详情
     * @return 是否成功
     */
    @PostMapping("/uav/register")
    R registerUav(@Valid @RequestBody Uav uav);

    /**
     * 远程调用无人机模块的修改无人机信息功能
     * @param uav 无人机信息
     * @return 成功与否
     */
    @PostMapping("/uav/updateInfo")
    R updateInfo (@Valid @RequestBody Uav uav);

    /**
     * 远程调用无人机模块的获取所有在册无人机分页数据功能
     * @param current 当前页
     * @param size 每页数据量
     * @return 分页数据
     */
    @GetMapping("/uav/getUav4Page/{current}&{size}")
    R getUav4Page (@PathVariable("current") int current, @PathVariable("size") int size);

    /**
     * 远程调用无人机模块的根据用户id来获取其本身的无人机列表分页数据功能
     * @param current 当前页
     * @param size 每页数据量
     * @param userId 用户id
     * @return 分页数据
     */
    @GetMapping("/uav/getUavByUserId4Page/{current}&{size}&{id}")
    R getUavByUserId4Page (@PathVariable("current") int current, @PathVariable("size") int size ,@PathVariable("id") Long userId);

    /**
     * 远程调用无人机模块的删除无人机功能
     * @param uavId 无人机id
     * @return 是否成功
     */
    @GetMapping("/uav/deleteUavById/{id}")
    R deleteUavById (@PathVariable("id") Long uavId);

    /**
     * 远程调用无人机模块的删除无人机功能
     * @param uavId 无人机id
     * @return 是否成功
     */
    @GetMapping("/uav/getUavById/{id}")
    Uav getUavById (@PathVariable("id") Long uavId);

    /**
     * 获取所有无人机的数量
     * @return 数量
     */
    @GetMapping("/uav/getUavCount")
    R getUavCount ();
}
