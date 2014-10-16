package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.web.annotations.Pid;
import com.globalwave.util.GsonUtil;
import com.wsb.biz.entity.RecmdtInventory;
import com.wsb.biz.entity.RecmdtInventorySO;
import com.wsb.biz.service.RecmdtInventoryBO;
import com.opensymphony.xwork2.Preparable;



@Service("biz_recmdtInventoryAction")
@Scope("prototype")
public class RecmdtInventoryAction extends BaseAction implements Preparable {
	
	private static final long serialVersionUID = 7244882365197775441L;
	
	private RecmdtInventory recmdtInventory;
	private RecmdtInventorySO recmdtInventorySO;
	private RecmdtInventoryBO recmdtInventoryBO;
	
	
	public String execute() throws Exception { 
        return this.list(); 
    }
	
	
	@Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<RecmdtInventory> pageList = recmdtInventoryBO.query(recmdtInventorySO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }
	
	
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	RecmdtInventory recmdt = recmdtInventoryBO.get(this.id) ;

    	renderObject(recmdt, null) ; 
        return null ;  
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newRecmdt = recmdtInventoryBO.create(recmdtInventory) ;

        renderObject(newRecmdt, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
    	recmdtInventoryBO.update(recmdtInventory) ;

        renderObject(recmdtInventory, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
    
    
    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
        	recmdtInventoryBO.delete(recmdtInventory) ;
        } else {
        	recmdtInventoryBO.deleteAll(ids) ;
        }

        
        renderObject(recmdtInventory, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


	public RecmdtInventory getRecmdtInventory() {
		return recmdtInventory;
	}


	public void setRecmdtInventory(RecmdtInventory recmdtInventory) {
		this.recmdtInventory = recmdtInventory;
	}


	public RecmdtInventorySO getRecmdtInventorySO() {
		return recmdtInventorySO;
	}


	public void setRecmdtInventorySO(RecmdtInventorySO recmdtInventorySO) {
		this.recmdtInventorySO = recmdtInventorySO;
	}


	public RecmdtInventoryBO getRecmdtInventoryBO() {
		return recmdtInventoryBO;
	}


	public void setRecmdtInventoryBO(RecmdtInventoryBO recmdtInventoryBO) {
		this.recmdtInventoryBO = recmdtInventoryBO;
	}
    
    
    
    
}