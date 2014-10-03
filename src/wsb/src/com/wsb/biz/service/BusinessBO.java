package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.wsb.biz.entity.Business;
import com.wsb.biz.entity.BusinessSO;


@Service("businessBO")
@Scope("prototype")
@Transactional
public class BusinessBO extends BaseServiceImpl {

    public Business create(Business business) {  

    	Business newItem = (Business) jdbcDao.insert(business) ;
        
        return newItem;
    }
    
    public void update(Business business) {
    	
        jdbcDao.update(business) ;
    }
    

    public void delete(Business business) {
    	
        jdbcDao.delete(business) ;
        
    }

    public void deleteAll(Long[] businessIds) {
    	
        BusinessSO criterion = new BusinessSO() ;
        criterion.setIds(businessIds) ;
        jdbcDao.delete(Business.class, criterion) ;
        
    }
    
    
    public ArrayPageList<Business> query(BusinessSO businessSO) {

        if (businessSO == null) {
        	businessSO = new BusinessSO() ;
        }
        businessSO.addDesc("business_name") ;
        
        return (ArrayPageList<Business>)jdbcDao.query(businessSO, Business.class);
    }



    public Business get(Long id) {  
    	Business org = new Business() ;
    	org.setId(id) ;
        org = (Business) jdbcDao.get(org) ;
        
        
        return org;
    }
    
}
