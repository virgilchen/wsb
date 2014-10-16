package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.wsb.biz.entity.Staff;
import com.wsb.biz.entity.StaffSO;


@Service("staffBO")
@Scope("prototype")
@Transactional

public class StaffBO extends BaseServiceImpl {
	public Staff create(Staff staff) {  

    	Staff newItem = (Staff) jdbcDao.insert(staff) ;
        
        return newItem;
    }
    
    public void update(Staff staff) {
    	
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
}
