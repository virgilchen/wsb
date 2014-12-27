package com.wsb.biz.web;




import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.AssetsTransaction;
import com.wsb.biz.entity.AssetsTransactionSO;
import com.wsb.biz.service.AssetsTransactionBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_assetsTransactionAction")
@Scope("prototype")
public class AssetsTransactionAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private AssetsTransactionBO assetsTransactionBO ;
    private AssetsTransaction assetsTransaction ;
    private AssetsTransactionSO assetsTransactionSO ; 
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<AssetsTransaction> pageList = assetsTransactionBO.query(assetsTransactionSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	AssetsTransaction org = assetsTransactionBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newAssetsTransaction = assetsTransactionBO.create(assetsTransaction) ;

        renderObject(newAssetsTransaction, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        assetsTransactionBO.update(assetsTransaction) ;

        renderObject(assetsTransaction, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            assetsTransactionBO.delete(assetsTransaction) ;
        } else {
            assetsTransactionBO.deleteAll(ids) ;
        }

        
        renderObject(assetsTransaction, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


    public void setAssetsTransactionBO(AssetsTransactionBO assetsTransactionBO) {
		this.assetsTransactionBO = assetsTransactionBO;
	}

    public AssetsTransaction getAssetsTransaction() {
        return assetsTransaction;
    }

    public void setAssetsTransaction(AssetsTransaction assetsTransaction) {
        this.assetsTransaction = assetsTransaction;
    }

    public AssetsTransactionSO getAssetsTransactionSO() {
        return assetsTransactionSO;
    }

    public void setAssetsTransactionSO(AssetsTransactionSO assetsTransactionSO) {
        this.assetsTransactionSO = assetsTransactionSO;
    }

}
