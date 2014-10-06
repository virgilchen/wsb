package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.entity.OrderProdPackSO;


@Service("orderProdPackBO")
@Scope("prototype")
@Transactional
public class OrderProdPackBO extends BaseServiceImpl {

    public OrderProdPack create(OrderProdPack orderProdPack) {  

    	OrderProdPack newItem = (OrderProdPack) jdbcDao.insert(orderProdPack) ;
        
        return newItem;
    }
    
    public void update(OrderProdPack orderProdPack) {
    	
        jdbcDao.update(orderProdPack) ;
    }
    

    public void delete(OrderProdPack orderProdPack) {
    	
        jdbcDao.delete(orderProdPack) ;
        
    }

    public void deleteAll(Long[] orderProdPackIds) {
    	
        OrderProdPackSO criterion = new OrderProdPackSO() ;
        criterion.setIds(orderProdPackIds) ;
        jdbcDao.delete(OrderProdPack.class, criterion) ;
        
    }
    
    
    public ArrayPageList<OrderProdPack> query(OrderProdPackSO orderProdPackSO) {

        if (orderProdPackSO == null) {
        	orderProdPackSO = new OrderProdPackSO() ;
        }
        orderProdPackSO.addDesc("orderProdPack_timestamp") ;
        
        return (ArrayPageList<OrderProdPack>)jdbcDao.query(orderProdPackSO, OrderProdPack.class);
    }



    public OrderProdPack get(Long id) {  
    	OrderProdPack org = new OrderProdPack() ;
    	org.setId(id) ;
        org = (OrderProdPack) jdbcDao.get(org) ;
        
        
        return org;
    }
    
}
