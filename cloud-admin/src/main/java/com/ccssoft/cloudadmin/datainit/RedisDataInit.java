package com.ccssoft.cloudadmin.datainit;

import com.ccssoft.cloudadmin.dao.UserDao;
import com.ccssoft.cloudadmin.filter.RedisBloomFilter;
import com.ccssoft.cloudcommon.entity.User;
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
    private UserDao userDao;

    @PostConstruct
    public void init () {
        List<User> users = userDao.selectList(null);
        for (User user : users) {
            redisBloomFilter.put(String.valueOf(user.getUsername()));
        }
    }
}
