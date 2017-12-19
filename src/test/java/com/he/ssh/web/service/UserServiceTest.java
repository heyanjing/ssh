package com.he.ssh.web.service;

import com.he.ssh.entity.User;
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
        User user = new User("何彦静2", "昵称2", 28, date, "2a17f1da-0e2b-42b5-99fd-4dfff3953a4f");// insert into User (age, birthday, name, id) values (28, '1989-09-19', '何彦静', 'c22a00d6-d63e-4821-882c-1b4e18af085f')
        user.setCreateDateTime(LocalDateTime.now());
        user.setUpdateDateTime(LocalDateTime.now());
        this.userService.save(user);
    }

    @Test
//    @Transactional
    public void update() throws Exception {

        User user1 = this.userService.getById("295d573f-0d6b-49cf-8e10-426c45c3404a");
        log.warn("{}", user1);
        user1.setAge(27);
        this.userService.save(user1);
//        User user2 = this.userService.getById2("8e28d91b-9171-4199-b438-67e459214c63");
//        log.warn("{}",user2);
//        this.userService.save(user);
    }

    @Test
    public void findAll() throws Exception {
        //select user0_.id as id1_1_, user0_.createDateTime as createDa2_1_, user0_.updateDateTime as  updateDa3_1_, user0_.age as age4_1_, user0_.birthday as birthday5_1_, user0_.name as name6_1_, user0_.nickName as nickName7_1_ from User user0_
        List<User> userList = this.userService.findAll();
        log.warn("{}", userList);
        List<User> userList2 = this.userService.findAll2();
        log.warn("{}", userList2);
    }

}