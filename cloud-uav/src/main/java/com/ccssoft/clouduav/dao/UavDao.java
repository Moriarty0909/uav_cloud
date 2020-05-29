package com.ccssoft.clouduav.dao;

import com.ccssoft.clouduav.entity.Uav;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 无人机设备信息 Mapper 接口
 * </p>
 *
 * @author moriarty
 * @since 2020-05-28
 */
@Repository//代表持久层也行
public interface UavDao extends BaseMapper<Uav> {

}
