package com.ccssoft.clouduav.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccssoft.cloudcommon.entity.Uav;
import org.springframework.stereotype.Repository;

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
