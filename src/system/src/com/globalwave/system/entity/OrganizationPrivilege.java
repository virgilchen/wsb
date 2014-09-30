package com.globalwave.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "sys_organization_privilege")
public class OrganizationPrivilege {
    private Long organization_id ;
    private Long privilege_id ;
    
    public Long getOrganization_id() {
        return organization_id;
    } 
    public void setOrganization_id(Long organization_id) {
        this.organization_id = organization_id;
    }
    public Long getPrivilege_id() {
        return privilege_id;
    }
    public void setPrivilege_id(Long privilege_id) {
        this.privilege_id = privilege_id;
    }
    
}
