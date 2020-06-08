package com.ccssoft.clouduav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudcommon.entity.Uav;
import com.ccssoft.clouduav.dao.UserUavDao;
import com.ccssoft.clouduav.dao.UavDao;
import com.ccssoft.clouduav.entity.UserUav;
import com.ccssoft.clouduav.service.UavService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 无人机设备信息 服务实现类
 * </p>
 *
 * @author moriarty
 * @since 2020-05-28
 */
@Service
public class UavServiceImpl extends ServiceImpl<UavDao, Uav> implements UavService {

    @Resource
    private UavDao uavDao;

    @Resource
    private UserUavDao userUavDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveUav(Uav uav, Long userId) {
        int result = uavDao.insert(uav);
        UserUav userUav = new UserUav();
        userUav.setUavId(uav.getId());
        userUav.setUserId(userId);

        return result ==1 && userUavDao.insert(userUav) == 1 ? 1:0;

    }

    @Override
    public Page getUav4Page(int current, int size) {
        Page<Uav> page = new Page<>(current,size);
        uavDao.selectPage(page,null);
        return page;
    }

    @Override
    public Page getUavByUserId4Page(int current, int size, Long id) {
        Page<Uav> page = new Page<>(current,size);
        QueryWrapper<Uav> wrapperUav = new QueryWrapper();
        QueryWrapper<UserUav> wrapperRelation = new QueryWrapper();
        wrapperRelation.eq("user_id",id);
        List<UserUav> userUavs = userUavDao.selectList(wrapperRelation);
        if (userUavs == null) {
            return null;
        }
        Collection<Long> collection = new ArrayList<>();
        for (UserUav userUav : userUavs) {
            collection.add(userUav.getUavId());
        }
        wrapperUav.in("id",collection);
        uavDao.selectPage(page,wrapperUav);
        return page;
    }

}
