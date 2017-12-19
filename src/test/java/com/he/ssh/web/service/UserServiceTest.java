package com.he.ssh.web.service;

import com.he.ssh.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by heyanjing on 2017/12/19 10:43.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-base.xml"})
public class UserServiceTest {
    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void save() throws Exception {
        String birthday = "1989-09-19";
        LocalDate date = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        User user = new User("何彦静", 28, date);// insert into User (age, birthday, name, id) values (28, '1989-09-19', '何彦静', 'c22a00d6-d63e-4821-882c-1b4e18af085f')
        user.setCreateDateTime(LocalDateTime.now());
        user.setUpdateDateTime(LocalDateTime.now());
        this.userService.save(user);
    }

    @Test
    @Transactional
    public void update() throws Exception {

        User user = this.userService.getById("8e28d91b-9171-4199-b438-67e459214c63");
        log.warn("{}",user);
//        this.userService.save(user);
    }

    @Test
    public void findAll() throws Exception {
        List<User> userList = this.userService.findAll();
        log.warn("{}", userList);
    }

}