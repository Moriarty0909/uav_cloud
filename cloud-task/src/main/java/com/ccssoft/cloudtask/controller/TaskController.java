package com.ccssoft.cloudtask.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.Airspace;
import com.ccssoft.cloudcommon.entity.Task;
import com.ccssoft.cloudcommon.entity.Uav;
import com.ccssoft.cloudtask.entity.TaskNatrue;
import com.ccssoft.cloudtask.filter.RedisBloomFilter;
import com.ccssoft.cloudtask.service.AirspaceService;
import com.ccssoft.cloudtask.service.TaskNatrueService;
import com.ccssoft.cloudtask.service.TaskService;
import com.ccssoft.cloudtask.service.UavService;
import com.ccssoft.cloudtask.util.RedisUtil;
import com.ccssoft.cloudtask.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    @Resource
    private UavService uavService;

    @Resource
    private TaskNatrueService taskNatrueService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisBloomFilter bloomFilter;

    //TODO 是否需要一个判断飞机飞行中返回时间是否有在任意计划内的告警提示

    /**
     * 创建计划
     * @param task 计划详细信息
     * @return 返回状态码和数据
     */
    @PostMapping("/createPlan")
    public R createPlan (@Valid @RequestBody Task task) {

        log.info("TaskController.createPlan(),参数={}",task);
        int result = taskService.createPlan(task);
        return result == 1 ? R.ok() : R.error(301,"关系列表创建失败！id="+result) ;
    }

    /**
     * 更改计划详情
     * @param task 更改后的计划详情
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

        ArrayList airspaceIdByTaskId = taskService.getAirspaceIdByTaskId(taskId);
        if (airspaceIdByTaskId == null) {
            return R.error(301,"taskId不存在！");
        }
        //调用远程服务
        List list = airspaceService.getAirspaceByAirspaceIds(airspaceIdByTaskId);
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
        return R.ok(getTheVoPage(current,size,null));
    }

    /**
     * 分页获取对应用户所有飞行计划数据
     * @param current 当前页数
     * @param size 每页数量
     * @param userId 用户id
     * @return R
     */
    @GetMapping("/getTaskByUserId4Page/{current}&{size}&{userId}")
    public R getTaskByUserId4Page (@PathVariable("current") int current, @PathVariable("size") int size, @PathVariable("userId") Long userId) {
        log.info("TaskController.getAllTask4Page(),参数:当前页数={}，一页{}个数据,用户id{}",current,size,userId);
        //获取user对应的task
        List<Long> uavIds = uavService.getUavIdByUserId(userId);
        return uavIds != null ? R.ok(getTheVoPage(current,size,uavIds)) : R.error(301,"此用户id下没有对应的无人机");
    }

    /**
     * 获取已经批复的飞行计划总数
     * @return 数量
     */
    @GetMapping("/getApprovaledCount")
    public R getApprovaledCount () {
        log.info("TaskController.getApprovaledCount()");
        return R.ok(taskService.getApprovaledCount());
    }

    /**
     * 获取未批复的飞行计划总数
     * @return 数量
     */
    @GetMapping("/getNoApprovaledCount")
    public R getNoApprovaledCount () {
        log.info("TaskController.getNoApprovaledCount()");
        return R.ok(taskService.getNoApprovaledCount());
    }

    @GetMapping("/getNatrue")
    public R getNatrueName () {
        log.info("TaskController.getNatrueName()");
        return R.ok(taskService.getNatrueName());
    }


    private Page getTheVoPage (int current, int size,List uavIds) {
        QueryWrapper<Task> wrapper = new QueryWrapper();
        Page<Task> page = new Page<>(current,size);
        //判断是有用户需求的还是无需求的
        if (uavIds == null) {
            taskService.page(page,null);
        } else {
            wrapper.in("uav_id",uavIds);
            taskService.page(page,wrapper);
        }

        //根据获得的计划数据进行拆分调用组装数据到vo里
        List<TaskVo> list = new ArrayList<>();
        for (Task task : page.getRecords()) {
            TaskVo taskVo = new TaskVo();
            BeanUtils.copyProperties(task,taskVo);
            //获取空域的名称
            ArrayList airspaceIdByTaskId = taskService.getAirspaceIdByTaskId(task.getId());
            List<Airspace> airSpaceList = airspaceService.getAirspaceByAirspaceIds(airspaceIdByTaskId);
            List nameList = new ArrayList();
            for (Airspace airspace : airSpaceList) {
                nameList.add(airspace.getAirspaceName());
            }
            taskVo.setAirspaceName(nameList);
            //获取无人机的名称
            //TODO 这里应该也可以加上缓存
            Uav uav = uavService.getUavById(task.getUavId());
            taskVo.setUavName(uav.getNickname());

            //获取用途的名称
            String taskNatureId = String.valueOf(task.getTaskNatureId());
            if (redisUtil.get(taskNatureId) == null) {
                TaskNatrue taskNatrue = taskNatrueService.getById(task.getTaskNatureId());
                redisUtil.set(taskNatureId,taskNatrue);
                taskVo.setNatrueName(taskNatrue.getTaskNatrueName());
            } else {
                taskVo.setNatrueName(JSONUtil.parse(redisUtil.get(taskNatureId)).toBean(TaskNatrue.class).getTaskNatrueName());
            }

            list.add(taskVo);
        }
        //复制并重新装配
        Page<TaskVo> pageNeed = new Page<>();
        BeanUtils.copyProperties(page,pageNeed);
        pageNeed.setRecords(list);
        return pageNeed;
    }

}

