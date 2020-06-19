package com.ccssoft.cloudui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author moriarty
 * @date 2020/6/19 09:11
 */
@Controller
public class TurnController {
    @GetMapping("/turn/login")
    public String login () {
        return "login";
    }

    @GetMapping("/turn/index")
    public String index () {
        return "index";
    }

    @GetMapping("/turn/welcome")
    public String welcome () {
        return "welcome";
    }

    @GetMapping("/turn/task-list")
    public String taskList () {
        return "task-list";
    }

    @GetMapping("/turn/task-add")
    public String taskAdd () {
        return "task-add";
    }
}
