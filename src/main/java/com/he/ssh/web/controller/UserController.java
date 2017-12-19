package com.he.ssh.web.controller;

import com.he.ssh.entity.User;
import com.he.ssh.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by heyanjing on 2017/12/19 11:17.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/save")
    public void save(User user) {
        this.userService.save(user);
    }

    @RequestMapping("/getById")
    @ResponseBody
    public User getById(String id) {
        return this.userService.getById(id);
    }

    @RequestMapping("/getById2")
    @ResponseBody
    public User getById2(String id) {
        return this.userService.getById2(id);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() {
        return this.userService.findAll();
    }
    @RequestMapping("/findAll2")
    @ResponseBody
    public List<User> findAll2() {
        return this.userService.findAll2();
    }

}
