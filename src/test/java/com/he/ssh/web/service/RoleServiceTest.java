package com.he.ssh.web.service;

import com.he.ssh.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by heyanjing on 2017/12/19 10:43.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-base.xml"})
public class RoleServiceTest {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceTest.class);

    private RoleService roleService;

    @Autowired
    public void setUserService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Test
    public void save() throws Exception {
        String birthday = "1989-09-19";
        LocalDate date = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Role role = new Role("user4", "code4");
        role.setCreateDateTime(LocalDateTime.now());
        role.setUpdateDateTime(LocalDateTime.now());
        this.roleService.save(role);
    }


}