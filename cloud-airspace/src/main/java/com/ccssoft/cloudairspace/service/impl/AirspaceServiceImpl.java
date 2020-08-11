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
import com.ccssoft.cloudcommon.entity.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        if (redisUtil.get("airBy"+userId) == null) {
            List<Airspace> airspaceListByIdList = airspaceDao.getAirspaceListByIdList(list);
            redisUtil.set("airBy"+userId,airspaceListByIdList,6000);
            return airspaceListByIdList;
        }

        List result = JSONUtil.parseArray(redisUtil.get("airBy"+userId));

        return result;

    }

    @Override
    public List<Airspace> getAirspaceByUserIdPremiseTime(Long userId, String date) {
        List list = getAirspaceIdsByUserId(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return airspaceDao.getAirspaceListByIdListAndTime(list,date1);
    }

    @Override
    public Airspace getAirspaceByAirspaceId(Long airspaceId) {
        String numId = String.valueOf(airspaceId);
        if(!bloomFilter.isExist(numId)){
            return null;
        }

        if (redisUtil.get(numId) == null) {
            Airspace as = airspaceDao.getAirspaceByAirspaceId(airspaceId);
            redisUtil.set(numId,as,6000);
        }

        JSONObject obj = JSONUtil.parseObj(redisUtil.get(numId));
        return obj.toBean(Airspace.class);
    }


    @Override
    public Page<Airspace> getAllAirspaceNotAllow(int current, int size) {
        //主要作为管理者使用，并发量应该不会很高？
        Page<Airspace> page = new Page<>(current,size);
        QueryWrapper<Airspace> wrapper = new QueryWrapper();
        wrapper.eq("status",0);
        airspaceDao.selectPage(page,wrapper);
        return page.setRecords(airspaceDao.getAirspaceNotAllow());
    }

    @Override
    public List<Airspace> getAirspaceByAirspaceIds(List<Long> airspaceIds) {
        List<Airspace> list = new ArrayList<>();
        for (int i = 0; i < airspaceIds.size(); i++) {

            String numId = String.valueOf(airspaceIds.get(i));
            if (!bloomFilter.isExist(numId)) {
                return null;
            }
            Object o = redisUtil.get(numId);

            if (o != null) {
                Airspace airspace = JSONUtil.parseObj(o).toBean(Airspace.class);
                list.add(airspace);
                airspaceIds.remove(airspaceIds.get(i));
            }
        }
        //全部在缓存中找到
        if (airspaceIds.size() == 0) {
            return list;
        }

        //添加进缓存，组装后返回
        List<Airspace> airspaceList = airspaceDao.getAirspaceByAirspaceIds(airspaceIds);
        for (Airspace airspace : airspaceList) {
            list.add(airspace);
            redisUtil.set(String.valueOf(airspace.getId()),airspace);
        }

        return list;
    }

    @Override
    public int updateAirSpace(Airspace airspace) {
        //更新时只需要删除是效率最高最节省资源的
        redisUtil.del(String.valueOf(airspace.getId()));
        airspace.setStatus(0);
        return airspaceDao.updateInfo(airspace);
    }

    @Override
    public Integer getNoApprovaledCount() {
        QueryWrapper<Airspace> wrapper = new QueryWrapper<>();
        wrapper.eq("status",0);
        return airspaceDao.selectCount(wrapper);
    }

    @Override
    public Integer getApprovaledCount() {
        QueryWrapper<Airspace> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        return airspaceDao.selectCount(wrapper);
    }

    @Override
    public Page<Airspace> getAirspaceByUserId4Page(int current, int size, Long userId) {
        if (redisUtil.lGetListSize("airspaceBy"+userId) < (current-1) * size) {
            //断开之后从中间重新开始，自动补齐前面。
            List list = getAirspaceIdsByUserId(userId);
            Page<Airspace> page = new Page<>(current,size);
            List<Airspace> airspaceList = airspaceDao.getAirspaceListByIdList4Page((current-1)*size,size,list);
            QueryWrapper<Airspace> wrapper = new QueryWrapper<>();
            wrapper.in("id",list);
            Page<Airspace> airspacePage = airspaceDao.selectPage(page, wrapper);
            airspacePage.setRecords(airspaceList);
            airspaceList = airspaceDao.getAirspaceListByIdList4Page(0,current * size - 1,list);
            for (Airspace airspace : airspaceList) {
                redisUtil.lSet("airspaceBy"+userId,airspace);
            }
            return airspacePage;
        } else if (redisUtil.lGet("airspaceBy"+userId,(current-1) * size,current * size - 1) != null) {
            //顺序往下
            Page page = new Page<>(current,size);
            page.setRecords(redisUtil.lGet("airspaceBy"+userId,(current-1) * size,current * size - 1));
            page.setTotal(redisUtil.lGetListSize("airspaceBy"+userId));
            return page;
        } else {
            //从头进来
            List list = getAirspaceIdsByUserId(userId);
            Page<Airspace> page = new Page<>(current,size);
            List<Airspace> airspaceList = airspaceDao.getAirspaceListByIdList4Page((current-1)*size,size,list);
            QueryWrapper<Airspace> wrapper = new QueryWrapper<>();
            wrapper.in("id",list);
            Page<Airspace> airspacePage = airspaceDao.selectPage(page, wrapper);
            airspacePage.setRecords(airspaceList);
            for (Airspace airspace : airspaceList) {
                redisUtil.lSet("airspaceBy"+userId,airspace,300);
            }
            return airspacePage;
        }
    }

    private List<Long> getAirspaceIdsByUserId (Long userId) {
        String numId = String.valueOf(userId);
        //在默认不删表的情况下，查到这个就也能表明对应的另一张表也是会有对应的数据的,后续的工作就可以直接进行
        if (!bloomFilter.isExist(numId) ) {
            return null;
        }

        QueryWrapper<UserAirspace> wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        List<UserAirspace> userAirspaces = userAirspaceDao.selectList(wrapper);

        List<Long> list = new ArrayList();
        for (UserAirspace userAirspace : userAirspaces) {
            list.add(userAirspace.getAirspaceId());
        }
        return list;
    }
}
