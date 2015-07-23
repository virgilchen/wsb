package com.wsb.biz.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.common.Util;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.SessionUser;
import com.wsb.biz.entity.Page;
import com.wsb.biz.entity.Staff;
import com.wsb.biz.entity.StaffSO;


@Service("staffBO")
@Scope("prototype")
@Transactional

public class StaffBO extends BaseServiceImpl {
	public Staff create(Staff staff) {  

		Staff staffSO = new Staff();
		staffSO.setStaff_login_profile(staff.getStaff_login_profile());
		
		if (this.jdbcDao.find(staffSO) != null) {
			throw new BusinessException(1110L);//1110', '用户登录账号已经被使用，请使用其它登录账号
		}
		
		staff.setStaff_login_pwd(Util.hash(staff.getStaff_login_pwd()));
    	Staff newItem = (Staff) jdbcDao.insert(staff) ;
        
        return newItem;
    }
    
    public void update(Staff staff) {
    	if(staff.getStaff_login_pwd() == null || staff.getStaff_login_pwd().equalsIgnoreCase("")){
    		staff.addExclusions("staff_login_pwd");
    	}else{
    		staff.setStaff_login_pwd(Util.hash(staff.getStaff_login_pwd()));
    	}
        jdbcDao.update(staff) ;
    }
    

    public void delete(Staff staff) {
    	String sql = "select o.order_id from order_rt o where o.order_init_staff_id = ? " + 
                           "or exists(select 1 from order_prod_pack_event_rt e where o.order_id=e.order_id and e.event_staff_id=?) limit 0,1";
    	Long id = jdbcDao.getLong(
    			sql, 
    			new Object[]{staff.getId(), staff.getId()});
    	
    	if (id != null && id > 0) {
    		throw new BusinessException(1007L);// '1007', '用户已有业务操作，不能被删除！'
    	}
    	
        jdbcDao.delete(staff) ;
        
    }

    public void deleteAll(Long[] staffIds) {
    	/*
        StaffSO staff = new StaffSO() ;
        staff.setIds(staffIds) ;
        jdbcDao.delete(Staff.class, staff) ;*/
    	
    	for (Long staffId : staffIds) {
    		
    		Staff staff = new Staff();
    		staff.setId(staffId);
    		
    		this.delete(staff);
    	}
        
    }

    
    public ArrayPageList<Staff> query(StaffSO staffSO) {

        if (staffSO == null) {
        	staffSO = new StaffSO() ;
        }
        staffSO.addAsc("staff_id") ;
        
        return (ArrayPageList<Staff>)jdbcDao.query(staffSO, Staff.class);
    }
    
    public SessionUser login(StaffSO staffSO) {
    	SessionUser result = new SessionUser();
    	
    	String password = staffSO.getStaff_login_pwd();
    	staffSO.setStaff_login_pwd(null);
    	
    	ArrayPageList<Staff> staffs = this.query(staffSO);
        if (staffs.size() == 0) {
        	throw new BusinessException(1005L);//1005', 'Staff is not exists!
        }
        
        Staff staff= staffs.get(0);
        
        if (!Staff.STATUS_ACTIVE.equals(staff.getStaff_status())) {
        	throw new BusinessException(1112L);//1112', '用户非激活状态，不能登录！
        }
        
        if (!Util.hash(password).equals(staff.getStaff_login_pwd())){
        	throw new BusinessException(1006L);// password is wrong
        }
        
        result.setStaff(staff);
        
        ArrayPageList<Page> privileges =
        		PageBO.getPageBO().queryByRole(staff.getStaff_role_id()) ;
            
        Set<Short> privilegeIds = new HashSet<Short>(privileges.size()) ;
        for (int i = privileges.size() - 1 ; i >= 0 ; i --) {
            privilegeIds.add(privileges.get(i).getId().shortValue()) ;
        }
        
        result.setPrivilege_ids(privilegeIds) ;
        
        staff.setStaff_last_login_time(U.currentTimestamp());
        staff.addInclusions("staff_last_login_time");
        
        jdbcDao.update(staff);
        
        return result;
    }

    public void changePassword(Staff staff) {
    	Staff oldStaff = this.get(SessionUser.get().getStaff().getId());

        if (!oldStaff.getStaff_login_pwd().equals(Util.hash(staff.getOld_password()))){
        	throw new BusinessException(1109L);//'1109', '原密码不正确，更改密码失败！' );
        }
        
        oldStaff.setStaff_login_pwd(Util.hash(staff.getStaff_login_pwd()));
        oldStaff.addInclusions("staff_login_pwd");
        
        jdbcDao.update(oldStaff);
    }


    public Staff get(Long id) {  
    	Staff staff = new Staff() ;
    	staff.setId(id) ;
    	staff = (Staff) jdbcDao.get(staff) ;
        
        
        return staff;
    }
    
    public void updateStatus(Staff staff) {
    	staff.addInclusions("staff_status");
    	
        jdbcDao.update(staff) ;
    }
    
    public static StaffBO getStaffBO() {
    	return (StaffBO) CodeHelper.getAppContext().getBean("staffBO");
    }
}
