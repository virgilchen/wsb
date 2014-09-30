package com.globalwave.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SYSTEM_USER_PRIVILEGE")
public class UserPrivilege {
    private long user_id ;
    private long privilege_id ;
    
    public long getPrivilege_id() {
        return privilege_id;
    }
    public void setPrivilege_id(long privilege_id) {
        this.privilege_id = privilege_id;
    }
    public long getUser_id() {
        return user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    
}
