package com.ccssoft.cloudauth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccssoft.cloudcommon.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author moriarty
 * @date 2020/5/19 18:05
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}

