package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.wsb.biz.entity.OrderProcess;
import com.wsb.biz.entity.OrderProcessSO;


@Service("orderProcessBO")
@Scope("prototype")
@Transactional
public class OrderProcessBO extends BaseServiceImpl {

    public void save(List<OrderProcess> orderProcesses, Long businessId) {  
    	
    	OrderProcess so = new OrderProcess();
    	so.setBusiness_id(businessId);
    	
    	jdbcDao.delete(OrderProcess.class, so) ;
    	
    	for (OrderProcess op : orderProcesses) {
    		if (op == null) {
    			continue;
    		}
    		
    	    jdbcDao.insert(op) ;
    	}
        
    }
    
    public void update(OrderProcess orderProcess) {
    	
        jdbcDao.update(orderProcess) ;
    }
    

    public void delete(OrderProcess orderProcess) {
    	
        jdbcDao.delete(orderProcess) ;
        
    }

    /*
    public void deleteAll(Long[] orderProcessIds) {
    	
        OrderProcessSO criterion = new OrderProcessSO() ;
        criterion.setIds(orderProcessIds) ;
        jdbcDao.delete(OrderProcess.class, criterion) ;
        
    }*/
    
    
    public ArrayPageList<OrderProcess> query(OrderProcessSO orderProcessSO) {

        if (orderProcessSO == null) {
        	orderProcessSO = new OrderProcessSO() ;
        }
        orderProcessSO.addAsc("procs_step_no") ;
        
        return (ArrayPageList<OrderProcess>)jdbcDao.query(orderProcessSO, OrderProcess.class);
    }


/*
    public OrderProcess get(Long id) {  
    	OrderProcess org = new OrderProcess() ;
    	org.setId(id) ;
        org = (OrderProcess) jdbcDao.get(org) ;
        
        
        return org;
    }
    */
}
