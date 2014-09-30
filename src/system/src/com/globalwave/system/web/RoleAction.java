package com.globalwave.system.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.entity.Role;
import com.globalwave.system.entity.RoleSO;
import com.globalwave.system.service.RoleBO;
import com.globalwave.system.web.annotations.Pid;
import com.opensymphony.xwork2.Preparable;

@Service("system_roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private RoleBO roleBO ;
    private Role role ;
    private RoleSO roleSO ; 
    private Long[] privilege_ids ; 
    
    public String execute() throws Exception {        
        
        return this.list();        
        
    }
    

    @Pid(value=2150, log=false)
    public String list() throws Exception {  
    	//roleSO.setPro_role_id_exclude(0L) ;// Just for loss, 取二级组织
        ArrayPageList<Role> pageList = roleBO.query(roleSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=2150, log=false)
    public String get() throws Exception {  

    	Role org = roleBO.get(this.id, true) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    //@Pid(value=132)
    @Pid(value=2150)
    public String create()  throws Exception {        

    	//role.setPro_role_id(1L) ;// just for loss
        
        Object newRole = roleBO.create(role, privilege_ids) ;

        renderObject(newRole, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    //@Pid(value=133)
    @Pid(value=2150)
    public String update()  throws Exception {     

        
    	//role.setPro_role_id(1L) ;// just for loss
    	
        roleBO.update(role, privilege_ids) ;


        renderObject(role, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    //@Pid(value=134)
    @Pid(value=2150)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            roleBO.delete(role) ;
        } else {
            roleBO.deleteAll(ids) ;
        }
        
        renderObject(role, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }
/*
    
    @Pid(value=101)
    public String userBelongTo()  throws Exception {
        ArrayPageList<Role> pageList = 
            roleBO.userBelongTo(user_id) ;

        renderXML(pageList.asXML()) ;        
        return null;    
        
    }
 */   
    public void prepare() throws Exception {
        // TODO Auto-generated method stub
        
    }

    public void setRoleBO(RoleBO roleBO) {
		this.roleBO = roleBO;
	}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public RoleSO getRoleSO() {
        return roleSO;
    }

    public void setRoleSO(RoleSO roleSO) {
        this.roleSO = roleSO;
    }


    public Long[] getPrivilege_ids() {
        return privilege_ids;
    }

    public void setPrivilege_ids(Long[] privilege_ids) {
        this.privilege_ids = privilege_ids;
    }


}
