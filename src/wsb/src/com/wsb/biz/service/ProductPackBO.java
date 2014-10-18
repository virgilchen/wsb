package com.wsb.biz.service;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Business;
import com.wsb.biz.entity.ProductPack;
import com.wsb.biz.entity.ProductPackDetail;
import com.wsb.biz.entity.ProductPackSO;


@Service("productPackBO")
@Scope("prototype")
@Transactional
public class ProductPackBO extends BaseServiceImpl {

    public ProductPack create(ProductPack productPack, Long[] product_ids, Integer product_quantitys[]) {  

    	ProductPack newItem = (ProductPack) jdbcDao.insert(productPack) ;
    	
    	this.addProductPackDetail(product_ids, product_quantitys, newItem.getId(), false);
        
        return newItem;
    }
    
    public void update(ProductPack productPack, Long[] product_ids, Integer product_quantitys[]) {
    	
        jdbcDao.update(productPack) ;
    	
    	this.addProductPackDetail(product_ids, product_quantitys, productPack.getId(), true);
    }
    
    private void addProductPackDetail(Long[] product_ids, Integer product_quantitys[], Long prodPackId, boolean isDeleteFirst) {

    	if (isDeleteFirst) {
        	ProductPackDetail so = new ProductPackDetail() ;
        	so.setProd_pack_id(prodPackId);
        	jdbcDao.delete(ProductPackDetail.class, so);
    	}
    	
    	for (int i = 0 ; i < product_ids.length ; i ++) {
    		ProductPackDetail detail = new ProductPackDetail();
    		detail.setProd_pack_id(prodPackId);
    		detail.setProd_id(product_ids[i]);
    		detail.setQuantity(product_quantitys[i]);
    		
    		jdbcDao.insert(detail) ;
    	}
    }
    

    public void delete(ProductPack productPack) {
    	
        jdbcDao.delete(productPack) ;
        
    	ProductPackDetail so = new ProductPackDetail() ;
    	so.setProd_pack_id(productPack.getId());
    	jdbcDao.delete(ProductPackDetail.class, so);
        
    }

    public void deleteAll(Long[] productPackIds) {
    	
    	/*
        ProductPackSO criterion = new ProductPackSO() ;
        criterion.setIds(productPackIds) ;
        jdbcDao.delete(ProductPack.class, criterion) ;
        */
    	
    	for (Long productPackId : productPackIds) {
    		ProductPack productPack = new ProductPack();
    		productPack.setId(productPackId);
    		this.delete(productPack);
    	}
    }
    
    
    public ArrayPageList<ProductPack> query(ProductPackSO productPackSO) {

        if (productPackSO == null) {
        	productPackSO = new ProductPackSO() ;
        }
        productPackSO.addAsc("prod_pack_name") ;
        
        return (ArrayPageList<ProductPack>)jdbcDao.query(productPackSO, ProductPack.class);
    }
    
    
    public ArrayPageList<HashMap> queryBusinesses(ProductPack productPack) {
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:queryBusinessesInProductPack", productPack, HashMap.class);
    }


    
    public ProductPack get(Long id) {  
    	ProductPack productPack = new ProductPack() ;
    	productPack.setId(id) ;
        productPack = (ProductPack) jdbcDao.get(productPack) ;
        
        
        return productPack;
    }

    public ProductPack getWithDetail(Long id) {  
    	ProductPack productPack = new ProductPack() ;
    	productPack.setId(id) ;
        productPack = (ProductPack) jdbcDao.get(productPack) ;
        
        productPack.setProductPackDetails(
        		(ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:queryAllSelectedProductDetails", productPack, HashMap.class));
        
        return productPack;
    }
    
    
}
