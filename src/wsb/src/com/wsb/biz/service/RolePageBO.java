package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.RolePage;

@Service("rolePageBO")
@Scope("prototype")
@Transactional
public class RolePageBO extends BaseServiceImpl {

    public RolePage create(RolePage rolePage) {  

    	RolePage newItem = (RolePage) jdbcDao.insert(rolePage) ;
        
        return newItem;
    }
    
    public void update(RolePage rolePage) {
    	
        jdbcDao.update(rolePage) ;
    }
    
/*
    public void delete(RolePage rolePage) {
    	
        jdbcDao.delete(rolePage) ;
        
    }

    public void deleteAll(Long[] rolePageIds) {
    	
        RolePageSO criterion = new RolePageSO() ;
        criterion.setIds(rolePageIds) ;
        jdbcDao.delete(RolePage.class, criterion) ;
        
    }
    
    
    public ArrayPageList<RolePage> query(RolePageSO rolePageSO) {

        if (rolePageSO == null) {
        	rolePageSO = new RolePageSO() ;
        }
        rolePageSO.addDesc("rolePage_timestamp") ;
        
        return (ArrayPageList<RolePage>)jdbcDao.query(rolePageSO, RolePage.class);
    }*/



    
}
