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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author moriarty
 * @date 2020/5/26 14:31
 */
@RestController
@Api(description = "鉴权跳转控制器")
public class AuthController {
    @Resource
    private AdminService adminService;

    @Resource
    private AirspaceService airspaceService;

    @Resource
    private TaskService taskService;

    @Resource
    private UavService uavService;


    @GetMapping("/consumer/admin/verificationCode")
    public R getVerificationCode () {
        return adminService.getVerificationCode();
    }

    @ApiOperation("注册新用户")
    @PostMapping(value = "/consumer/admin/registerUser")
    public R registerUser(@ApiParam("包含用户信息的实体类") @RequestBody User user) {
        return adminService.registerUser(user);
    }

    @ApiOperation("修改用户密码")
    @PostMapping(value = "/consumer/admin/changePassword")
    public R changePassword(@ApiParam("其余资料可以通过get默认获取，把对应修改的密码加入里面就行") @RequestBody User user) {
        return adminService.changePassword(user);
    }

    @ApiOperation("禁用用户，只有管理员角色可以执行。")
    @GetMapping(value = "/consumer/admin/deleteUser/{id}")
    public R deleteUser(@ApiParam("对应需要禁用的用户id") @PathVariable("id") Long userId) {
        return adminService.delUser(userId);
    }

    @ApiOperation("修改用户信息")
    @PostMapping(value = "/consumer/admin/updateUser")
    public R updateUser(@ApiParam("用户名密码可以通过get默认获取，把对应需修改的资料加入里面就行") @RequestBody User user) {
        return adminService.updateInfo(user);
    }

    @ApiOperation("获取单个用户详情")
    @GetMapping(value = "/consumer/admin/getUser/{username}")
    public R getUser(@ApiParam("对应需获取用户详情的用户名") @PathVariable("username") String userName) {
        return adminService.getInfo(userName);
    }

    @ApiOperation("获取游客数量总数")
    @GetMapping(value = "/consumer/admin/getUserCount")
    public R getUserCount() {
        return adminService.getUserCount();
    }

    @ApiOperation("以分页的形式获取所有的游客用户信息")
    @GetMapping(value = "/consumer/admin/getUser4Page/{current}&{size}")
    public R getUser4Page(@ApiParam("当前页数") @PathVariable("current") int current,
                          @ApiParam("每页数据量") @PathVariable("size") int size) {
        return adminService.getUser4Page(current, size);
    }

    //===========空域模块==========

    @ApiOperation("提交注册新空域信息")
    @PostMapping(value = "/consumer/airspace/registerAirspace")
    public R registerAirspace(@ApiParam("包含空域信息的实体类") @RequestBody Airspace airspace) {
        return airspaceService.registerAirSpace(airspace);
    }

    @ApiOperation("删除空域")
    @GetMapping(value = "/consumer/airspace/deleteAirspace/{id}")
    public R deleteAirspace(@ApiParam("需要删除的空域id") @PathVariable("id") Long airspaceId) {
        return airspaceService.deleteAirspace(airspaceId);
    }

    @ApiOperation("修改空域信息，且如果该空域已经被批准会重新转为未批准状态。")
    @PostMapping(value = "/consumer/airspace/updateAirspace")
    public R updateAirspace(@ApiParam("需要修改的空域信息，其余信息可以通过get方法获得") @RequestBody Airspace airspace) {
        return airspaceService.updateInfo(airspace);
    }

    @ApiOperation("批准空域，只能由管理员操作")
    @GetMapping(value = "/consumer/airspace/approvalAirspace/{id}")
    public R approvalAirspace(@ApiParam("需要批准的空域id") @PathVariable("id") Long airspaceId) {
        return airspaceService.approval(airspaceId);
    }

    @ApiOperation("以分页形式获取所有的还未批准的空域")
    @GetMapping("/consumer/airspace/getAllAirspaceNotAllow/{current}&{size}")
    public R getAllAirspaceNotAllow(@ApiParam("当前页数") @PathVariable("current") int current ,
                                    @ApiParam("每页数据量") @PathVariable("size") int size) {
        return airspaceService.getAllAirspaceNotAllow(current,size);
    }

