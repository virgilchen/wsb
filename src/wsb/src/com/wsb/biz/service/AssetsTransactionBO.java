package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.wsb.biz.entity.AssetsTransaction;
import com.wsb.biz.entity.AssetsTransactionSO;


@Service("assetsTransactionBO")
@Scope("prototype")
@Transactional
public class AssetsTransactionBO extends BaseServiceImpl {

    public AssetsTransaction create(AssetsTransaction assetsTransaction) {  

    	AssetsTransaction newItem = (AssetsTransaction) jdbcDao.insert(assetsTransaction) ;
        
        return newItem;
    }
    
    public void update(AssetsTransaction assetsTransaction) {
    	
        jdbcDao.update(assetsTransaction) ;
    }
    

    public void delete(AssetsTransaction assetsTransaction) {
    	
        jdbcDao.delete(assetsTransaction) ;
        
    }

    public void deleteAll(Long[] assetsTransactionIds) {
    	
        AssetsTransactionSO criterion = new AssetsTransactionSO() ;
        criterion.setIds(assetsTransactionIds) ;
        jdbcDao.delete(AssetsTransaction.class, criterion) ;
        
    }
    
    
    public ArrayPageList<AssetsTransaction> query(AssetsTransactionSO assetsTransactionSO) {

        if (assetsTransactionSO == null) {
        	assetsTransactionSO = new AssetsTransactionSO() ;
        }
        assetsTransactionSO.addDesc("assetsTransaction_timestamp") ;
        
        return (ArrayPageList<AssetsTransaction>)jdbcDao.query(assetsTransactionSO, AssetsTransaction.class);
    }



    public AssetsTransaction get(Long id) {  
    	AssetsTransaction org = new AssetsTransaction() ;
    	org.setId(id) ;
        org = (AssetsTransaction) jdbcDao.get(org) ;
        
        
        return org;
    }
    
}
