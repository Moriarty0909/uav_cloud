package com.ccssoft.cloudairspace.service.impl;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudairspace.dao.UserAirspaceDao;
import com.ccssoft.cloudairspace.dao.AirspaceDao;
import com.ccssoft.cloudairspace.entity.UserAirspace;
import com.ccssoft.cloudairspace.filter.RedisBloomFilter;
import com.ccssoft.cloudairspace.service.AirspaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccssoft.cloudairspace.util.RedisUtil;
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
    private RedisUtil redisUtil;

    @Resource
    private RedisBloomFilter bloomFilter;


    @Override
    @Transactional(rollbackFor=Exception.class)
    public int registerAirSpace(Airspace airspace) {
        airspace.setStatus(0);
        //用不了MP只能自己手动
        Snowflake snowflake = new Snowflake(1,1);
        long uuid = snowflake.nextId();
        airspace.setId(uuid);
        Console.log(airspace);
        int result = airspaceDao.saveDB(airspace);
        //新增数据需要处理一下布隆过滤器的列表
        bloomFilter.put(String.valueOf(airspace.getId()));
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
        List<Airspace> airspaceListByIdList = airspaceDao.getAirspaceListByIdList(list);

        if (redisUtil.get("asbyuserid"+userId) == null) {

            if (airspaceListByIdList != null) {
                redisUtil.set("airspace"+userId,airspaceListByIdList,6000);
            }
            return airspaceListByIdList;
        }

        JSONObject obj = JSONUtil.parseObj(redisUtil.get("airspace"+userId));
        return obj.toBean(List.class);

    }

    @Override
    public List<Airspace> getAirspaceByUserIdPremiseTime(Long userId, Date date) {
        List list = getAirspaceIdsByUserId(userId);
        return airspaceDao.getAirspaceListByIdListAndTime(list,date);
    }

    @Override
    public Airspace getAirspaceByAirspaceId(Long airspaceId) {
        if(!bloomFilter.isExist(String.valueOf(airspaceId))){
            return null;
        }

        if (redisUtil.get(String.valueOf(airspaceId)) == null) {
            Airspace as = airspaceDao.getAirspaceByAirspaceId(airspaceId);
            redisUtil.set(""+airspaceId,as,6000);
        }

        JSONObject obj = JSONUtil.parseObj(redisUtil.get(""+airspaceId));
        return obj.toBean(Airspace.class);
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
