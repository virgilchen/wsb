package com.globalwave.system.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.Organization;
import com.globalwave.system.entity.OrganizationSO;
import com.globalwave.system.service.OrganizationBO;
import com.globalwave.system.service.UserBO;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;

@Service("system_organizationAction")
@Scope("prototype")
public class OrganizationAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private OrganizationBO organizationBO ;
    private Organization organization ;
    private Long user_id ;
    private OrganizationSO organizationSO ; 
    private Long[] privilege_ids ; 

    
    public String execute() throws Exception {        
        
        return this.list();        
        
    }
    

    @Pid(value=Pid.LOGINED, log=false)
    public String list() throws Exception {  
    	//organizationSO.setPro_organization_id_exclude(0L) ;// Just for loss, 取二级组织
        ArrayPageList<Organization> pageList = organizationBO.query(organizationSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.LOGINED,log=false)
    public String get() throws Exception {  

    	Organization org = organizationBO.get(this.id, true) ;

        //renderXML(pageList.asXML()) ;
    	/*
        Gson gson = new GsonBuilder().setDateFormat("yyyymmdd").create() ;
        JsonElement jsonTree = gson.toJsonTree(org); 
        JsonObject jsonObject = new JsonObject(); 
        jsonObject.add("org", jsonTree);
        
        System.err.println(jsonObject);
        renderJson(jsonObject.toString()) ;
        */
    	renderObject(org, null) ; 
        return null ;  
    }

    //@Pid(value=122)
    @Pid(value=2140)
    public String create()  throws Exception {        

    	//organization.setPro_organization_id(1L) ;// just for loss
        
        Object newOrganization = organizationBO.create(organization, privilege_ids) ;

        CodeHelper.reload("Organization") ;
        //renderXML(this.getSuccessCreateMessage(newOrganization).asXML()) ;
        renderObject(newOrganization, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    //@Pid(value=123)
    @Pid(value=2140)
    public String update()  throws Exception {     

        
    	//organization.setPro_organization_id(1L) ;// just for loss
    	
        organizationBO.update(organization, privilege_ids) ;

        CodeHelper.reload("Organization") ;
        //renderXML(this.getSuccessUpdateMessage(organization).asXML()) ;

        renderObject(organization, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    //@Pid(value=124)
    @Pid(value=2140)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            organizationBO.delete(organization) ;
        } else {
            organizationBO.deleteAll(ids) ;
        }

        CodeHelper.reload("Organization") ;
        //renderXML(this.getSuccessDeleteMessage().asXML()) ;

        renderObject(organization, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }

    @Pid(value=2140, log=false)
    public String userBelongTo()  throws Exception {
        ArrayPageList<Organization> pageList = 
            organizationBO.userBelongTo(user_id) ;

        renderXML(pageList.asXML()) ;        
        return null;    
        
    }
    
    public void prepare() throws Exception {
        // TODO Auto-generated method stub
        
    }

    public void setOrganizationBO(OrganizationBO organizationBO) {
		this.organizationBO = organizationBO;
	}

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public OrganizationSO getOrganizationSO() {
        return organizationSO;
    }

    public void setOrganizationSO(OrganizationSO organizationCriterion) {
        this.organizationSO = organizationCriterion;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long[] getPrivilege_ids() {
        return privilege_ids;
    }

    public void setPrivilege_ids(Long[] privilege_ids) {
        this.privilege_ids = privilege_ids;
    }


    public UserBO getUserBO() {
    	return (UserBO)CodeHelper.getAppContext().getBean("userBO") ;
    }

}
