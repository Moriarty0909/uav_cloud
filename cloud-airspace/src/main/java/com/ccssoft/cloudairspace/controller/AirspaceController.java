package com.ccssoft.cloudairspace.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjectUtil;
import com.ccssoft.cloudairspace.entity.Airspace;
import com.ccssoft.cloudairspace.service.AirspaceService;
import com.ccssoft.cloudcommon.common.utils.R;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sun.tools.jconsole.JConsole;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 空域信息 前端控制器
 * </p>
 *
 * @author moriarty
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/airspace")
@Slf4j
public class AirspaceController {
    @Resource
    private AirspaceService airspaceService;

    /**
     * 注册空域
     * @param airspace 空域详细信息
     * @return
     */
    @PostMapping("/registerAS")
    public R registerAirSpace (@RequestBody Airspace airspace) {
        log.info("AirspaceController.registerAirSpace(),参数={}",airspace);
        return airspaceService.registerAirSpace(airspace) == 1 ? R.ok() : R.error(300,"注册失败！");
    }

    /**
     * 批准空域
     * @param id 空域id
     * @return
     */
    @GetMapping("/approval/{id}")
    public R approval (@PathVariable("id") Long id) {
        log.info("AirspaceController.approval(),参数={}",id);
        return airspaceService.approvalById(id) == 1 ?R.ok() :R.error(300,"批准失败！");
    }

    /**
     * 获取所有待审批的空域
     */
    @GetMapping("/getAllAirspaceNotAllow")
    public R getAllAirspaceNotAllow () {
        log.info("AirspaceController.getAllAirspaceNotAllow()");
        return R.ok(airspaceService.getAllAirspaceNotAllow());
    }


    /**
     * 批量查询符合条件的所有空域
     * @param userId 用户id
     * @param date 如果时间为null就直接查处和此用户相关的空域，如果有时间时，还需要比对是否在空域的起始范围内
     * @return
     */
    @GetMapping("/getASByUserId/{id}&{time}")
    public R getASByUserId (@PathVariable("id") Long userId,@PathVariable("time") Date date) {
        log.info("AirspaceController.getASByUserId(),参数={},{}",userId,date);
        if (date != null) {
            List<Airspace> asByUserIdPremiseTime = airspaceService.getASByUserIdPremiseTime(userId, date);

            return ObjectUtil.length(asByUserIdPremiseTime) != 0 ? R.ok(asByUserIdPremiseTime) : R.error(301,"无查询数据！");
        } else {
            List list = airspaceService.getASByUserId(userId);
            return ObjectUtil.length(list) != 0 ? R.ok(list) : R.error(301, "无查询数据！");
        }
    }

    /**
     * 单个获取符合空域id的空域信息
     * @param airspaceId 空域id
     * @return
     */
    @GetMapping("/getAirspaceByAirspaceId/{id}")
    public R getAirspaceByAirspaceId (@PathVariable("id") Long airspaceId) {
        log.info("AirspaceController.getAirspaceByAirspaceId(),参数={}",airspaceId);
        Airspace airspace = airspaceService.getAirspaceByAirspaceId(airspaceId);
        return airspace != null ? R.ok(airspace) : R.error(301,"无查询数据！");
    }

    /**
     * 可以让task模块远程调用，通过一组id获取相对应的空域详情
     * @param list 一组空域id
     * @return 一组Airspace的list
     */
    @PostMapping("/getAirspaceByAirspaceIds")
    public List<Airspace> getAirspaceByAirspaceIds (@RequestParam("idList") ArrayList<Long> list) {
        log.info("AirspaceController.getAirspaceByAirspaceIds(),参数={}",list);
        return airspaceService.getAirspaceByAirspaceIds(list);
    }

}

