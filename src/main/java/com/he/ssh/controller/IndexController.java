package com.he.ssh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
public class IndexController {

    @GetMapping(value = {"","/"})
    public String index() {
        return "/index";
    }
    @GetMapping(value = {"/admin","/admin/"})
    public String admin() {
        return "/index";
    }
    @GetMapping(value = {"/user","/user/"})
    public String user() {
        return "/index";
    }
    @GetMapping(value = {"/other","/other/"})
    public String other() {
        return "/index";
    }
    @GetMapping(value = {"/javadoc","/javadoc/"})
    public String javadoc() {
        return "/index";
    }
}
