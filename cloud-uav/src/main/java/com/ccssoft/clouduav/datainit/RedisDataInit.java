package com.ccssoft.clouduav.datainit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccssoft.cloudcommon.entity.Uav;
import com.ccssoft.clouduav.dao.UavDao;
import com.ccssoft.clouduav.dao.UserUavDao;
import com.ccssoft.clouduav.entity.UserUav;
import com.ccssoft.clouduav.filter.RedisBloomFilter;
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
    private UavDao uavDao;

    @Resource
    private UserUavDao userUavDao;

    @PostConstruct
    public void init () {
        List<Uav> uavs = uavDao.selectList(null);
        for (Uav uav : uavs) {
            redisBloomFilter.put(String.valueOf(uav.getId()));
        }
        QueryWrapper<UserUav> wrapper = new QueryWrapper<>();
        wrapper.select("distinct user_id");
        List<UserUav> userUavs = userUavDao.selectList(wrapper);

        for (UserUav userUav : userUavs) {
            redisBloomFilter.put(String.valueOf(userUav.getUserId()));
        }

    }
}
