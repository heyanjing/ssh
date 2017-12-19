package com.he.ssh.web.service;

import com.he.ssh.entity.User;
import com.he.ssh.web.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(User user) {
        this.userDao.save(user);
    }


    public User getById(String id) {
        return this.userDao.getOne(id);//调用getone的方法需要添加@Transactional注解
    }

    public User getById2(String id) {
//        return this.userDao.findOne();
        return null;
    }

    public List<User> findAll() {
        return this.userDao.findAll();
    }


}
