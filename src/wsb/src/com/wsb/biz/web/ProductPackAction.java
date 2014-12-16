package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.web.annotations.Pid;
import com.globalwave.util.GsonUtil;
import com.wsb.biz.entity.ProductPack;
import com.wsb.biz.entity.ProductPackSO;
import com.wsb.biz.service.ProductBO;
import com.wsb.biz.service.ProductPackBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_productPackAction")
@Scope("prototype")
public class ProductPackAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private ProductPackBO productPackBO ;
    private ProductPack productPack ;
    private ProductPackSO productPackSO ; 

    private Long[] product_ids;
    private Integer[] product_quantitys;

    public String execute() throws Exception { 
        return this.list(); 
    }


    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String view() throws Exception { 
    	
    	this.getRequest().setAttribute(
    			"productsJson", 
    			GsonUtil.getGson().toJson(getProductBO().queryAllSelectableProducts()));
    	
        return super.view(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<ProductPack> pageList = productPackBO.query(productPackSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	ProductPack productPack = productPackBO.getWithDetail(this.id) ;

    	renderObject(productPack, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String queryBusinesses() throws Exception {  

    	renderList(productPackBO.queryBusinesses(productPack), null) ; 
    	
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create() throws Exception {        

        Object newProductPack = productPackBO.create(productPack, product_ids, product_quantitys) ;

        renderObject(newProductPack, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        productPackBO.update(productPack, product_ids, product_quantitys) ;

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
    
    public ProductBO getProductBO() {
        return (ProductBO)CodeHelper.getAppContext().getBean("productBO");
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

	public Long[] getProduct_ids() {
		return product_ids;
	}

	public void setProduct_ids(Long[] product_ids) {
		this.product_ids = product_ids;
	}

	public Integer[] getProduct_quantitys() {
		return product_quantitys;
	}

	public void setProduct_quantitys(Integer[] product_quantitys) {
		this.product_quantitys = product_quantitys;
	}

}
