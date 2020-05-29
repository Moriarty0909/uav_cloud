package com.ccssoft.cloudadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudadmin.entity.User;

/**
 * @author moriarty
 * @date 2020/5/20 09:56
 */
public interface UserService {
    int saveUser (User user);

    User getUserByUsername (String username);

    String getSaltByUsername(String username);

    int updatePassword(User user);

    int delUserByUsername(String username);

    int updateUser(User user);

    Page getUserByPage(int current, int size);
}
