package com.he.ssh.other;

import com.he.ssh.base.core.Guava;
import com.he.ssh.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by heyanjing on 2017/12/19 13:23.
 * spring data jpa 条件示例
 * http://www.bijishequ.com/detail/560480?p=
 */
public class SpecificationDemo {

    public void demo(String name, String nickName, Integer age, LocalDate birthday, String roleId) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //用于暂时存放查询条件的集合
                List<Predicate> predicatesList = Guava.newArrayList();
                //equal示例
                if (!StringUtils.isEmpty(name)) {
                    Predicate namePredicate = cb.equal(root.get("name"), name);
                    predicatesList.add(namePredicate);
                }
                //like示例
                if (!StringUtils.isEmpty(nickName)) {
                    Predicate nickNamePredicate = cb.like(root.get("nickName"), '%' + nickName + '%');
                    predicatesList.add(nickNamePredicate);
                }
                //between示例
                if (birthday != null) {
                    Predicate birthdayPredicate = cb.between(root.get("birthday"), birthday, LocalDate.now());
                    predicatesList.add(birthdayPredicate);
                }
                //排序示例(先根据学号排序，后根据姓名排序)
                query.orderBy(cb.asc(root.get("name")), cb.asc(root.get("age")));

                //最终将查询条件拼好然后return
                Predicate[] predicates = new Predicate[predicatesList.size()];
                return cb.and(predicatesList.toArray(predicates));
            }
        };
    }
}
