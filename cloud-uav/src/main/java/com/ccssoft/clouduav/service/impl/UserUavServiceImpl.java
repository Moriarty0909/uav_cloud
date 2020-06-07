package com.ccssoft.clouduav.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccssoft.clouduav.dao.UserUavDao;
import com.ccssoft.clouduav.entity.UserUav;
import com.ccssoft.clouduav.service.UserUavService;
import org.springframework.stereotype.Service;

@Service
public class UserUavServiceImpl  extends ServiceImpl<UserUavDao, UserUav> implements UserUavService {
}
