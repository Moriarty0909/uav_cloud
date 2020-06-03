package com.ccssoft.cloudairspace.service;

import com.ccssoft.cloudairspace.entity.Airspace;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccssoft.cloudcommon.common.utils.R;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 空域信息 服务类
 * </p>
 *
 * @author moriarty
 * @since 2020-06-01
 */
public interface AirspaceService extends IService<Airspace> {

    int registerAirSpace(Airspace airspace);

    int approvalById(Long id);

    List<Airspace> getASByUserId(Long userId);

    Airspace getAirspaceByAirspaceId(Long airspaceId);

    List<Airspace> getASByUserIdPremiseTime(Long userId, Date date);

    List<Airspace> getAllAirspaceNotAllow();
}
