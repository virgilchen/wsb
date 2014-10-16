package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.RecmdtInventory;
import com.wsb.biz.entity.RecmdtInventorySO;



@Service("recmdtInventoryBO")
@Scope("prototype")
@Transactional
public class RecmdtInventoryBO extends BaseServiceImpl {
	
	public RecmdtInventory create(RecmdtInventory recmdt) {  

		RecmdtInventory newItem = (RecmdtInventory) jdbcDao.insert(recmdt) ;
        
        return newItem;
    }
	
    public void update(RecmdtInventory recmdt) {
    	
        jdbcDao.update(recmdt) ;
    }
	
    public void delete(RecmdtInventory recmdt) {
    	
        jdbcDao.delete(recmdt) ;
        
    }
    
    public void deleteAll(Long[] recmdtIds) {
    	
    	RecmdtInventorySO criterion = new RecmdtInventorySO() ;
        criterion.setIds(recmdtIds) ;
        jdbcDao.delete(RecmdtInventory.class, criterion) ;
        
    }
    
    public ArrayPageList<RecmdtInventory> query(RecmdtInventorySO recmdtInventorySO) {

        if (recmdtInventorySO == null) {
        	recmdtInventorySO = new RecmdtInventorySO() ;
        }
        recmdtInventorySO.addDesc("recmdt_id") ;
        
        return (ArrayPageList<RecmdtInventory>)jdbcDao.query(recmdtInventorySO, RecmdtInventory.class);
    }
    
    public RecmdtInventory get(Long id) {  
    	RecmdtInventory recmdt = new RecmdtInventory() ;
    	recmdt.setId(id) ;
    	recmdt = (RecmdtInventory) jdbcDao.get(recmdt) ;
        
        return recmdt;
    }
    
    
}