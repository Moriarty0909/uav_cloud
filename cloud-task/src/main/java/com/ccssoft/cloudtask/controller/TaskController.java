package com.ccssoft.cloudtask.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudtask.entity.Task;
import com.ccssoft.cloudtask.service.AirspaceService;
import com.ccssoft.cloudtask.service.TaskService;
import com.ccssoft.cloudtask.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 飞行计划 前端控制器
 * </p>
 *
 * @author moriarty
 * @since 2020-06-02
 */
@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {
    /*
    1.参数校验
    2.调用service层接口实现业务逻辑
    3.转换业务／数据对象
    4.组装返回对象
    5.异常处理
     */

    @Resource
    private TaskService taskService;

    @Resource
    private AirspaceService airspaceService;

    //TODO 是否需要一个判断飞机飞行返回时间是否有在任意计划内的告警提示

    /**
     * 创建计划
     * @param task 计划详细信息
     * @return 返回状态码和数据
     */
    @PostMapping("/createPlan")
    public R createPlan (@Valid @RequestBody Task task) {
        log.info("TaskController.createPlan(),参数={}",task);
        int result = taskService.createPlan(task);
        return result == 1 ? R.ok() : R.error(301,"关系列表创建失败！id="+result);
    }

    /**
     * 更改计划详情
     * @param task
     * @return R
     */
    @PostMapping("/updateInfo")
    public R updateInfo (@Valid @RequestBody Task task) {
        log.info("TaskController.updateInfo(),参数={}",task);
        return taskService.updateInfo(task) == 1 ? R.ok() : R.error(301,"修改失败或已批准！");
    }

    /**
     * 批准计划
     * @param taskId 计划id
     * @return 成功与否
     */
    @GetMapping("/approval/{id}")
    public R approval (@PathVariable("id") Long taskId) {
        log.info("TaskController.approval(),参数：taskId={}",taskId);
        return taskService.updateStatusById(taskId) ==1 ? R.ok() : R.error(301,"批准失败！");
    }

    /**
     * 删除飞行计划
     * @param taskId 飞行计划id
     * @return R
     */
    @GetMapping("/deletePlan/{id}")
    public R deletePlan (@PathVariable("id") Long taskId) {
        log.info("TaskController.updateInfo(),参数={}",taskId);
        return taskService.removeById(taskId) ? R.ok() : R.error(300,"删除失败！");
    }

    /**
     * 根据飞机计划id查找其对应的空域详情
     * @param taskId 飞机计划id
     * @return 空域详情
     */
    @GetMapping("/getAirspaceByTaskId/{id}")
    public R getAirspaceByTaskId (@PathVariable("id") Long taskId) {
        log.info("TaskController.createPlan(),参数:taskId={}",taskId);
        //调用远程服务
        List list = airspaceService.getAirspaceByAirspaceIds(taskService.getAirspaceByTaskId(taskId));
        return ObjectUtil.length(list) > 0 ? R.ok(list) : R.error(301,"无此查询数据！");
    }

    /**
     * 分页获取所有飞机计划数据
     * @param current 当前页数
     * @param size 每页数量
     * @return R
     */
    @GetMapping("/getAllTask4Page/{current}&{size}")
    public R getAllTask4Page (@PathVariable("current") int current, @PathVariable("size") int size) {
        log.info("TaskController.getAllTask4Page(),参数:当前页数={}，一页{}个数据.",current,size);

        Page<Task> page = new Page<>(current,size);
        taskService.page(page,null);
        List<Task> records = page.getRecords();
        for (Task task : records) {
            TaskVo taskVo = new TaskVo();
            BeanUtils.copyProperties(task,taskVo);
            //TODO 调用远程方法查询对应的无人机名称，空域名称，用途set进vo里返回前端页面
            airspaceService
            taskVo.setUavName("gogogo");

        }




        return R.ok(taskVo);
    }

}

