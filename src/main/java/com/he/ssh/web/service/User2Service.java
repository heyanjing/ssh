package com.he.ssh.web.service;

import com.he.ssh.web.dao.User2Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
public class User2Service {

    private User2Dao user2Dao;

    @Autowired
    public void setUser2Dao(User2Dao user2Dao) {
        this.user2Dao = user2Dao;
    }








}
