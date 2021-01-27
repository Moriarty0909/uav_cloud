package com.ccssoft.cloudauth.service;

import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 远程调用有关用户管理模块的服务
 * @author moriarty
 * @date 2020/5/26 15:49
 */
@Component
@FeignClient(value = "admin-server")//调用哪个微服务
public interface AdminService {

//    @GetMapping("/admin/login")
//    String login();
//
    /**
     * 远程调用有关用户管理模块的用户注册功能
     * @param user 需注册的用户详情
     * @return 成功与否
     */
    @PostMapping("/admin/register")
    R registerUser(@Valid @RequestBody User user);

    /**
     * 远程调用有关用户管理模块的修改密码功能
     * @param user 带有新密码的用户详情
     * @return 成功与否
     */
    @PostMapping("/admin/changePassword")
    R changePassword (@Valid @RequestBody User user);

    /**
     * 远程调用有关用户管理模块的修改用户信息功能
     * @param user 修改用户信息详情
     * @return 成功与否
     */
    @PostMapping("/admin/updateInfo")
    R updateInfo (@Valid @RequestBody User user);

    /**
     * 远程调用有关用户管理模块的通过用户id删除用户功能
     * @param id 用户id
     * @return 成功与否
     */
    @GetMapping("/admin/deleteUser/{id}")
    R delUser (@PathVariable("id") Long id);

    /**
     * 远程调用有关用户管理模块的通过用户名获取用户信息功能
     * @param username 用户名
     * @return 用户信息详情
     */
    @GetMapping("/admin/getUser/{username}")
    R getInfo(@PathVariable("username") String username);

    /**
     * 获取所有的用户分页信息
     * @param current 当前页数
     * @param size 每页数据量
     * @return 分页数据
     */
    @GetMapping("/admin/getUser4Page/{current}&{size}")
    R getUser4Page(@PathVariable("current") int current, @PathVariable("size") int size);

    /**
     * 获取验证码
     * @return 分页数据
     */
    @GetMapping("/admin/verificationCode")
    R getVerificationCode ();

    /**
     * 获取用户数量
     * @return
     */
    @GetMapping("/admin/getUserCount")
    R getUserCount ();
}
