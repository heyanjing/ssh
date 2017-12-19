package com.he.ssh.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;

/**
 * Created by heyanjing on 2017/12/16 14:29.
 */
@Controller
public class IndexController {
private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @GetMapping(value = {"","/"})
    public String index() {
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        log.warn("{}",servletContext);
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
