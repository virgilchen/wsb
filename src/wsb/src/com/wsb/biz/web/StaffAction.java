package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.web.annotations.Pid;
import com.itextpdf.text.log.SysoLogger;
import com.wsb.biz.entity.Staff;
import com.wsb.biz.entity.StaffSO;
import com.wsb.biz.service.StaffBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_staffAction")
@Scope("prototype")

public class StaffAction extends BaseAction implements Preparable  {
private static final long serialVersionUID = 7244882365197775441L;
    
    private StaffBO staffBO ;
    private Staff staff ;
    private StaffSO staffSO ; 
    
    public String execute() throws Exception {        
        
        return this.list();        
        
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Staff> pageList = staffBO.query(staffSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Staff staff = staffBO.get(this.id) ;

    	renderObject(staff, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newStaff = staffBO.create(staff) ;

//        CodeHelper.reload("StaffUpper");
        
        renderObject(newStaff, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        staffBO.update(staff) ;
        
//        CodeHelper.reload("StaffUpper");

        renderObject(staff, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            staffBO.delete(staff) ;
        } else {
            staffBO.deleteAll(ids) ;
        }
        
//        CodeHelper.reload("StaffUpper");
        
        renderObject(staff, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String updateStatus()  throws Exception {    
    	if(staff.getStaff_status() != null){
    		if(staff.getStaff_status().equalsIgnoreCase("无效")){
    			staff.setStaff_status("有效");
    		}else{
    			staff.setStaff_status("无效");
    		}
    	}else{
    		staff.setStaff_status("无效");
    	}
    	
        staffBO.updateStatus(staff) ;
        
//        CodeHelper.reload("StaffUpper");

        renderObject(staff, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }


    public void setStaffBO(StaffBO staffBO) {
		this.staffBO = staffBO;
	}

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public StaffSO getStaffSO() {
        return staffSO;
    }

    public void setStaffSO(StaffSO staffSO) {
        this.staffSO = staffSO;
    }

}
