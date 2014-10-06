package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.CompanyOrg;
import com.wsb.biz.entity.CompanyOrgSO;
import com.wsb.biz.service.CompanyOrgBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_companyOrgAction")
@Scope("prototype")
public class CompanyOrgAction extends BaseAction implements Preparable {
	
	private static final long serialVersionUID = 7244882365197775441L;
	
	private CompanyOrg companyOrg;
	private CompanyOrgSO companyOrgSO;
	private CompanyOrgBO companyOrgBO;
	
	public String execute() throws Exception { 
		return this.list(); 
	}
	
	@Pid(value=Pid.DO_NOT_CHECK,log=false)
	public String list() throws Exception {
		ArrayPageList<CompanyOrg> pageList = companyOrgBO.query(companyOrgSO) ;
        renderList(pageList) ; 
        return null ; 
	}
	
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  
    	CompanyOrg org = companyOrgBO.get(this.id) ;
    	renderObject(org, null) ; 
        return null ;  
    }
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        
        Object newCompanyOrg = companyOrgBO.create(companyOrg) ;
        renderObject(newCompanyOrg, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
    }
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {         	
    	companyOrgBO.update(companyOrg) ;
        renderObject(companyOrg, ResponseMessage.KEY_UPDATE_OK) ;
        return null;    
    }
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {
        if (this.ids == null) { 
        	companyOrgBO.delete(companyOrg) ;
        } else {
        	companyOrgBO.deleteAll(ids) ;
        }
        renderObject(companyOrg, ResponseMessage.KEY_DELETE_OK) ;       
        return null;        
    }

	public CompanyOrg getCompanyOrg() {
		return companyOrg;
	}

	public void setCompanyOrg(CompanyOrg companyOrg) {
		this.companyOrg = companyOrg;
	}

	public CompanyOrgSO getCompanyOrgSO() {
		return companyOrgSO;
	}

	public void setCompanyOrgSO(CompanyOrgSO companyOrgSO) {
		this.companyOrgSO = companyOrgSO;
	}

	public CompanyOrgBO getCompanyOrgBO() {
		return companyOrgBO;
	}

	public void setCompanyOrgBO(CompanyOrgBO companyOrgBO) {
		this.companyOrgBO = companyOrgBO;
	}
    
}