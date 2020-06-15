package com.ccssoft.cloudauth.service;

import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

/**
 * 远程调用有关飞行计划管理模块的服务
 * @author moriarty
 * @date 2020/6/8 23:33
 */
@Component
@FeignClient(value = "task-server")
public interface TaskService {

    /**
     * 远程调用飞行计划模块的创建计划功能
     * @param task
     * @return
     */
    @PostMapping("/task/createPlan")
    R createPlan (@Valid @RequestBody Task task);

    /**
     * 远程调用飞行计划模块的更改计划详情功能
     * @param task 更改后的计划详情
     * @return R
     */
    @PostMapping("/task/updateInfo")
    R updateInfo (@Valid @RequestBody Task task);

    /**
     * 远程调用飞行计划模块的批准计划功能
     * @param taskId 计划id
     * @return 成功与否
     */
    @GetMapping("/task/approval/{id}")
    R approval (@PathVariable("id") Long taskId);

    /**
     * 远程调用飞行计划模块的删除飞行计划功能
     * @param taskId 飞行计划id
     * @return R
     */
    @GetMapping("/task/deletePlan/{id}")
    R deletePlan (@PathVariable("id") Long taskId);

    /**
     * 远程调用飞行计划模块的根据飞机计划id查找其对应的空域详情功能
     * @param taskId 飞机计划id
     * @return 空域详情
     */
    @GetMapping("/task/getAirspaceByTaskId/{id}")
    R getAirspaceByTaskId (@PathVariable("id") Long taskId);

    /**
     * 远程调用飞行计划模块的分页获取所有飞机计划数据功能
     * @param current 当前页数
     * @param size 每页数量
     * @return R
     */
    @GetMapping("/task/getAllTask4Page/{current}&{size}")
    R getAllTask4Page (@PathVariable("current") int current, @PathVariable("size") int size);

    /**
     * 远程调用飞行计划模块的分页获取对应该用户的所有飞机计划数据功能
     * @param current 当前页数
     * @param size 每页数量
     * @param userId 用户id
     * @return R
     */
    @GetMapping("/task/getTaskByUserId4Page/{current}&{size}&{userId}")
    R getTaskByUserId4Page (@PathVariable("current") int current, @PathVariable("size") int size, @PathVariable("userId") Long userId);
}
