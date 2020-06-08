package com.ccssoft.cloudadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccssoft.cloudcommon.entity.User;

/**
 * @author moriarty
 * @date 2020/5/20 09:56
 */
public interface UserService extends IService<User> {
    /**
     * 以用户名获取用户
     * @param username 用户名
     * @return 用户详情
     */
    User getUserByUsername (String username);

    String getSaltByUsername(String username);

    /**
     * 以分页形式获取用户数据列表
     * @param current 当前页数
     * @param size 每页数据量
     * @return 分页数据
     */
    Page getUserByPage(int current, int size);

    /**
     * 注册用户，用户名不能重复
     * @param user 用户信息
     * @return 成功与否
     */
    boolean saveDB(User user);
}
