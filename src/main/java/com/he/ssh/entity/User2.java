package com.he.ssh.entity;

import com.he.ssh.base.entity.BaseEntityWithLongId;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Created by heyanjing on 2017/12/19 9:43.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class User2 extends BaseEntityWithLongId {
    private String name;
    private Integer age;
    private LocalDate birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public User2() {

    }

    public User2(String name, Integer age, LocalDate birthday) {

        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }
}
