package com.ccssoft.cloudairspace.dao;

import com.ccssoft.cloudairspace.entity.Airspace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
}
