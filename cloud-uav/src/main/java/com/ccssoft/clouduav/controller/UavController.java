package com.ccssoft.clouduav.controller;

import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.clouduav.dto.UavDto;
import com.ccssoft.clouduav.entity.Uav;
import com.ccssoft.clouduav.service.UavService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * <p>
 * 无人机设备信息 前端控制器
 * </p>
 *
 * @author moriarty
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/uav")
@Slf4j
public class UavController {
    @Resource
    private UavService uavService;

    @PostMapping("/register")
    public R registerUav(@RequestBody UavDto uavDto) {
        log.info("UavController.registerUav(),参数="+uavDto.toString());
        Uav uav = uavDto.getUav();
        if (uav.getManufacturerName() != null && uav.getUavType() != null && uav.getWeight() != null && uav.getSpeedMax() != null && uavDto.getUserId() != null) {
            return uavService.saveUav(uav,uavDto.getUserId()) == 1 ? R.ok() : R.error(300,"注册失败!");
        }

        return R.error(301,"注册信息不全");
    }

    @GetMapping("/getUav4Page/{current}&{size}")
    public R getUav4Page (@PathVariable("current") int current, @PathVariable("size") int size) {
        log.info("UavController.getUav4Page(),参数="+current+","+size);
        return R.ok(uavService.getUav4Page(current,size));
    }

    @GetMapping("/getUavByUserId4Page/{current}&{size}&{id}")
    public R getUavByUserId4Page (@PathVariable("current") int current, @PathVariable("size") int size ,@PathVariable("id") Long userId) {
        log.info("UavController.getUavByUserId4Page(),参数="+current+","+size+","+userId);
        return R.ok(uavService.getUavByUserId4Page(current,size,userId));
    }

    @GetMapping("/getUavById/{id}")
    public R getUavById (@PathVariable("id") Long uavId) {
        log.info("UavController.getUavById(),参数="+uavId);
        Uav uav = uavService.getUavById(uavId);
        return uav != null ? R.ok(uav) : R.error(301,"不存在此无人机");

    }

    @GetMapping("/deleteUavById/{id}")
    public R deleteUavById (@PathVariable("id") Long uavId) {
        log.info("UavController.deleteUavById(),参数="+uavId);
        return uavService.deleteUavById(uavId) == 1 ? R.ok() : R.error(300,"删除失败！");
    }
}

