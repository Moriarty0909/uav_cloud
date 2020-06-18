package com.ccssoft.cloudairspace;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjectUtil;
import com.ccssoft.cloudairspace.service.AirspaceService;
import com.ccssoft.cloudairspace.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class CloudAirspaceApplicationTests {
    @Resource
    private RedisUtil redisUtil;

    @Test
    void getAirspaceByAirspaceIdsTest() {
        List list = new ArrayList();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        System.out.println(list.size());
        list.remove(2L);
        System.out.println(list.toString());
        System.out.println(list.size());
    }

}
