package com.he.ssh.entity;

import com.he.ssh.base.entity.BaseEntityWithStringId;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;

/**
 * Created by heyanjing on 2017/12/19 9:43.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class Role extends BaseEntityWithStringId {

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Role(String name, String code) {

        this.name = name;
        this.code = code;
    }

    public Role() {

    }
}
