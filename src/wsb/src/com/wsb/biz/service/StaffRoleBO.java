package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.StaffRole;
import com.wsb.biz.entity.StaffRoleSO;


@Service("staffRoleBO")
@Scope("prototype")
@Transactional
public class StaffRoleBO extends BaseServiceImpl {
	
	public StaffRole create(StaffRole role) {  

		StaffRole newItem = (StaffRole) jdbcDao.insert(role) ;
        
        return newItem;
    }
	
    public void update(StaffRole role) {
    	
        jdbcDao.update(role) ;
    }
    

    public void delete(StaffRole role) {
    	
        jdbcDao.delete(role) ;
        
    }
	
    public void deleteAll(Long[] roleIds) {
    	
        StaffRoleSO criterion = new StaffRoleSO() ;
        criterion.setIds(roleIds) ;
        jdbcDao.delete(StaffRole.class, criterion) ;
        
    }
    
    public ArrayPageList<StaffRole> query(StaffRoleSO staffRoleSO) {

        if (staffRoleSO == null) {
        	staffRoleSO = new StaffRoleSO() ;
        }
        staffRoleSO.addDesc("staff_role_id") ;
        
        return (ArrayPageList<StaffRole>)jdbcDao.query(staffRoleSO, StaffRole.class);
    }
    
    public StaffRole get(Long id) {  
    	StaffRole role = new StaffRole() ;
    	role.setId(id) ;
    	role = (StaffRole) jdbcDao.get(role) ;
        
        return role;
    }
	
}