package com.wsb.biz.service;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.wsb.biz.entity.Product;
import com.wsb.biz.entity.ProductSO;


@Service("productBO")
@Scope("prototype")
@Transactional
public class ProductBO extends BaseServiceImpl {

    public Product create(Product product) {  

    	Product newItem = (Product) jdbcDao.insert(product) ;
        
        return newItem;
    }
    
    public void update(Product product) {
    	
        jdbcDao.update(product) ;
    }
    

    public void delete(Product product) {
    	
        jdbcDao.delete(product) ;
        
    }

    public void deleteAll(Long[] productIds) {
    	
        ProductSO criterion = new ProductSO() ;
        criterion.setIds(productIds) ;
        jdbcDao.delete(Product.class, criterion) ;
        
    }
    

    public ArrayPageList<Product> query(ProductSO productSO) {

        if (productSO == null) {
        	productSO = new ProductSO() ;
        }
        productSO.addDesc("prod_name") ;
        
        return (ArrayPageList<Product>)jdbcDao.query(productSO, Product.class);
    }
    
    public ArrayPageList<HashMap> queryAllSelectableProducts() {

    	//ProductSO productSO = new ProductSO() ;

        
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:queryAllSelectableProducts", null, HashMap.class);
    }
    


    public ArrayPageList<Product> queryProdByProdPackId(Long productPackId) {

    	ProductSO productSO = new ProductSO() ;
    	productSO.setProd_pack_id(productPackId);
        
        return (ArrayPageList<Product>)jdbcDao.queryName("bizSQLs:queryProdByProdPackId", productSO, Product.class);
    } 

    public Product get(Long id) {  
    	Product product = new Product() ;
    	product.setId(id) ;
        product = (Product) jdbcDao.get(product) ;
        
        
        return product;
    }
    
    
    public static ProductBO getProductBO() {
    	return (ProductBO)CodeHelper.getAppContext().getBean("productBO");
    }
}
