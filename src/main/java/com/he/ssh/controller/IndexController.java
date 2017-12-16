package com.he.ssh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "/index";
    }
}
