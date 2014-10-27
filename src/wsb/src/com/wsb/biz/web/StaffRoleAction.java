package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.web.annotations.Pid;
import com.globalwave.util.GsonUtil;
import com.wsb.biz.entity.CompanyOrgSO;
import com.wsb.biz.entity.StaffRole;
import com.wsb.biz.entity.StaffRoleSO;
import com.wsb.biz.service.StaffRoleBO;
import com.wsb.biz.service.CompanyOrgBO;
import com.opensymphony.xwork2.Preparable;


@Service("biz_staffRoleAction")
@Scope("prototype")
public class StaffRoleAction extends BaseAction implements Preparable {
	
	private static final long serialVersionUID = 7244882365197775441L;
	
	private StaffRole staffRole;
	private StaffRoleSO staffRoleSO;
	private StaffRoleBO staffRoleBO;
	private Long[] page_ids;
	
	public String execute() throws Exception { 
        return this.list(); 
    }
	
	public String view() throws Exception { 
    	
		CompanyOrgSO companyOrgSO = new CompanyOrgSO();
		
    	this.getRequest().setAttribute(
    			"orgJson", 
    			GsonUtil.getGson().toJson(getCompanyOrgBO().query(companyOrgSO)));
    	
        return super.view(); 
    }
    

	@Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<StaffRole> pageList = staffRoleBO.query(staffRoleSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	StaffRole role = staffRoleBO.get(this.id, true) ;

    	renderObject(role, null) ; 
        return null ;  
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newRole = staffRoleBO.create(staffRole, page_ids) ;

        renderObject(newRole, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
    	staffRoleBO.update(staffRole, page_ids) ;

        renderObject(staffRole, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
        	staffRoleBO.delete(staffRole) ;
        } else {
        	staffRoleBO.deleteAll(ids) ;
        }

        
        renderObject(staffRole, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


	public StaffRole getStaffRole() {
		return staffRole;
	}


	public void setStaffRole(StaffRole staffRole) {
		this.staffRole = staffRole;
	}


	public StaffRoleSO getStaffRoleSO() {
		return staffRoleSO;
	}


	public void setStaffRoleSO(StaffRoleSO staffRoleSO) {
		this.staffRoleSO = staffRoleSO;
	}


	public StaffRoleBO getStaffRoleBO() {
		return staffRoleBO;
	}


	public void setStaffRoleBO(StaffRoleBO staffRoleBO) {
		this.staffRoleBO = staffRoleBO;
	}    
	
    private CompanyOrgBO getCompanyOrgBO() {
    	return (CompanyOrgBO)CodeHelper.getAppContext().getBean("companyOrgBO");
	}
	public Long[] getPage_ids() {
		return page_ids;
	}
	public void setPage_ids(Long[] page_ids) {
		this.page_ids = page_ids;
	}
}