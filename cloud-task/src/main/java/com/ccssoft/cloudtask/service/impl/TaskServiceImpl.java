package com.ccssoft.cloudtask.service.impl;

import com.ccssoft.cloudtask.entity.Task;
import com.ccssoft.cloudtask.dao.TaskMapper;
import com.ccssoft.cloudtask.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 飞行计划 服务实现类
 * </p>
 *
 * @author moriarty
 * @since 2020-06-02
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

}
