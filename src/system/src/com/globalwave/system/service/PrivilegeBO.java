package com.globalwave.system.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.OrganizationPrivilege;
import com.globalwave.system.entity.Privilege;
import com.globalwave.system.entity.PrivilegeSO;

@Service("privilegeBO")
@Scope("prototype")
@Transactional
public class PrivilegeBO 
       extends BaseServiceImpl  {

    public Privilege create(Privilege privilege) {
    	
    	Long pid = privilege.getPro_privilege_id() ;
    	
    	if (pid != null && lock(pid) == 0) {
    		throw new BusinessException(1031L) ;//1031', '父权限不存在，本操作无效！
    	}
    	
        return (Privilege) jdbcDao.insert(privilege);
    }

    public void update(Privilege privilege) {
    	
    	lock(privilege.getPro_privilege_id()) ;
    	
        jdbcDao.update(privilege) ;
    }

    public void delete(Privilege privilege) {
    	
    	lock(privilege.getPro_privilege_id()) ;
    	
    	if (hasChildren(privilege.getId())) {
    		throw new BusinessException(1032L) ;//1032', '子权限存在，本操作无效！
    	}
    	
        // delete cascade 
        OrganizationPrivilege orgPrivilege = new OrganizationPrivilege() ;
        orgPrivilege.setPrivilege_id(privilege.getId()) ;
        jdbcDao.delete(OrganizationPrivilege.class, orgPrivilege) ;
        
        jdbcDao.delete(privilege) ;
        
    }

    public void deleteAll(Long[] privilegeIds) {
    	//lockPrivilege(jdbcDao.get(object)) ;--lock!!
    	
    	for (Long privilegeId:privilegeIds) {
        	if (hasChildren(privilegeId)) {
        		throw new BusinessException("1032") ;//1032', '子权限存在，本操作无效！
        	}
    	}
    	
        PrivilegeSO criterion = new PrivilegeSO() ;
        criterion.setPrivilegeIds(privilegeIds) ;
        
        jdbcDao.delete(OrganizationPrivilege.class, criterion) ;
        jdbcDao.delete(Privilege.class, criterion) ;
        
    }

    private boolean hasChildren(Long privilegeId) {
    	Privilege so = new Privilege() ;
    	so.setPro_privilege_id(privilegeId) ;
    	return jdbcDao.getName("systemSQLs:privilegeChildrenCount", so, 0L) > 0 ;
    }
    
    private int lock(Long privilegeId) {
    	if (privilegeId == null) {
    		return 0;
    	}
    	Privilege p = new Privilege() ;
    	p.setOperate(Privilege.OPERATE_UPDATE_UNVERSION) ;
    	p.setId(privilegeId) ;
    	p.addInclusions("id") ;
    	
    	return jdbcDao.update(p) ;
    }

    public ArrayPageList<Privilege> query(
            PrivilegeSO privilegeSO) throws Exception {
        
        if (privilegeSO == null) {
        	privilegeSO = new PrivilegeSO() ;
        }
        privilegeSO.addAsc("name_") ;
        
        return (ArrayPageList<Privilege>)jdbcDao.query(privilegeSO, Privilege.class);
    }


    public ArrayPageList<Privilege> possessedByUser(Long userId) {
    	/*
        String Sql = String.format(PrivilegeSO.PRIVILEGE_PROSESSED_BY_USER, userId) ;
        return (ArrayPageList<Privilege>)jdbcDao.query(new StringBuffer(Sql), null, null, Privilege.class);
        */
    	throw new BusinessException("Not Support method!");
    }
    
    public ArrayPageList<Privilege> possessedByOrganization(Long organizationId) {
    	/*
        String Sql = String.format(PrivilegeCriterion.PRIVILEGE_PROSESSED_BY_ORGANIZATION, organizationId) ;
        return (ArrayPageList<Privilege>)jdbcDao.query(new StringBuffer(Sql), null, null, Privilege.class);
        */
    	PrivilegeSO so = new PrivilegeSO() ;
    	so.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE) ;
    	so.setOrganization_id(organizationId) ;
    	
    	return (ArrayPageList<Privilege>)jdbcDao.queryName(
    			"systemSQLs:privilegeProsessedByOrganization", so, Privilege.class) ;
    }
    
    public ArrayPageList<Privilege> possessedByRole(Long roleId) {
    	PrivilegeSO so = new PrivilegeSO() ;
    	so.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE) ;
    	so.setRole_id(roleId) ;
    	
    	return (ArrayPageList<Privilege>)jdbcDao.queryName(
    			"systemSQLs:privilegeProsessedByRole", so, Privilege.class) ;
    }

    public ArrayPageList<Privilege> getAllByUserId(Long user_id) {
    	/*
        String Sql = String.format(PrivilegeSO.USER_ALL_PRIVILEGE, user_id) ;
        
        ArrayPageList<Privilege> privileges =
            (ArrayPageList<Privilege>)jdbcDao.query(new StringBuffer(Sql), null, null, Privilege.class);      

        return privileges ;
        */
    	PrivilegeSO so = new PrivilegeSO() ;
    	so.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE) ;
    	so.setUser_id(user_id) ;
    	
    	return (ArrayPageList<Privilege>)jdbcDao.queryName(
    			"systemSQLs:user_all_privilege", so, Privilege.class) ;
    }
}
