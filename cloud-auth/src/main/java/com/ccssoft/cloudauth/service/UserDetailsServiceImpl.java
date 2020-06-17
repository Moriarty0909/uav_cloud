package com.ccssoft.cloudauth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccssoft.cloudauth.dao.RoleDao;
import com.ccssoft.cloudauth.dao.UserDao;
import com.ccssoft.cloudauth.entity.JwtUser;
import com.ccssoft.cloudcommon.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * @author moriarty
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //TODO 过滤器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",s);
        User user = userDao.selectOne(wrapper);
        //当被逻辑删除、禁用的用户，不用进行后续验证，直接退
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在！");
        }
        return new JwtUser(user,roleDao);
    }

}
