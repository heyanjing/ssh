package com.he.ssh.web.controller;

import com.he.ssh.entity.Role;
import com.he.ssh.web.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Role save(Role role) {
        this.roleService.save(role);
        return role;
    }

    @RequestMapping("/getById")
    @ResponseBody
    public Role getById(String id) {
        return this.roleService.getById(id);
    }

    @RequestMapping("/getById2")
    @ResponseBody
    public Role getById2(String id) {
        return this.roleService.getById2(id);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Role> findAll() {
        return this.roleService.findAll();
    }

}
