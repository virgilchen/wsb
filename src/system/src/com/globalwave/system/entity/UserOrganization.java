package com.globalwave.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user_organization")
public class UserOrganization {
    private Long organization_id ;
    private Long user_id ;
    
    public Long getOrganization_id() {
        return organization_id;
    }
    public void setOrganization_id(Long organization_id) {
        this.organization_id = organization_id;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    
}
