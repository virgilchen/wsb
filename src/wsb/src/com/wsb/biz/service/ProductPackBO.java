package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.ProductPack;
import com.wsb.biz.entity.ProductPackSO;


@Service("productPackBO")
@Scope("prototype")
@Transactional
public class ProductPackBO extends BaseServiceImpl {

    public ProductPack create(ProductPack productPack) {  

    	ProductPack newItem = (ProductPack) jdbcDao.insert(productPack) ;
        
        return newItem;
    }
    
    public void update(ProductPack productPack) {
    	
        jdbcDao.update(productPack) ;
    }
    

    public void delete(ProductPack productPack) {
    	
        jdbcDao.delete(productPack) ;
        
    }

    public void deleteAll(Long[] productPackIds) {
    	
        ProductPackSO criterion = new ProductPackSO() ;
        criterion.setIds(productPackIds) ;
        jdbcDao.delete(ProductPack.class, criterion) ;
        
    }
    
    
    public ArrayPageList<ProductPack> query(ProductPackSO productPackSO) {

        if (productPackSO == null) {
        	productPackSO = new ProductPackSO() ;
        }
        productPackSO.addDesc("prod_pack_name") ;
        
        return (ArrayPageList<ProductPack>)jdbcDao.query(productPackSO, ProductPack.class);
    }



    public ProductPack get(Long id) {  
    	ProductPack productPack = new ProductPack() ;
    	productPack.setId(id) ;
        productPack = (ProductPack) jdbcDao.get(productPack) ;
        
        
        return productPack;
    }
    
}
