package com.ccssoft.cloudadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccssoft.cloudadmin.entity.User;
import com.ccssoft.cloudadmin.service.UserService;
import com.ccssoft.cloudcommon.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author moriarty
 * @date 2020/5/20 09:50
 */
@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;
    @Value("${server.port}")
    private String port;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    @ResponseBody
    public R registerUser(@RequestBody User user) {
        log.info("进入AuthController.registerUser(),参数={}",user);
        //TODO 后续挨个验证，防止前端传过来的数据使后端异常。
        if (user != null && userService.getUserByUsername(user.getUsername()) == null) {
            //处理一下密码加密，暂时先不用了，毕竟security已自带
//        String salt = String.valueOf((int)(Math.random()*1000000));
//        user.setSalt(salt);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userService.saveUser(user) == 1 ? R.ok() :R.error(300,"用户注册失败");
        }

        return R.error(301,"注册信息不全！");
    }
    @PostMapping("/changePassword")
    @ResponseBody
    public R changePassword (@RequestBody User user) {
        log.info("进入AuthController.changePassword(),参数={}",user);
        if (user.getUsername() != null && user.getPassword() != null && userService.getUserByUsername(user.getUsername()) != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            if (userService.updatePassword(user) == 1) {
                return R.ok();
            }
            return R.error(300,"密码修改失败！");
        } else {
            return R.error(301,"账户或密码为空、不存在此用户！");
        }
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public R updateInfo (@RequestBody User user) {
        log.info("进入AuthController.updateInfo(),参数={}",user);
        //TODO 后续确认那些数据不能为null再细致补充
        if (user != null) {
            userService.updateUser(user);
            return R.ok();
        }

        return R.error(300,"信息修改失败");
    }

    @GetMapping("/deleteUser/{username}")
    @ResponseBody
    public R delUser (@PathVariable("username") String username) {
        log.info("进入AuthController.delUser(),参数={}",username);
        return userService.delUserByUsername(username) == 1 ? R.ok() : R.error(301,"不存在此用户！");
    }

    @GetMapping("/getUser/{username}")
    @ResponseBody
    public R getInfo(@PathVariable("username") String username) {
        log.info("进入AuthController.getInfo(),参数={}",username);
        User user = userService.getUserByUsername(username);
        return user != null ? R.ok(user) : R.error(301,"不存在此用户！");
    }

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
