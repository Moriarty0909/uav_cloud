package com.ccssoft.cloudauth.controller;

import com.ccssoft.cloudauth.service.AdminService;
import com.ccssoft.cloudauth.service.AirspaceService;
import com.ccssoft.cloudauth.service.TaskService;
import com.ccssoft.cloudauth.service.UavService;
import com.ccssoft.cloudcommon.common.utils.R;
import com.ccssoft.cloudcommon.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author moriarty
 * @date 2020/5/26 14:31
 */
@RestController
public class AdminController {
    @Resource
    private AdminService adminService;

    @Resource
    private AirspaceService airspaceService;

    @Resource
    private TaskService taskService;

    @Resource
    private UavService uavService;

    @PostMapping(value = "/consumer/admin/register")
    public R registerUser(@RequestBody User user) {
        return adminService.registerUser(user);
    }

    @PostMapping(value = "/consumer/admin/changePassword")
    public R changePWD(@RequestBody User user) {
        return adminService.changePassword(user);
    }

    @GetMapping(value = "/consumer/admin/deleteUser/{id}")
    public R deleteUser(@PathVariable("id") Long id) {
        return adminService.delUser(id);
    }
}
