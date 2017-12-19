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
}
