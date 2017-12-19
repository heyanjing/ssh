package com.he.ssh.web.service;

import com.he.ssh.entity.Role;
import com.he.ssh.web.dao.RoleDao;
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
public class RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleService.class);
    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void save(Role role) {
        this.roleDao.save(role);
    }

    //    @Transactional
    public Role getById(String id) {
        Role role = this.roleDao.getOne(id);//调用getone的方法需要添加@Transactional注解
        String name = role.getName();
        log.info(name);
        log.info("{}", role);
        return role;
    }

    public Role getById2(String id) {
        //region Description
        /*
        Specification<role> specification = new Specification<role>() {
            @Override
            public Predicate toPredicate(Root<role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
        Optional<Role> one = this.roleDao.findOne((root, query, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (!StringUtils.isEmpty(id)) {
                Predicate namePredicate = cb.equal(root.get("id"), id);
                predicatesList.add(namePredicate);
            }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        });
        return one.get();
    }

    public List<Role> findAll() {
        return this.roleDao.findAll();
    }


}
