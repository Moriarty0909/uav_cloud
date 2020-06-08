package com.ccssoft.cloudairspace.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudairspace.dao.UserAirspaceDao;
import com.ccssoft.cloudairspace.dao.AirspaceDao;
import com.ccssoft.cloudairspace.entity.UserAirspace;
import com.ccssoft.cloudairspace.service.AirspaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccssoft.cloudairspace.service.UserAirspaceService;
import com.ccssoft.cloudcommon.entity.Airspace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private UserAirspaceService userAirspaceService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int registerAirSpace(Airspace airspace) {
        airspace.setStatus(0);
        int result = airspaceDao.saveDB(airspace);
        //绑定关系列表
        UserAirspace userAirspace = new UserAirspace();
        userAirspace.setUserId(airspace.getUserId());
        userAirspace.setAirspaceId(airspace.getId());

        return result == 1 && userAirspaceDao.insert(userAirspace) == 1 ? 1 :0;
    }

    @Override
    public int approvalById(Long id) {
        return airspaceDao.updateStatusById(id);
    }

    @Override
    public List<Airspace> getAirspaceByUserId(Long userId) {
        List list = getAirspaceIdsByUserId(userId);
        return airspaceDao.getAirspaceListByIdList(list);
    }

    @Override
    public List<Airspace> getAirspaceByUserIdPremiseTime(Long userId, Date date) {
        List list = getAirspaceIdsByUserId(userId);
        return airspaceDao.getAirspaceListByIdListAndTime(list,date);
    }

    @Override
    public Airspace getAirspaceByAirspaceId(Long airspaceId) {
        return airspaceDao.getAirspaceByAirspaceId(airspaceId);
    }

    @Override
    public Page<Airspace> getAllAirspaceNotAllow(int current, int size) {
        Page<Airspace> page = new Page<>(current,size);
        QueryWrapper<Airspace> wrapper = new QueryWrapper();
        wrapper.eq("status",0);
        airspaceDao.selectPage(page,wrapper);
        return page.setRecords(airspaceDao.getAirspaceNotAllow());
    }

    @Override
    public List<Airspace> getAirspaceByAirspaceIds(List AirspaceId) {
        return airspaceDao.getAirspaceByAirspaceIds(AirspaceId);
    }

    @Override
    public int updateAirSpace(Airspace airspace) {
        airspace.setStatus(0);
        return airspaceDao.updateInfo(airspace);
    }

    private List getAirspaceIdsByUserId (Long userId) {
        QueryWrapper<UserAirspace> wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<UserAirspace> userAirspaces = userAirspaceDao.selectList(wrapper);
        if (ObjectUtil.length(userAirspaces) == 0) {
            return null;
        }

        List list = new ArrayList();
        for (UserAirspace userAirspace : userAirspaces) {
            list.add(userAirspace.getAirspaceId());
        }
        return list;
    }
}
