package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.ProductPack;
import com.wsb.biz.entity.ProductPackSO;
import com.wsb.biz.service.ProductPackBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_productPackAction")
@Scope("prototype")
public class ProductPackAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private ProductPackBO productPackBO ;
    private ProductPack productPack ;
    private ProductPackSO productPackSO ; 
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<ProductPack> pageList = productPackBO.query(productPackSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	ProductPack org = productPackBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newProductPack = productPackBO.create(productPack) ;

        renderObject(newProductPack, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        productPackBO.update(productPack) ;

        renderObject(productPack, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            productPackBO.delete(productPack) ;
        } else {
            productPackBO.deleteAll(ids) ;
        }

        
        renderObject(productPack, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


    public void setProductPackBO(ProductPackBO productPackBO) {
		this.productPackBO = productPackBO;
	}

    public ProductPack getProductPack() {
        return productPack;
    }

    public void setProductPack(ProductPack productPack) {
        this.productPack = productPack;
    }

    public ProductPackSO getProductPackSO() {
        return productPackSO;
    }

    public void setProductPackSO(ProductPackSO productPackSO) {
        this.productPackSO = productPackSO;
    }

}
