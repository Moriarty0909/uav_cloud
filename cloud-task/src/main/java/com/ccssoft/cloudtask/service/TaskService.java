package com.ccssoft.cloudtask.service;

import com.ccssoft.cloudcommon.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.ArrayList;

/**
 * <p>
 * 飞行计划 服务类
 * </p>
 *
 * @author moriarty
 * @since 2020-06-02
 */
public interface TaskService extends IService<Task> {

    /**
     * 创建飞行计划
     * @param task 计划详情
     * @return 是否成功
     */
    int createPlan(Task task);

    /**
     * 通过飞行计划的id获取相应的空域id
     * @param taskId 飞行计划id
     * @return 一组对应的空域id
     */
    ArrayList getAirspaceByTaskId(Long taskId);

    /**
     * 批准飞机计划
     * @param taskId 飞行计划id
     * @return 是否成功
     */
    int updateStatusById(Long taskId);

    /**
     * 更改还没有批准的计划，批准之后不能更改
     * @param task 更改的计划详情
     * @return 是否成功
     */
    int updateInfo(Task task);

}
