package com.he.ssh.base.entity;


import com.he.ssh.base.bean.BaseBean;

import java.io.Serializable;

public abstract class BaseEntity<ID extends Serializable> extends BaseBean {
    public abstract ID getId();

    public abstract void setId(ID id);
}
