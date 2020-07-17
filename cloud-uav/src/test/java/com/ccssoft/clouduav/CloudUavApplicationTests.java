package com.ccssoft.clouduav;

import com.ccssoft.clouduav.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CloudUavApplicationTests {

    @Resource
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        redisUtil.lSet("abc",0);
        redisUtil.lSet("abc",1);
        redisUtil.lSet("abc",2);
        redisUtil.lSet("abc",3);
        redisUtil.lSet("abc",4);
        redisUtil.lSet("abc",5);
        redisUtil.lSet("abc",6);
        redisUtil.lSet("abc",7);
        redisUtil.lSet("abc",8);
        redisUtil.lSet("abc",9);

        System.out.println(redisUtil.getRange("abc", 1, 3));
        System.out.println(redisUtil.getRange("abc", 0, 2));

    }

}
