package com.he.ssh.entity;

import com.he.ssh.base.entity.BaseEntityWithStringId;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by heyanjing on 2017/12/19 9:43.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class User extends BaseEntityWithStringId {

    private String name;
    private String nickName;
    private Integer age;
    private LocalDate birthday;
    private Date birthday2;
    private LocalDateTime birthday3;

    public LocalDateTime getBirthday3() {
        return birthday3;
    }

    public void setBirthday3(LocalDateTime birthday3) {
        this.birthday3 = birthday3;
    }



    public Date getBirthday2() {
        return birthday2;
    }

    public void setBirthday2(Date birthday2) {
        this.birthday2 = birthday2;
    }

    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

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

    public User() {

    }

    public User(String name, String nickName, Integer age, LocalDate birthday, String roleId) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.birthday = birthday;
        this.roleId = roleId;
    }

    public User(String name, Integer age, LocalDate birthday) {

        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public User(String name, String nickName, Integer age, LocalDate birthday) {

        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.birthday = birthday;
    }
}
