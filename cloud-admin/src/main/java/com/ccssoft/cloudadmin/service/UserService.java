package com.ccssoft.cloudadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccssoft.cloudcommon.entity.Task;
import com.ccssoft.cloudcommon.entity.User;

/**
 * @author moriarty
 * @date 2020/5/20 09:56
 */
public interface UserService extends IService<User> {
    int saveUser (User user);

    User getUserByUsername (String username);

    String getSaltByUsername(String username);

    Page getUserByPage(int current, int size);
}
