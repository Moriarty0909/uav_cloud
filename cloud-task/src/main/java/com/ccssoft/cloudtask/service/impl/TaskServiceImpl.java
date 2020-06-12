package com.ccssoft.cloudtask.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccssoft.cloudtask.dao.TaskAirspaceDao;
import com.ccssoft.cloudcommon.entity.Task;
import com.ccssoft.cloudtask.dao.TaskDao;
import com.ccssoft.cloudtask.entity.TaskAirspace;
import com.ccssoft.cloudtask.filter.RedisBloomFilter;
import com.ccssoft.cloudtask.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccssoft.cloudtask.util.RedisUtil;
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

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisBloomFilter bloomFilter;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int createPlan(Task task) {
        int result = taskDao.insert(task);

        List<Long> list = task.getAirspaceId();
        return insertTaskAirspace(task, result, list);
    }

    @Override
    public ArrayList getAirspaceIdByTaskId(Long taskId) {
        String numId = String.valueOf(taskId);
        if (!bloomFilter.isExist(numId)) {
            return null;
        }

        if (redisUtil.get(numId) == null) {
            QueryWrapper<TaskAirspace> wrapper = new QueryWrapper();
            wrapper.eq("task_id",taskId);
            ArrayList<Long> list = new ArrayList();
            for (TaskAirspace taskAirspace : taskAirspaceDao.selectList(wrapper)) {
                list.add(taskAirspace.getAirspaceId());
            }
            redisUtil.set(numId,list);
            return list;
        }

        JSON parse = JSONUtil.parse(redisUtil.get(numId));
        return parse.toBean(ArrayList.class);

    }

    @Override
    public int updateStatusById(Long taskId) {
        QueryWrapper<Task> wrapper = new QueryWrapper();
        wrapper.eq("id",taskId);
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

        return insertTaskAirspace(task, result, list) ;
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
