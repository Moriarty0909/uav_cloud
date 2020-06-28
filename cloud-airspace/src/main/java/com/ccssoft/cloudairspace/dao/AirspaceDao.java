package com.ccssoft.cloudairspace.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudcommon.entity.Airspace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 空域信息 Mapper 接口
 * </p>
 *
 * @author moriarty
 * @since 2020-06-01
 */
//@Repository
@Mapper
public interface AirspaceDao extends BaseMapper<Airspace> {

    int saveDB(Airspace airspace);

    int updateStatusById(Long id);

    List<Airspace> getAirspaceListByIdList(List list);

    Airspace getAirspaceByAirspaceId(Long airspaceId);

    List<Airspace> getAirspaceListByIdListAndTime(List list, Date date);

    List<Airspace> getAirspaceNotAllow();

    List<Airspace> getAirspaceByAirspaceIds(List list);

    int updateInfo(Airspace airspace);

    List<Airspace> getAirspaceListByIdList4Page(@Param("current") int i, @Param("size") int size, List list);
}
