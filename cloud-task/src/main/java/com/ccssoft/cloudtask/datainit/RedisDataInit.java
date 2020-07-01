package com.ccssoft.cloudtask.datainit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccssoft.cloudcommon.entity.Airspace;
import com.ccssoft.cloudcommon.entity.Task;
import com.ccssoft.cloudtask.dao.TaskAirspaceDao;
import com.ccssoft.cloudtask.dao.TaskDao;
import com.ccssoft.cloudtask.entity.TaskAirspace;
import com.ccssoft.cloudtask.filter.RedisBloomFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author moriarty
 * @date 2020/6/11 14:47
 */
@Component
public class RedisDataInit {
    @Resource
    private RedisBloomFilter redisBloomFilter;

    @Resource
    private TaskDao taskDao;


    @PostConstruct
    public void init () {
        List<Task> tasks = taskDao.selectList(null);
        for (Task task : tasks) {
            redisBloomFilter.put(String.valueOf(task.getId()));
        }

    }
}
