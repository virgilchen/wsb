package com.wsb.biz.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Order;
import com.wsb.biz.entity.OrderSO;


@Service("orderBO")
@Scope("prototype")
@Transactional
public class OrderBO extends BaseServiceImpl {

    public Order create(Order order) {  

    	Order newItem = (Order) jdbcDao.insert(order) ;
        
        return newItem;
    }
    
    public void update(Order order) {
    	
        jdbcDao.update(order) ;
    }
    

    public void delete(Order order) {
    	
        jdbcDao.delete(order) ;
        
    }

    public void deleteAll(Long[] orderIds) {
    	
        OrderSO criterion = new OrderSO() ;
        criterion.setIds(orderIds) ;
        jdbcDao.delete(Order.class, criterion) ;
        
    }
    
    
    public ArrayPageList<Order> query(OrderSO orderSO) {

        if (orderSO == null) {
        	orderSO = new OrderSO() ;
        }
        orderSO.addDesc("order_timestamp") ;
        
        return (ArrayPageList<Order>)jdbcDao.query(orderSO, Order.class);
    }



    public Order get(Long id) {  
    	Order org = new Order() ;
    	org.setId(id) ;
        org = (Order) jdbcDao.get(org) ;
        
        
        return org;
    }
    
}
