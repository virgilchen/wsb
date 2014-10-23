package com.wsb.biz.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.service.SequenceBO;
import com.wsb.biz.entity.Order;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.entity.OrderProdPackEvent;
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
    	
    	String orderPrefix = U.yyyyMMdd(U.currentDate());
    	result.setOrder_no(
    			orderPrefix + 
    			StringUtils.leftPad(String.valueOf(SequenceBO.getSequenceBO().nextValue("Ord$" + orderPrefix.substring(0,6))), 4, '0'));
    	
    	result.setOrder_init_time_stamp(U.currentTimestamp());
    	result.setPsdo_cust_id(result.getCustomer().getId());
    	
    	return result;
    }
    
    public Order save(Order order, List<OrderProdPack> orderProdPacks) {

    	Order result = null;
        
    	if (order.getId() == null) {
    		if (order.getOrder_cur_status() == null) {
    		    order.setOrder_cur_status(Order.STATUS_INIT);
    		}
    		result = (Order) jdbcDao.insert(order) ;
    	} else {
    		jdbcDao.update(order) ;
    		result = order;
    		
    		OrderProdPackSO orderProdPackSO = new OrderProdPackSO();
    		orderProdPackSO.setOrder_id(order.getId());
    		jdbcDao.delete(OrderProdPack.class, orderProdPackSO);
    	}

    	OrderProdPackEventBO eventBO = this.getOrderProdPackEventBO();
    	
    	for (OrderProdPack opp : orderProdPacks) {
    		if (opp == null) {
    			continue;
    		}
    		opp.setId(null);
    		opp.setOrder_id(order.getId());
    		jdbcDao.insert(opp);

    		eventBO.saveOpenOrderEvents(order.getId(), opp);
    	}
    	
        return result;
    }
    
    public Order open(Order order, List<OrderProdPack> orderProdPacks) {
    	
    	order.setOrder_cur_status(Order.STATUS_START);
    	
    	Order newItem = this.save(order, orderProdPacks);
    	
    	Set<Long> productPackageIds = new HashSet<Long>();

    	OrderProdPackEventBO eventBO = this.getOrderProdPackEventBO();
    	
    	for (OrderProdPack opp : orderProdPacks) {
    		productPackageIds.add(opp.getProd_pack_id());
    		eventBO.saveOpenOrderEvents(order.getId(), opp);
    	}
    	
    	//this.getOrderProdPackEventBO().saveOpenOrderEvents(productPackageIds, order.getId());
    	return newItem;
    }
    
    public Order getOrderInfo(Long orderId) {
    	
    	Order result = this.get(orderId);
    	result.setCustomer(getCustomerBO().get(result.getPsdo_cust_id()));
    	
    	OrderProdPackBO orderProdPackBO = getOrderProdPackBO();
    	
    	result.setOrderProdPacks(orderProdPackBO.queryByOrderId(orderId));
    	result.setProducts(orderProdPackBO.queryOrderProdByOrderId(orderId));
    	
    	result.setOrderProdPackEvents(getOrderProdPackEventBO().queryByOrderId(orderId));
    	
    	
    	return result;
    }

    public OrderProdPackEvent followUp(OrderProdPackEvent event) {
    	return this.getOrderProdPackEventBO().followUp(event);
    }

    public void pickUp(OrderProdPackEvent event) {
    	this.getOrderProdPackEventBO().pickUp(event);
    }

    
    public ArrayPageList<HashMap> getMyTasks() {
    	OrderSO orderSO = new OrderSO() ;
        
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:myTasks", orderSO, HashMap.class);
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
    
    
    public void addOrderProdPackEvent(OrderProdPackEvent orderProdPackEvent) {
    	
        
        
    }
    
    public ArrayPageList<Order> query(OrderSO orderSO) {

        if (orderSO == null) {
        	orderSO = new OrderSO() ;
        }
        orderSO.addDesc("order_timestamp") ;
        
        return (ArrayPageList<Order>)jdbcDao.query(orderSO, Order.class);
    }



    public Order get(Long id) {  
    	Order order = new Order() ;
    	order.setId(id) ;
        order = (Order) jdbcDao.get(order) ;
        
        
        return order;
    }
    
    public CustomerBO getCustomerBO() {
    	return (CustomerBO)CodeHelper.getAppContext().getBean("customerBO");
    }
    
    public OrderProdPackEventBO getOrderProdPackEventBO() {
    	return (OrderProdPackEventBO)CodeHelper.getAppContext().getBean("orderProdPackEventBO");
    }
    
    public OrderProdPackBO getOrderProdPackBO() {
    	return (OrderProdPackBO)CodeHelper.getAppContext().getBean("orderProdPackBO");
    }
    
    
    
}
