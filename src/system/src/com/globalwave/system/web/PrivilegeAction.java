package com.globalwave.system.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.Privilege;
import com.globalwave.system.entity.PrivilegeSO;
import com.globalwave.system.service.PrivilegeBO;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;

@Service("system_privilegeAction")
@Scope("prototype")
public class PrivilegeAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 8582051849254108462L;
    
    private PrivilegeBO privilegeBO ;
    private Privilege privilege ;
    private Long user_id ;
    private Long organization_id ;
    private PrivilegeSO privilegeSO ; 

    
    public String execute() throws Exception {    
        return this.list();    
    }

    @Pid(value=121, log=false)
    public String list() throws Exception {      
        
        ArrayPageList<Privilege> pageList = 
            privilegeBO.query(privilegeSO) ;

        renderList(pageList) ; 
        //renderXML(pageList.asXML()) ;
        
        return null ;    
        
    }

    @Pid(value=122)
    public String create()  throws Exception {              
        
        Object newPrivilege = privilegeBO.create(privilege) ;
 
        CodeHelper.reload("Privilege") ;
        
        renderXML(this.getSuccessCreateMessage(newPrivilege).asXML()) ;
        
        return null;    
        
    }

    @Pid(value=123)
    public String update()  throws Exception {     
        
        privilegeBO.update(privilege) ;

        CodeHelper.reload("Privilege") ;
        
        renderXML(this.getSuccessUpdateMessage(privilege).asXML()) ;
        
        return null;    
        
    }

    @Pid(value=124)
    public String delete()  throws Exception {
        if (this.ids == null) { 
            privilegeBO.delete(privilege) ;
        } else {
            privilegeBO.deleteAll(this.ids) ;
        }

        CodeHelper.reload("Privilege") ;
        
        renderXML(this.getSuccessDeleteMessage().asXML()) ;
        
        return null;    
        
    }

    @Pid(value=101)
    public String possessedByUser() throws Exception {
        ArrayPageList<Privilege> pageList = 
            privilegeBO.possessedByUser(user_id) ;

        renderXML(pageList.asXML()) ;        
        return null;    
        
    }

    @Pid(value=111)
    public String possessedByOrganization() throws Exception {
        ArrayPageList<Privilege> pageList = 
            privilegeBO.possessedByOrganization(organization_id) ;

        renderXML(pageList.asXML()) ;        
        return null;    
        
    }
    
    public void prepare() throws Exception {
        
    }
    
    public void setPrivilegeBO(PrivilegeBO privilegeService) {
        this.privilegeBO = privilegeService;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public PrivilegeSO getPrivilegeSO() {
        return privilegeSO;
    }

    public void setPrivilegeSO(PrivilegeSO privilegeCriterion) {
        this.privilegeSO = privilegeCriterion;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Long organization_id) {
        this.organization_id = organization_id;
    }

}
