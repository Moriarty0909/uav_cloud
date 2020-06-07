package com.ccssoft.cloudadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudcommon.entity.User;
import com.ccssoft.cloudadmin.service.UserService;
import com.ccssoft.cloudcommon.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @author moriarty
 * @date 2020/5/20 09:50
 */
@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Resource
    private UserService userService;
    @Value("${server.port}")
    private String port;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    @ResponseBody
    public R registerUser(@Valid @RequestBody User user) {
        log.info("进入AuthController.registerUser(),参数={}",user);
        //处理一下密码加密，暂时先不用了，毕竟security已自带
//        String salt = String.valueOf((int)(Math.random()*1000000));
//        user.setSalt(salt);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.saveUser(user) == 1 ? R.ok() :R.error(300,"用户注册失败");
    }
    @PostMapping("/changePassword")
    @ResponseBody
    public R changePassword (@Valid @RequestBody User user) {
        log.info("进入AuthController.changePassword(),参数={}",user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.updateById(user) ? R.ok() : R.error(300,"密码修改失败！");
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public R updateInfo (@Valid @RequestBody User user) {
        log.info("进入AuthController.updateInfo(),参数={}",user);
        return userService.updateById(user) ?R.ok() : R.error(300,"信息修改失败");
    }

    @GetMapping("/deleteUser/{id}")
    @ResponseBody
    public R delUser (@PathVariable("id") Long id) {
        log.info("进入AuthController.delUser(),参数:userId={}",id);
        return userService.removeById(id) ? R.ok() : R.error(301,"不存在此用户！");
    }

    @GetMapping("/getUser/{username}")
    @ResponseBody
    public R getInfo(@PathVariable("username") String username) {
        log.info("进入AuthController.getInfo(),参数={}",username);
        User user = userService.getUserByUsername(username);
        return user != null ? R.ok(user) : R.error(301,"不存在此用户！");
    }

    /**
     * 获取所有的用户分页信息
     * @param current 当前页数
     * @param size 每页数据量
     * @return 分页数据
     */
    @GetMapping("/getUser4Page/{current}&{size}")
    @ResponseBody
    public R getUser4Page(@PathVariable("current") int current, @PathVariable("size") int size){
        log.info("进入AuthController.getUserByPage(),参数={}",current+","+size);
        Page page = userService.getUserByPage(current,size);
        return R.ok(page);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //TODO 验证码

}
