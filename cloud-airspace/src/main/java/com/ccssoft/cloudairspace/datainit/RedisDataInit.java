package com.ccssoft.cloudairspace.datainit;

import com.ccssoft.cloudairspace.dao.AirspaceDao;
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

    @PostConstruct
    public void init () {
        List<Airspace> airspaces = airspaceDao.selectList(null);
        for (Airspace airspace : airspaces) {
            redisBloomFilter.put(String.valueOf(airspace.getId()));
        }
    }
}
