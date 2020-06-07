package com.ccssoft.cloudtask.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudtask.dao.TaskAirspaceDao;
import com.ccssoft.cloudtask.entity.Task;
import com.ccssoft.cloudtask.dao.TaskDao;
import com.ccssoft.cloudtask.entity.TaskAirspace;
import com.ccssoft.cloudtask.service.AirspaceService;
import com.ccssoft.cloudtask.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 飞行计划 服务实现类
 * </p>
 *
 * @author moriarty
 * @since 2020-06-02
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskDao, Task> implements TaskService {
    @Resource
    private TaskDao taskDao;

    @Resource
    private TaskAirspaceDao taskAirspaceDao;


    @Override
    @Transactional(rollbackFor=Exception.class) //开启事务管理
    public int createPlan(Task task) {
        task.setDeleted(1);
        int result = taskDao.insert(task);
        List<Long> list = task.getAirspaceId();
        return insertTaskAirspace(task, result, list);
    }

    @Override
    public ArrayList getAirspaceByTaskId(Long taskId) {
        QueryWrapper<TaskAirspace> wrapper = new QueryWrapper();
        wrapper.eq("task_id",taskId);
        ArrayList<Long> list = new ArrayList();
        for (TaskAirspace taskAirspace : taskAirspaceDao.selectList(wrapper)) {
            list.add(taskAirspace.getAirspaceId());
        }

        return list;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int updateStatusById(Long taskId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("task_id",taskId);
        return taskDao.updateById(taskDao.selectOne(wrapper).setStatus(1)) == 1 ? 1:0;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int updateInfo(Task task) {
        if (task.getStatus() == 1) {
            return 0;
        }
        int result = taskDao.updateById(task);
        List<Long> list = task.getAirspaceId();

        //考虑到空域关系可能多一条也可能少一条，还是删了重建比较方便
        taskAirspaceDao.deleteById(task.getId());
        return insertTaskAirspace(task, result, list);
    }

    private int insertTaskAirspace(Task task, int result, List<Long> list) {
        if (ObjectUtil.length(list) > 1 && result == 1) {
            for (Long id : list) {
                taskAirspaceDao.insert(new TaskAirspace(task.getId(),id));
            }
            return 1;
        }
        return result == 1 &&  taskAirspaceDao.insert(new TaskAirspace(task.getId(),list.get(0)))== 1 ? 1 : 0;
    }
}
