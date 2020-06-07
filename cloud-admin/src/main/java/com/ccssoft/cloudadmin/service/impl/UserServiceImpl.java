package com.ccssoft.cloudadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccssoft.cloudadmin.dao.UserDao;
import com.ccssoft.cloudcommon.entity.Task;
import com.ccssoft.cloudcommon.entity.User;
import com.ccssoft.cloudadmin.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author moriarty
 * @date 2020/5/20 10:04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public int saveUser(User user) {
        user.setStatus(1);
        return userDao.insert(user);
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userDao.selectOne(wrapper);
    }

    @Override
    public String getSaltByUsername(String username) {
        return userDao.getSaltByUsername(username);
    }


    @Override
    public Page getUserByPage(int current, int size) {
        Page<User> page = new Page<>(current,size);
        userDao.selectPage(page,null);
        return page;
    }
}
