package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.OrderProcess;
import com.wsb.biz.entity.RolePage;
import com.wsb.biz.entity.Staff;
import com.wsb.biz.entity.StaffRole;
import com.wsb.biz.entity.StaffRoleSO;


@Service("staffRoleBO")
@Scope("prototype")
@Transactional
public class StaffRoleBO extends BaseServiceImpl {
	
	public StaffRole create(StaffRole role, Long[] pageIds) {  

		StaffRole newItem = (StaffRole) jdbcDao.insert(role) ;
        
		RolePage rp = new RolePage();
		rp.setStaff_role_id(newItem.getId());
		
		for (Long pageId: pageIds) {
			rp.setPage_id(pageId);
			jdbcDao.insert(rp) ;
		}
		
        return newItem;
    }
	
    public void update(StaffRole role, Long[] pageIds) {
    	
        jdbcDao.update(role) ;
        
		RolePage rp = new RolePage();
		rp.setStaff_role_id(role.getId());
		
		jdbcDao.delete(RolePage.class, rp);
		
		for (Long pageId: pageIds) {
			rp.setPage_id(pageId);
			jdbcDao.insert(rp) ;
		}
    }
    

    public void delete(StaffRole role) {
    	Long roleId = role.getId();
    	
    	Staff staff = new Staff() ;
    	staff.setStaff_role_id(roleId);
    	
    	if (jdbcDao.find(staff) != null) {
    		throw new BusinessException(1401L);//'1401', '用户角色已有用户，不能被删除！'
    	}
    	
    	OrderProcess orderProcess = new OrderProcess() ;
    	orderProcess.setProcs_staff_role_id(roleId);
    	
    	if (jdbcDao.find(orderProcess) != null) {
    		throw new BusinessException(1402L);//'1402', '用户角色已经被流程环节使用，不能被删除！
    	}
    	
        jdbcDao.delete(role) ;
        
		RolePage rp = new RolePage();
		rp.setStaff_role_id(roleId);
		
		jdbcDao.delete(RolePage.class, rp);
        
    }
	
    public void deleteAll(Long[] roleIds) {
    	/*
        StaffRoleSO criterion = new StaffRoleSO() ;
        criterion.setIds(roleIds) ;
        jdbcDao.delete(StaffRole.class, criterion) ;
        */
    	StaffRole role = new StaffRole() ;
    	for (Long roleId : roleIds) {
    		role.setId(roleId);
    		this.delete(role);
    	}
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

    public StaffRole get(Long id, boolean isCreateNewWhenNotExist) throws Exception {  
    	StaffRole staffRole = new StaffRole() ;
    	staffRole.setId(id) ;
        staffRole = (StaffRole) jdbcDao.get(staffRole) ;
        
        if (staffRole == null) {
        	if (isCreateNewWhenNotExist) {
        		staffRole = new StaffRole() ;
        	} else {
        	    return staffRole ;
        	}
        }
        
        staffRole.setRolePages(PageBO.getPageBO().queryAccessablePagesByRole(id)) ;
        
        return staffRole;
    }
	
    public static StaffRoleBO getStaffRoleBO() {
    	return (StaffRoleBO)CodeHelper.getAppContext().getBean("staffRoleBO");
    }
}