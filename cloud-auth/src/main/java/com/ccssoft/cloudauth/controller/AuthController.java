package com.ccssoft.cloudauth.controller;

import com.ccssoft.cloudauth.service.AdminService;
import com.ccssoft.cloudauth.service.AirspaceService;
import com.ccssoft.cloudauth.service.TaskService;
import com.ccssoft.cloudauth.service.UavService;
import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.Airspace;
import com.ccssoft.cloudcommon.entity.Task;
import com.ccssoft.cloudcommon.entity.Uav;
import com.ccssoft.cloudcommon.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author moriarty
 * @date 2020/5/26 14:31
 */
@RestController
public class AuthController {
    @Resource
    private AdminService adminService;

    @Resource
    private AirspaceService airspaceService;

    @Resource
    private TaskService taskService;

    @Resource
    private UavService uavService;

    @PostMapping(value = "/consumer/admin/registerUser")
    public R registerUser(@RequestBody User user) {
        return adminService.registerUser(user);
    }

    @PostMapping(value = "/consumer/admin/changePassword")
    public R changePassword(@RequestBody User user) {
        return adminService.changePassword(user);
    }

    @GetMapping(value = "/consumer/admin/deleteUser/{id}")
    public R deleteUser(@PathVariable("id") Long userId) {
        return adminService.delUser(userId);
    }

    @PostMapping(value = "/consumer/admin/updateUser")
    public R updateUser(@RequestBody User user) {
        return adminService.updateInfo(user);
    }

    @GetMapping(value = "/consumer/admin/getUser/{username}")
    public R getUser(@PathVariable("username") String userName) {
        return adminService.getInfo(userName);
    }

    @GetMapping(value = "/consumer/admin/getUser4Page/{current}&{size}")
    public R getUser4Page(@PathVariable("current") int current, @PathVariable("size") int size) {
        return adminService.getUser4Page(current, size);
    }

    //===========空域模块==========

    @PostMapping(value = "/consumer/airspace/registerAirspace")
    public R registerAirspace(@RequestBody Airspace airspace) {
        return airspaceService.registerAirSpace(airspace);
    }

    @GetMapping(value = "/consumer/airspace/deleteAirspace/{id}")
    public R deleteAirspace(@PathVariable("id") Long airspaceId) {
        return airspaceService.deleteAirspace(airspaceId);
    }

    @PostMapping(value = "/consumer/airspace/updateAirspace")
    public R updateAirspace(@RequestBody Airspace airspace) {
        return airspaceService.updateInfo(airspace);
    }

    @GetMapping(value = "/consumer/airspace/approvalAirspace/{id}")
    public R approvalAirspace(@PathVariable("id") Long airspaceId) {
        return airspaceService.approval(airspaceId);
    }

    @GetMapping("/consumer/airspace/getAllAirspaceNotAllow/{current}&{size}")
    public R getAllAirspaceNotAllow(@PathVariable("current") int current ,@PathVariable("size") int size) {
        return airspaceService.getAllAirspaceNotAllow(current,size);
    }

    @GetMapping("/consumer/airspace/getAiarspaceByUserId/{id}&{time}")
    public R getAiarspaceByUserId(@PathVariable("id") Long userId ,@PathVariable("time") Date startTime) {
        return airspaceService.getAirspaceByUserId(userId,startTime);
    }

    @GetMapping("/consumer/airspace/getAirspaceByAirspaceId/{id}")
    public R getAirspaceByAirspaceId(@PathVariable("id") Long AirspaceId) {
        return airspaceService.getAirspaceByAirspaceId(AirspaceId);
    }

    //===============飞行计划模块============

    @PostMapping("/consumer/task/createPlan")
    public R createPlan(@RequestBody Task task) {
        return taskService.createPlan(task);
    }

    @PostMapping("/consumer/task/updatePlan")
    public R updatePlan(@RequestBody Task task) {
        return taskService.updateInfo(task);
    }

    @GetMapping("/consumer/task/approvalTask/{id}")
    public R approvalTask(@PathVariable("id") Long taskId) {
        return taskService.approval(taskId);
    }

    @GetMapping("/consumer/task/deletePlan/{id}")
    public R deletePlan(@PathVariable("id") Long taskId) {
        return taskService.deletePlan(taskId);
    }

    @GetMapping("/consumer/task/getAirspaceByTaskId/{id}")
    public R getAirspaceByTaskId(@PathVariable("id") Long taskId) {
        return taskService.getAirspaceByTaskId(taskId);
    }

    @GetMapping("/consumer/task/getAllTask4Page/{current}&{size}")
    public R getAllTask4Page(@PathVariable("current") int current ,@PathVariable("size") int size) {
        return taskService.getAllTask4Page(current,size);
    }

    @GetMapping("/consumer/task/getTaskByUserId4Page/{current}&{size}&{id}")
    public R getTaskByUserId4Page(@PathVariable("current") int current ,@PathVariable("size") int size ,@PathVariable("id") Long userId) {
        return taskService.getTaskByUserId4Page(current,size,userId);
    }

    //============无人机模块=================

    @PostMapping("/consumer/uav/registerUav")
    public R registerUav(@RequestBody Uav uav) {
        return uavService.registerUav(uav);
    }

    @PostMapping("/consumer/uav/updateUav")
    public R updateUav(@RequestBody Uav uav) {
        return uavService.updateInfo(uav);
    }

    @GetMapping("/consumer/uav/deleteUav/{id}")
    public R deleteUav(@PathVariable("id") Long uavId) {
        return uavService.deleteUavById(uavId);
    }

    @GetMapping("/consumer/uav/getUav4Page/{current}&{size}")
    public R getUav4Page(@PathVariable("current") int current ,@PathVariable("size") int size) {
        return uavService.getUav4Page(current,size);
    }

    @GetMapping("/uav/getUavByUserId4Page/{current}&{size}&{id}")
    public R getUavByUserId4Page(@PathVariable("current") int current ,@PathVariable("size") int size ,@PathVariable("id") Long userId) {
        return uavService.getUavByUserId4Page(current,size,userId);
    }



}