    @ApiOperation("通过用户id获取其相关的空域信息，以供创建飞行计划时提供可选的空域")
    @GetMapping("/consumer/airspace/getAiarspaceByUserId/{id}&{time}")
    public R getAiarspaceByUserId(@ApiParam("用户id") @PathVariable("id") Long userId ,
                                  @ApiParam("飞行计划开始时间") @PathVariable("time") Date startTime) {
        return airspaceService.getAirspaceByUserId(userId,startTime);
    }

    @ApiOperation("通过用户id获取其相关的空域信息，以用户查看自己所拥有的所有空域")
    @GetMapping("/consumer/airspace/getAiarspaceByUserId/{id}")
    public R getAiarspaceByUserId(@ApiParam("用户id") @PathVariable("id") Long userId) {
        R airspaceByUserId = airspaceService.getAirspaceByUserId(userId);
        return airspaceByUserId;
    }

    @ApiOperation("通过空域id获取其对应的空域详情")
    @GetMapping("/consumer/airspace/getAirspaceByAirspaceId/{id}")
    public R getAirspaceByAirspaceId(@ApiParam("空域id") @PathVariable("id") Long AirspaceId) {
        return airspaceService.getAirspaceByAirspaceId(AirspaceId);
    }

    @ApiOperation("获取已批复了的空域数量，以供页面展示")
    @GetMapping("/consumer/airspace/getApprovaledCount")
    public R getApprovaledCount() {
        return airspaceService.getApprovaledCount();
    }

    @ApiOperation("获取未批复的空域数量，以供页面展示")
    @GetMapping("/consumer/airspace/getNoApprovaledCount")
    public R getNoApprovaledCount() {
        return airspaceService.getNoApprovaledCount();
    }

    //===============飞行计划模块============

    @ApiOperation("创建飞行计划")
    @PostMapping("/consumer/task/createPlan")
    public R createPlan(@ApiParam("包含飞行计划详情的实体类") @RequestBody Task task) {
        return taskService.createPlan(task);
    }

    @ApiOperation("更新飞行计划信息，批准之前可以修改，之后不能在修改。")
    @PostMapping("/consumer/task/updatePlan")
    public R updatePlan(@ApiParam("包含飞行计划更改之后的实体类，其余信息可以通过get方法获取") @RequestBody Task task) {
        return taskService.updateInfo(task);
    }

    @ApiOperation("批准飞行计划")
    @GetMapping("/consumer/task/approvalTask/{id}")
    public R approvalTask(@ApiParam("飞行计划的id") @PathVariable("id") Long taskId) {
        return taskService.approval(taskId);
    }

    @ApiOperation("删除飞行计划")
    @GetMapping("/consumer/task/deletePlan/{id}")
    public R deletePlan(@ApiParam("飞行计划的id") @PathVariable("id") Long taskId) {
        return taskService.deletePlan(taskId);
    }

    @ApiOperation("获取单个飞行计划详情，以供更改飞行计划时获取原始数据")
    @GetMapping("/consumer/task/getPlan/{id}")
    public R getPlan(@ApiParam("飞行计划的id") @PathVariable("id") Long taskId) {
        System.out.println("传过来的id有问题="+taskId);
        return taskService.getPlan(taskId);
    }


    @ApiOperation("获取飞行计划对应的空域详情，以供展示页能获取到该任务对应的空域名称与点击之后的详情")
    @GetMapping("/consumer/task/getAirspaceByTaskId/{id}")
    public R getAirspaceByTaskId(@ApiParam("飞行任务id") @PathVariable("id") Long taskId) {
        return taskService.getAirspaceByTaskId(taskId);
    }

    @ApiOperation("以分页的形式获取所有的飞行计划，以供管理员查看管理")
    @GetMapping("/consumer/task/getAllTask4Page/{current}&{size}")
    public R getAllTask4Page(@ApiParam("当前页数") @PathVariable("current") int current ,
                             @ApiParam("每页数据量") @PathVariable("size") int size) {
        return taskService.getAllTask4Page(current,size);
    }

