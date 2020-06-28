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

    @GetMapping("/turn/task-list-guess")
    public String taskListGuess () {
        return "task-list-guess";
    }

    @GetMapping("/turn/task-list-admin")
    public String taskListAdmin () {
        return "task-list-admin";
    }

    @GetMapping("/turn/task-add")
    public String taskAdd () {
        return "task-add";
    }

    @GetMapping("/turn/task-map")
    public String taskMap () {
        return "task-map";
    }

    @GetMapping("/turn/task-edit")
    public String taskEdit () {
        return "task-edit";
    }

    @GetMapping("/turn/airspace-list-guess")
    public String airspaceListGuess () {
        return "airspace-list-guess";
    }

    @GetMapping("/turn/airspace-list-admin")
    public String airspaceListAdmin () {
        return "airspace-list-admin";
    }

}
