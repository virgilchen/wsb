package com.globalwave.system.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.Role;
import com.globalwave.system.entity.RoleSO;
import com.globalwave.system.entity.RolePrivilege;
import com.globalwave.system.entity.UserRole;
import com.globalwave.system.entity.UserSO;


@Service("roleBO")
@Scope("prototype")
@Transactional
public class RoleBO extends BaseServiceImpl {

    public Role create(Role role, Long[] privilege_ids) {  

    	Long pid = role.getPro_role_id() ;
    	if (pid != null && lock(pid) == 0) {
    		throw new BusinessException(1061L) ;//1051', '父组织不存在，本操作无效！
    	}
    	
        Role newRole = (Role) jdbcDao.insert(role) ;
        
        if (privilege_ids != null) {
            for (Long privilege_id : privilege_ids) {
                RolePrivilege orgPrivilege = new RolePrivilege() ;
                orgPrivilege.setPrivilege_id(privilege_id) ;
                orgPrivilege.setRole_id(newRole.getId()) ;
                jdbcDao.insert(orgPrivilege) ;
            }
        }
        
        return newRole;
    }
    public void update(Role role, Long[] privilege_ids) {
    	/*
        if (role.getVersion_id() == null) {
            throw new BusinessException(103L) ;// 103L,保留数据
        }*/
    	if (role.getVersion_id() != null) {
	    	lock(role.getPro_role_id()) ;
	    	
	        jdbcDao.update(role) ;
    	}

        RolePrivilege orgPrivilege = new RolePrivilege() ;
        orgPrivilege.setRole_id(role.getId()) ;
        jdbcDao.delete(RolePrivilege.class, orgPrivilege) ;
        
        if (privilege_ids != null) {
        	orgPrivilege = new RolePrivilege() ;
        	orgPrivilege.setRole_id(role.getId()) ;

        	for (Long privilege_id : privilege_ids) {
                orgPrivilege.setPrivilege_id(privilege_id) ;
                jdbcDao.insert(orgPrivilege) ;
            }
        }        
    }

    public void delete(Role role) {
        if (role.getVersion_id() == null) {
            throw new BusinessException(103L) ;// 103L,保留数据
        }

    	lock(role.getPro_role_id()) ;
    	
    	if (hasChildren(role.getId())) {
    		throw new BusinessException(1062L) ;// 1062', '子角色存在，本操作无效！
    	}
        // delete cascade
        RolePrivilege orgPrivilege = new RolePrivilege() ;
        orgPrivilege.setRole_id(role.getId()) ;
        jdbcDao.delete(RolePrivilege.class, orgPrivilege) ;
        
        // delete cascade
        UserRole userOrg = new UserRole() ;
        userOrg.setRole_id(role.getId()) ;
        jdbcDao.delete(UserRole.class,userOrg) ;
        
        jdbcDao.delete(role) ;
        
    }

    public void deleteAll(Long[] roleIds) {
    	
    	if (hasRetain(roleIds)) {
            throw new BusinessException(103L) ;// 103L,保留数据
    	
    	}
    	for (Long oId:roleIds) {
        	if (hasChildren(oId)) {
        		throw new BusinessException(1062L) ;// 1062', '子角色存在，本操作无效！
        	}
    	}
    	
        RoleSO criterion = new RoleSO() ;
        criterion.setRoleIds(roleIds) ;
        
        // delete cascade
        jdbcDao.delete(RolePrivilege.class, criterion) ;
        jdbcDao.delete(UserRole.class, criterion) ;

        criterion.setRoleIds(null) ;
        criterion.setIds(roleIds) ;
        jdbcDao.delete(Role.class, criterion) ;
        
    }
    
    private boolean hasChildren(Long roleId) {
    	Role so = new Role() ;
    	so.setPro_role_id(roleId) ;
    	return jdbcDao.getName("systemSQLs:roleChildrenCount", so, 0L) > 0 ;
    }
    
    private boolean hasRetain(Long[] roleIds) {
    	RoleSO so = new RoleSO() ;
    	so.setIds(roleIds) ;
    	return jdbcDao.getName("systemSQLs:hasRetainRoles", so, 0L) > 0 ;
    }

    private int lock(Long roleId) {
    	if (roleId == null) {
    		return 0;
    	}
    	
    	Role o = new Role() ;
    	o.setOperate(Role.OPERATE_UPDATE_UNVERSION) ;
    	o.setId(roleId) ;
    	o.addInclusions("id") ;
    	
    	return jdbcDao.update(o) ;
    }
    
    public ArrayPageList<Role> query(RoleSO roleSO)  {

        if (roleSO == null) {
        	roleSO = new RoleSO() ;
        }
        
        roleSO.addAsc("name_") ;
        
        
        return (ArrayPageList<Role>)jdbcDao.query(roleSO, Role.class);
    }



    public Role get(Long id, boolean isCreateNewWhenNotExist) throws Exception {  
    	Role org = new Role() ;
    	org.setId(id) ;
        org = (Role) jdbcDao.get(org) ;
        
        if (org == null) {
        	if (isCreateNewWhenNotExist) {
        		org = new Role() ;
        	} else {
        	    return org ;
        	}
        }
        
        org.setPrivileges(getPrivilegeBO().possessedByRole(id)) ;
        
        return org;
    }

    public ArrayPageList<Role> userBelongTo(Long userId, Long limitedUserId) {
    	
        //String Sql = String.format(RoleSO.USER_BELONG_TO_ORGANIZATION, userId) ;
        //return (ArrayPageList<Role>)jdbcDao.query(new StringBuffer(Sql), null, 1, Integer.MAX_VALUE, null, Role.class);

    	UserSO so = new UserSO() ;
    	so.setUser_id(userId) ;
    	so.setLimited_user_id(limitedUserId);
    	so.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE) ;
    	return (ArrayPageList<Role>)jdbcDao.queryName("systemSQLs:user_belong_to_role", so, Role.class) ;
    }
    
    
    public ArrayPageList<Role> userHasRoles(Long userId) {
    	
        //String Sql = String.format(RoleSO.USER_BELONG_TO_ORGANIZATION, userId) ;
        //return (ArrayPageList<Role>)jdbcDao.query(new StringBuffer(Sql), null, 1, Integer.MAX_VALUE, null, Role.class);

    	UserSO so = new UserSO() ;
    	so.setUser_id(userId) ;
    	so.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE) ;
    	return (ArrayPageList<Role>)jdbcDao.queryName("systemSQLs:user_has_roles", so, Role.class) ;
    }

    
    private PrivilegeBO getPrivilegeBO() {
    	return (PrivilegeBO)CodeHelper.getAppContext().getBean("privilegeBO") ;
    }
}
