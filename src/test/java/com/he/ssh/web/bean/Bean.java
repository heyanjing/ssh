package com.he.ssh.web.bean;

import java.util.Date;

/**
 * Created by heyanjing on 2017/12/1 15:56.
 */
public class Bean {

    private String id;
    private Integer age;
    private Date birthday;
    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }

    public Bean(String id, Integer age, Date birthday) {
        this.id = id;
        this.age = age;
        this.birthday = birthday;
    }

    public Bean() {

    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


}
