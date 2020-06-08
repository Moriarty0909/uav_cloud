package com.ccssoft.cloudairspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccssoft.cloudairspace.dao.UserAirspaceDao;
import com.ccssoft.cloudairspace.entity.UserAirspace;
import com.ccssoft.cloudairspace.service.UserAirspaceService;
import org.springframework.stereotype.Service;

/**
 * @author moriarty
 * @date 2020/6/8 14:34
 */
@Service
public class UserAirspaceServiceImpl extends ServiceImpl<UserAirspaceDao, UserAirspace> implements UserAirspaceService {
}
