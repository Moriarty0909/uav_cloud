package com.ccssoft.clouduav.dao;

import com.ccssoft.clouduav.entity.UserUav;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户与无人机关系 Mapper 接口
 * </p>
 *
 * @author moriarty
 * @since 2020-05-29
 */
@Repository//代表持久层也行
public interface UserUavDao extends BaseMapper<UserUav> {

}
