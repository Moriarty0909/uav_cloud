package com.ccssoft.cloudairspace.datainit;

import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccssoft.cloudairspace.dao.AirspaceDao;
import com.ccssoft.cloudairspace.dao.UserAirspaceDao;
import com.ccssoft.cloudairspace.entity.UserAirspace;
import com.ccssoft.cloudairspace.filter.RedisBloomFilter;
import com.ccssoft.cloudcommon.entity.Airspace;
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
    private AirspaceDao airspaceDao;

    @Resource
    private UserAirspaceDao userAirspaceDao;

    @PostConstruct
    public void init () {
        List<Airspace> airspaces = airspaceDao.selectList(null);
        QueryWrapper<UserAirspace> wrapper = new QueryWrapper();
        wrapper.select("distinct user_id");
        List<UserAirspace> userAirspaces = userAirspaceDao.selectList(wrapper);
        for (Airspace airspace : airspaces) {
            redisBloomFilter.put(String.valueOf(airspace.getId()));
        }
        for (UserAirspace userAirspace : userAirspaces) {
            redisBloomFilter.put(String.valueOf(userAirspace.getUserId()));
        }
    }
}