    @ApiOperation("以分页的形式获取对应用户下的所有的飞行计划，以供用户管理查看")
    @GetMapping("/consumer/task/getTaskByUserId4Page/{current}&{size}&{id}")
    public R getTaskByUserId4Page(@ApiParam("当前页数") @PathVariable("current") int current ,
                                  @ApiParam("每页数据量") @PathVariable("size") int size ,
                                  @ApiParam("对应的用户id") @PathVariable("id") Long userId) {
        return taskService.getTaskByUserId4Page(current,size,userId);
    }

    @ApiOperation("获取飞行计划已批准的任务数，供展示页面使用")
    @GetMapping("/consumer/task/getApprovaledCount")
    public R getTaskApprovaledCount() {
        return taskService.getApprovaledCount();
    }

    @ApiOperation("获取飞行计划未批准的任务数，供展示页面使用")
    @GetMapping("/consumer/task/getNoApprovaledCount")
    public R getTaskNoApprovaledCount() {
        return taskService.getNoApprovaledCount();
    }

    @ApiOperation("获取所有的用途名称，供前端页面展示")
    @GetMapping("/consumer/task/getNatrue")
    public R getNatrue() {
        return taskService.getNatrueName();
    }

    //============无人机模块=================

    @ApiOperation("注册无人机")
    @PostMapping("/consumer/uav/registerUav")
    public R registerUav(@ApiParam("无人机详情的实体类") @RequestBody Uav uav) {
        return uavService.registerUav(uav);
    }

    @ApiOperation("更新无人机信息")
    @PostMapping("/consumer/uav/updateUav")
    public R updateUav(@ApiParam("无人机详情的实体类") @RequestBody Uav uav) {
        return uavService.updateInfo(uav);
    }

    @ApiOperation("删除无人机")
    @GetMapping("/consumer/uav/deleteUav/{id}")
    public R deleteUav(@ApiParam("对应的无人机id") @PathVariable("id") Long uavId) {
        return uavService.deleteUavById(uavId);
    }

    @ApiOperation("以分页形式获取所有的无人机数据")
    @GetMapping("/consumer/uav/getUav4Page/{current}&{size}")
    public R getUav4Page(@ApiParam("当前页数") @PathVariable("current") int current ,
                         @ApiParam("每页数据量") @PathVariable("size") int size) {
        return uavService.getUav4Page(current,size);
    }


    @ApiOperation("以分页形式获取对应各自用户id的无人机数据")
    @GetMapping("/consumer/uav/getUavByUserId4Page/{current}&{size}&{id}")
    public R getUavByUserId4Page(@ApiParam("当前页数") @PathVariable("current") int current ,
                                 @ApiParam("每页数据量") @PathVariable("size") int size ,
                                 @ApiParam("用户id") @PathVariable("id") Long userId) {
        return uavService.getUavByUserId4Page(current,size,userId);
    }

    @ApiOperation("以list形式获取对应各自用户id的无人机数据")
    @GetMapping("/consumer/uav/getUavsByUserId/{id}")
    public R getUavsByUserId(@ApiParam("用户id") @PathVariable("id") Long userId) {
        List uavIdByUserId = uavService.getUavsByUserId(userId);
        return uavIdByUserId.size() != 0 ? R.ok(uavIdByUserId) : R.error(301,"无此数据！");
    }

    @ApiOperation("获取单个无人机详情")
    @GetMapping("/consumer/uav/getUavByUavId/{id}")
    public R getUavByUavId(@ApiParam("无人机id") @PathVariable("id") Long uavId) {
        Uav uav = uavService.getUavById(uavId);
        return uav != null ? R.ok(uav) : R.error(301,"无此无人机！");
    }

    @ApiOperation("获取所有无人机数量")
    @GetMapping("/consumer/uav/getUavCount")
    public R getUavCount() {
        return uavService.getUavCount();
    }
}
