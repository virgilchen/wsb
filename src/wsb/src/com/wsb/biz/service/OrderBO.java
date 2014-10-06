package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Order;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.entity.OrderProdPackSO;
import com.wsb.biz.entity.OrderSO;


@Service("orderBO")
@Scope("prototype")
@Transactional
public class OrderBO extends BaseServiceImpl {

    public Order create(Order order) {  

    	Order newItem = (Order) jdbcDao.insert(order) ;
        
        return newItem;
    }
    
    public Order getOpenInfo(Long customerId) {
    	
    	Order result = new Order();
    	result.setCustomer(getCustomerBO().get(customerId));
    	
    	result.setOrder_init_time_stamp(U.currentTimestamp());
    	result.setPsdo_cust_id(result.getCustomer().getId());
    	
    	return result;
    }
    
    public Order open(Order order, List<OrderProdPack> orderProdPacks) {

    	Order result = null;
        
    	if (order.getId() == null) {
    		result = (Order) jdbcDao.insert(order) ;
    	} else {
    		jdbcDao.update(order) ;
    		result = order;
    		
    		OrderProdPackSO orderProdPackSO = new OrderProdPackSO();
    		orderProdPackSO.setOrder_id(order.getId());
    		jdbcDao.delete(OrderProdPack.class, orderProdPackSO);
    	}
    	
    	for (OrderProdPack opp : orderProdPacks) {
    		if (opp == null) {
    			continue;
    		}
    		opp.setId(null);
    		opp.setOrder_id(order.getId());
    		jdbcDao.insert(opp);
    	}
    	
        return result;
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
    	Order orderr = new Order() ;
    	orderr.setId(id) ;
        orderr = (Order) jdbcDao.get(orderr) ;
        
        
        return orderr;
    }
    
    public CustomerBO getCustomerBO() {
    	return (CustomerBO)CodeHelper.getAppContext().getBean("customerBO");
    }
}
