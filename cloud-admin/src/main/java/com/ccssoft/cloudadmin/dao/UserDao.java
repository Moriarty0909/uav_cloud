package com.ccssoft.cloudadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccssoft.cloudcommon.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author moriarty
 * @date 2020/5/19 18:05
 */
//@Mapper
@Repository//代表持久层也行
public interface UserDao extends BaseMapper<User> {
    User getUserById (int id);

    String getSaltByUsername(String username);

}

