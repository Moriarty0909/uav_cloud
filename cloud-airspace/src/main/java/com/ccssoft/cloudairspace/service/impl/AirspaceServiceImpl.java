package com.ccssoft.cloudairspace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccssoft.cloudairspace.dao.UserAirspaceDao;
import com.ccssoft.cloudairspace.entity.Airspace;
import com.ccssoft.cloudairspace.dao.AirspaceDao;
import com.ccssoft.cloudairspace.entity.UserAirspace;
import com.ccssoft.cloudairspace.service.AirspaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 空域信息 服务实现类
 * </p>
 *
 * @author moriarty
 * @since 2020-06-01
 */
@Service
public class AirspaceServiceImpl extends ServiceImpl<AirspaceDao, Airspace> implements AirspaceService {
    @Resource
    private AirspaceDao airspaceDao;
    @Resource
    private UserAirspaceDao userAirspaceDao;

    @Override
    public int registerAirSpace(Airspace airspace) {
        airspace.setStatus(0);
        airspace.setGmtCreate(new Date());
        airspace.setGmtModified(new Date());
        int result = airspaceDao.saveDB(airspace);
        //绑定关系列表
        UserAirspace userAirspace = new UserAirspace();
        userAirspace.setUserId(Long.valueOf(String.valueOf(airspace.getData())));
        userAirspace.setAirspaceId(airspace.getId());

        return result == 1 && userAirspaceDao.insert(userAirspace) == 1 ? 1 :0;
    }

    @Override
    public int approvalById(Long id) {
        return airspaceDao.updateStatusById(id);
    }

    @Override
    public List<Airspace> getASByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<UserAirspace> userAirspaces = userAirspaceDao.selectList(wrapper);
        if (userAirspaces.size() == 0) {
            return null;
        }

        List list = new ArrayList();
        for (UserAirspace userAirspace : userAirspaces) {
            list.add(userAirspace.getAirspaceId());
        }

        return airspaceDao.getAirspaceListByIdList(list);
    }

    @Override
    public Airspace getAirspaceByAirspaceId(Long airspaceId) {
        return airspaceDao.getAirspaceByAirspaceId(airspaceId);
    }

    @Override
    public List<Airspace> getASByUserIdPremiseTime(Long userId, Date date) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<UserAirspace> userAirspaces = userAirspaceDao.selectList(wrapper);
        if (userAirspaces.size() == 0) {
            return null;
        }

        List list = new ArrayList();
        for (UserAirspace userAirspace : userAirspaces) {
            list.add(userAirspace.getAirspaceId());
        }


        return airspaceDao.getAirspaceListByIdListAndTime(list,date);
    }

    @Override
    public List<Airspace> getAllAirspaceNotAllow() {
        return airspaceDao.getAirspaceNotAllow();
    }
}
