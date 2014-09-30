package com.globalwave.system.entity;

import javax.persistence.Column;

import com.globalwave.base.BaseSO;


public class PrivilegeSO extends BaseSO{
	/*
    public final static String PRIVILEGE_PROSESSED_BY_ORGANIZATION = 
        " select p.privilege_id privilege_id," +
        "        p.pro_privilege_id pro_privilege_id," +
        "        p.code_ code_ ," +
        "        p.name_ name_ ," +
        "        p.url_ url_ ," +
        "        p.desc_ desc_ , " +
        "        op.organization_id organization_id " +
        "   from system_privilege p left join system_organization_privilege op " +
        "         on p.privilege_id = op.privilege_id " +
        "        and op.organization_id='%s'" ; 
    */

    public final static String PRIVILEGE_PROSESSED_BY_USER = 
        " select p.privilege_id privilege_id," +
        "        p.pro_privilege_id pro_privilege_id," +
        "        p.code_ code_ ," +
        "        p.name_ name_ ," +
        "        p.url_ url_ ," +
        "        p.desc_ desc_ , " +
        "        up.user_id user_id " +
        "   from system_privilege p left join system_user_privilege up " +
        "         on p.privilege_id = up.privilege_id " +
        "        and up.user_id='%s'" ;     
    
        /*
    public final static String USER_ALL_PRIVILEGE = 
        " select p.privilege_id privilege_id," +
        "        p.pro_privilege_id pro_privilege_id," +
        "        p.code_ code_ ," +
        "        p.name_ name_ ," +
        "        p.url_ url_ ," +
        "        p.desc_ desc_ " +
        "   from system_user_organization uo , " +
        "        system_organization o , " +
        "        system_organization_privilege op , " +
        "        system_privilege p " +
        "  where uo.user_id = %s" +
        "    and uo.organization_id = o.organization_id" +
        "    and o.organization_id = op.organization_id" +
        "    and op.privilege_id = p.privilege_id " ;
    */
    @Column(name="id")
    private Long[] privilegeIds ; 
    private Long organization_id ;
    private Long role_id ;
    
    private Long user_id;
    
    private Long pro_privilege_id ;

    public Long[] getPrivilegeIds() {
        return privilegeIds;
    }
    public void setPrivilegeIds(Long[] privilegeIds) {
        this.privilegeIds = privilegeIds;
    }
	public Long getOrganization_id() {
		return organization_id;
	}
	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public Long getPro_privilege_id() {
		return pro_privilege_id;
	}
	public void setPro_privilege_id(Long pro_privilege_id) {
		this.pro_privilege_id = pro_privilege_id;
	}
	@Override
	public Class<?> getTableClass() {
		return Privilege.class;
	}
    
    
}
