package com.globalwave.system.entity;

import javax.persistence.Column;


public class PrivilegeCriterion {
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
    
    @Column(name="privilege_id")
    private Long[] privilegeIds ; 

    public Long[] getPrivilegeIds() {
        return privilegeIds;
    }
    public void setPrivilegeIds(Long[] privilegeIds) {
        this.privilegeIds = privilegeIds;
    }
    
    
}
