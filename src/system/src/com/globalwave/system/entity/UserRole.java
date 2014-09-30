package com.globalwave.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user_role")
public class UserRole {
    private Long role_id ;
    private Long user_id ;
    
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
    
}
