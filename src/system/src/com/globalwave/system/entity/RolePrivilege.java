package com.globalwave.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "SYS_ROLE_PRIVILEGE")
public class RolePrivilege {
    private Long role_id ;
    private Long privilege_id ;
    
    public Long getPrivilege_id() {
        return privilege_id;
    }
    public void setPrivilege_id(Long privilege_id) {
        this.privilege_id = privilege_id;
    }
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
    
}
