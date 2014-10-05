package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.Business;
import com.wsb.biz.entity.BusinessSO;
import com.wsb.biz.service.BusinessBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_businessAction")
@Scope("prototype")
public class BusinessAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private BusinessBO businessBO ;
    private Business business ;
    private BusinessSO businessSO ; 
    
    public String execute() throws Exception {        
        
        return this.list();        
        
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Business> pageList = businessBO.query(businessSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Business org = businessBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newBusiness = businessBO.create(business) ;

        CodeHelper.reload("BusinessUpper");
        
        renderObject(newBusiness, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        businessBO.update(business) ;
        
        CodeHelper.reload("BusinessUpper");

        renderObject(business, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            businessBO.delete(business) ;
        } else {
            businessBO.deleteAll(ids) ;
        }
        
        CodeHelper.reload("BusinessUpper");
        
        renderObject(business, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


    public void setBusinessBO(BusinessBO businessBO) {
		this.businessBO = businessBO;
	}

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public BusinessSO getBusinessSO() {
        return businessSO;
    }

    public void setBusinessSO(BusinessSO businessSO) {
        this.businessSO = businessSO;
    }

}
