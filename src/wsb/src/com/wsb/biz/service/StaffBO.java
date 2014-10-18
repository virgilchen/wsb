package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.Util;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.Staff;
import com.wsb.biz.entity.StaffSO;


@Service("staffBO")
@Scope("prototype")
@Transactional

public class StaffBO extends BaseServiceImpl {
	public Staff create(Staff staff) {  

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
    	
        jdbcDao.delete(staff) ;
        
    }

    public void deleteAll(Long[] staffIds) {
    	
        StaffSO staff = new StaffSO() ;
        staff.setIds(staffIds) ;
        jdbcDao.delete(Staff.class, staff) ;
        
    }

    
    public ArrayPageList<Staff> query(StaffSO staffSO) {

        if (staffSO == null) {
        	staffSO = new StaffSO() ;
        }
        staffSO.addAsc("staff_id") ;
        
        return (ArrayPageList<Staff>)jdbcDao.query(staffSO, Staff.class);
    }
    
    public Staff login(StaffSO staffSO) {
    	String password = staffSO.getStaff_login_pwd();
    	staffSO.setStaff_login_pwd(null);
    	
    	ArrayPageList<Staff> staffs = this.query(staffSO);
        if (staffs.size() == 0) {
        	throw new BusinessException(1005L);//1005', 'Staff is not exists!
        }
        
        Staff staff= staffs.get(0);
        
        if (!Util.hash(password).equals(staff.getStaff_login_pwd())){
        	throw new BusinessException(1006L);// password is wrong
        }
        
        return staff;
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
