package com.ccssoft.cloudairspace.controller;

import cn.hutool.core.util.ObjectUtil;
import com.ccssoft.cloudairspace.service.AirspaceService;
import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.Airspace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
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
     * @return R
     */
    @PostMapping("/registerAS")
    public R registerAirSpace (@Valid @RequestBody Airspace airspace) {
        log.info("AirspaceController.registerAirSpace(),参数={}",airspace);
        return airspaceService.registerAirSpace(airspace) == 1 ? R.ok() : R.error(300,"注册失败！");
    }

    /**
     * 修改空域信息
     * @param airspace 空域详情
     * @return 成功与否
     */
    @PostMapping("/updateInfo")
    public R updateInfo (@Valid @RequestBody Airspace airspace) {
        log.info("AirspaceController.updateInfo(),参数={}",airspace);
        return airspaceService.updateAirSpace(airspace) == 1 ? R.ok() : R.error(300,"更新失败！");
    }

    /**
     * 批准空域
     * @param airspaceId 空域id
     * @return R
     */
    @GetMapping("/approval/{id}")
    public R approval (@PathVariable("id") Long airspaceId) {
        log.info("AirspaceController.approval(),参数:airspaceId={}",airspaceId);
        return airspaceService.approvalById(airspaceId) == 1 ?R.ok() :R.error(300,"批准失败！");
    }

    /**
     * 获取所有待审批的空域
     * @param current 当前页数
     * @param size 每页数据量
     * @return R
     */
    @GetMapping("/getAllAirspaceNotAllow/{current}&{size}")
    public R getAllAirspaceNotAllow (@PathVariable("current") int current, @PathVariable("size") int size) {
        log.info("AirspaceController.getAllAirspaceNotAllow(),参数：当前页数={},每页数量={}",current,size);
        return R.ok(airspaceService.getAllAirspaceNotAllow(current,size));
    }


    /**
     * 批量查询符合条件的所有空域，直接查出和此用户相关的空域
     * @param userId 用户id
     * @return R
     */
    @GetMapping("/getASByUserId/{id}")
    public R getAirspaceByUserId (@PathVariable("id") Long userId) {
        log.info("AirspaceController.getASByUserId(),参数={}。",userId);
        List<Airspace> list = airspaceService.getAirspaceByUserId(userId);
        return ObjectUtil.length(list) != 0 ? R.ok(list) : R.error(301, "无查询数据！");
    }

    /**
     * 批量查询符合条件的所有空域
     * @param userId 用户id
     * @param date 查出和此用户相关的空域并且还需要比对是否在空域使用时间的起始范围内
     * @return R
     */
    @GetMapping("/getASByUserId/{id}&{time}")
    public R getAirspaceByUserId (@PathVariable("id") Long userId,@PathVariable("time") Date date) {

        log.info("AirspaceController.getASByUserId(),参数={},{}",userId,date);
        List<Airspace> asByUserIdPremiseTime = airspaceService.getAirspaceByUserIdPremiseTime(userId, date);
        return ObjectUtil.length(asByUserIdPremiseTime) != 0 ? R.ok(asByUserIdPremiseTime) : R.error(301,"无查询数据！");
    }

    /**
     * 单个获取符合空域id的空域信息
     * @param airspaceId 空域id
     * @return R
     */
    @GetMapping("/getAirspaceByAirspaceId/{id}")
    public R getAirspaceByAirspaceId (@PathVariable("id") Long airspaceId) {
        log.info("AirspaceController.getAirspaceByAirspaceId(),参数={}",airspaceId);
        Airspace airspace = airspaceService.getAirspaceByAirspaceId(airspaceId);
        return airspace != null ? R.ok(airspace) : R.error(301,"无查询数据！");
    }

    /**
     * 删除空域
     * @param airspaceId 空域id
     * @return 是否成功
     */
    @GetMapping("/deleteAirspace/{id}")
    public R deleteAirspace (@PathVariable("id") Long airspaceId) {
        log.info("AirspaceController.deleteAirspace(),参数:airspaceId={}",airspaceId);
        return airspaceService.removeById(airspaceId) ? R.ok() :R.error(300,"删除失败！");
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

