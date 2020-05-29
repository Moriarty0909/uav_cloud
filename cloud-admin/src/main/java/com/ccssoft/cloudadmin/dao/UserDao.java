package com.ccssoft.cloudadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccssoft.cloudadmin.entity.User;
import com.ccssoft.cloudcommon.common.utils.R;
import org.apache.ibatis.annotations.Mapper;
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

