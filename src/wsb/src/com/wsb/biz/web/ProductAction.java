package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.Product;
import com.wsb.biz.entity.ProductSO;
import com.wsb.biz.service.ProductBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_productAction")
@Scope("prototype")
public class ProductAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private ProductBO productBO ;
    private Product product ;
    private ProductSO productSO ; 
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Product> pageList = productBO.query(productSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Product org = productBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newProduct = productBO.create(product) ;

        renderObject(newProduct, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        productBO.update(product) ;

        renderObject(product, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            productBO.delete(product) ;
        } else {
            productBO.deleteAll(ids) ;
        }

        
        renderObject(product, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


    public void setProductBO(ProductBO productBO) {
		this.productBO = productBO;
	}

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductSO getProductSO() {
        return productSO;
    }

    public void setProductSO(ProductSO productSO) {
        this.productSO = productSO;
    }

}
