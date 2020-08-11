package com.ccssoft.cloudadmin.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccssoft.cloudadmin.dao.UserDao;
import com.ccssoft.cloudadmin.filter.RedisBloomFilter;
import com.ccssoft.cloudadmin.util.RedisUtil;
import com.ccssoft.cloudcommon.entity.User;
import com.ccssoft.cloudadmin.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author moriarty
 * @date 2020/5/20 10:04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisBloomFilter bloomFilter;

    @Override
    public User getUserByUsername(String username) {
        if (!bloomFilter.isExist(username)) {
            return null;
        }

//        if (redisUtil.get(username) == null) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username",username);
            User user = userDao.selectOne(wrapper);
            redisUtil.set(username,JSONUtil.parse(user),600);
            return user;
//        }
//        JSONObject jsonObject = JSONUtil.parseObj(redisUtil.get(username));
//        return jsonObject.toBean(User.class);

    }

    @Override
    public String getSaltByUsername(String username) {
        return userDao.getSaltByUsername(username);
    }


    @Override
    public Page getUserByPage(int current, int size) {
        if (redisUtil.lGetListSize("userforpage") == (current-1) * size){//TODO 这里还是有问题的，要换小区改造那种写法
            //正常缓存里有数据的时候，直接获取
            List userforpage = redisUtil.lGet("userforpage", (current - 1) * size, current * size - 1);
            Page<User> page = new Page<>(current,size);
            page.setRecords(userforpage);
            return page;
        } else if (redisUtil.lGetListSize("userforpage") < (current-1) * size) {
            //当缓存里什么数据都没有的时候
            Page<User> page = new Page<>(current,size);
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",2);
            userDao.selectPage(page,wrapper);
            Page<User> list = new Page<>(0,current * size - 1);
            userDao.selectPage(list,wrapper);

            for (User record : list.getRecords()) {
                redisUtil.lSet("userforpage",record,300);
            }
            return page;
        } else {
            //当缓存里没有这一批新数据的时候，查询，存，返回
            Page<User> page = new Page<>(current,size);
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",2);
            userDao.selectPage(page,wrapper);

            for (User record : page.getRecords()) {
                redisUtil.lSet("userforpage",record,300);
            }
            return page;
        }
    }

    @Override
    public boolean saveDB(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("username",user.getUsername());
        if (userDao.selectOne(wrapper) == null) {
            int result = userDao.insert(user);
            //新增时需要处理一下布隆过滤器的列表
            bloomFilter.put(String.valueOf(user.getUsername()));
            return result ==1 ? true : false;
        }
        return false;
    }

    @Override
    public Map getVerificationCode() {
        //定义图形验证码的长和宽
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 40);
        String img = captcha.getImageBase64();
        String code = captcha.getCode();

        Map<String,Object> map = new HashMap<>();
        map.put("captcha","data:image/png;base64,"+img);
        map.put("key",code);

        redisUtil.set(code,img,60);
        return map;
    }

    @Override
    public int getUserCount() {
        if (redisUtil.get("userCount") == null) {
            QueryWrapper<User> wrapper = new QueryWrapper();
            wrapper.eq("role_id",2);
            Integer integer = userDao.selectCount(wrapper);
            redisUtil.set("userCount",integer);
            return integer;
        }

        return (int)redisUtil.get("userCount");
    }
}
