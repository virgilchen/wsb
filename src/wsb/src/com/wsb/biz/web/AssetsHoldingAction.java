package com.wsb.biz.web;




import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.AssetsHolding;
import com.wsb.biz.entity.AssetsHoldingSO;
import com.wsb.biz.service.AssetsHoldingBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_assetsHoldingAction")
@Scope("prototype")
public class AssetsHoldingAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private AssetsHoldingBO assetsHoldingBO ;
    private AssetsHolding assetsHolding ;
    private AssetsHoldingSO assetsHoldingSO ; 
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<AssetsHolding> pageList = assetsHoldingBO.query(assetsHoldingSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	AssetsHolding org = assetsHoldingBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newAssetsHolding = assetsHoldingBO.create(assetsHolding) ;

        renderObject(newAssetsHolding, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        assetsHoldingBO.update(assetsHolding) ;

        renderObject(assetsHolding, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            assetsHoldingBO.delete(assetsHolding) ;
        } else {
            assetsHoldingBO.deleteAll(ids) ;
        }

        
        renderObject(assetsHolding, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }

    public void setAssetsHoldingBO(AssetsHoldingBO assetsHoldingBO) {
		this.assetsHoldingBO = assetsHoldingBO;
	}

    public AssetsHolding getAssetsHolding() {
        return assetsHolding;
    }

    public void setAssetsHolding(AssetsHolding assetsHolding) {
        this.assetsHolding = assetsHolding;
    }

    public AssetsHoldingSO getAssetsHoldingSO() {
        return assetsHoldingSO;
    }

    public void setAssetsHoldingSO(AssetsHoldingSO assetsHoldingSO) {
        this.assetsHoldingSO = assetsHoldingSO;
    }

}
