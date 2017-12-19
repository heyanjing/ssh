package com.he.ssh.web.service;

import com.he.ssh.entity.User;
import com.he.ssh.web.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(User user) {
        this.userDao.save(user);
    }

    public User getById(String id) {
//        User user = this.userDao.getOne(id);//调用getone的方法需要添加@Transactional注解
//        String name = user.getName();
//        log.info(name);
//        log.info("{}", user);
        User user = this.userDao.getById(id);
        return user;
    }

    public User getById2(String id) {
        //region Description
        /*
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(id)) {
                    Predicate namePredicate = cb.equal(root.get("id"), id);
                    predicatesList.add(namePredicate);
                }
                Predicate[] predicates = new Predicate[predicatesList.size()];
                return cb.and(predicatesList.toArray(predicates));
            }
        };
        */
        //endregion
        Optional<User> one = this.userDao.findOne((root,query,cb)->{
            List<Predicate> predicatesList = new ArrayList<>();
            if (!StringUtils.isEmpty(id)){
                Predicate namePredicate = cb.equal(root.get("id"), id);
                predicatesList.add(namePredicate);
            }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        });
        return one.get();
    }

    public List<User> findAll() {
        return this.userDao.findAll();
    }
    public List<User> findAll2() {
        String name="何彦静";
        return this.userDao.findAll((root,query,cb)->{
            List<Predicate> predicatesList = new ArrayList<>();
            if (!StringUtils.isEmpty(name)){
                Predicate namePredicate = cb.like(root.get("name"), '%' + name + '%');
                predicatesList.add(namePredicate);
            }

            query.orderBy(cb.asc(root.get("name")),cb.asc(root.get("age")));
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        });
    }


}
