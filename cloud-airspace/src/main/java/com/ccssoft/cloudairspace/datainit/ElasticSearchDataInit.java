package com.ccssoft.cloudairspace.datainit;

import com.ccssoft.cloudairspace.dao.AirspaceDao;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author moriarty
 * @date 2020/7/10 17:52
 */
@Component
public class ElasticSearchDataInit {
    @Resource
    private AirspaceDao airspaceDao;
    @PostConstruct
    public void init () {

    }
}
