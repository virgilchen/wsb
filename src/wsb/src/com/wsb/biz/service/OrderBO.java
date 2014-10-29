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
import com.globalwave.system.entity.SessionUser;
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
    
    /**
     * 要看是否包含订单信息，当包含订单信息时，需要从数据中取出信息
     * 
     * @param order
     * @param customerId
     * @return
     */
    public Order getOpenInfo(Order order, Long customerId) {
    	
    	Order result = (order == null?new Order():this.get(order.getId()));
    	
    	if (customerId == null) {
    		customerId = result.getPsdo_cust_id();
    	}
    	
    	result.setCustomer(CustomerBO.getCustomerBO().get(customerId));
    	
    	if (order == null) {
	    	String orderPrefix = U.yyyyMMdd(U.currentDate());
	    	result.setOrder_no(
	    			orderPrefix + 
	    			StringUtils.leftPad(String.valueOf(SequenceBO.getSequenceBO().nextValue("Ord$" + orderPrefix.substring(0,6))), 4, '0'));
	    	
	    	result.setOrder_init_time_stamp(U.currentTimestamp());
	    	result.setPsdo_cust_id(result.getCustomer().getId());
    	} else {
    		result.setOrderProdPacks(OrderProdPackBO.getOrderProdPackBO().queryByOrderId(order.getId()));
    		result.setOrderProdPackEvents(OrderProdPackEventBO.getOrderProdPackEventBO().queryByOrderId(order.getId())) ;
    	}
    	
    	return result;
    }
    
    public Order save(Order order, List<OrderProdPack> orderProdPacks) {

    	Order result = null;
    	order.setOrder_init_staff_id(SessionUser.get().getStaff().getId());
        
    	if (order.getId() == null) {
    		order.setOrder_cur_status(Order.STATUS_INIT);

    		result = (Order) jdbcDao.insert(order) ;
    	} else {
    		jdbcDao.update(order) ;
    		result = order;
    		
    		OrderProdPackSO orderProdPackSO = new OrderProdPackSO();
    		orderProdPackSO.setOrder_id(order.getId());
    		jdbcDao.delete(OrderProdPack.class, orderProdPackSO);
    	}

    	OrderProdPackEventBO eventBO = OrderProdPackEventBO.getOrderProdPackEventBO();
    	
    	for (OrderProdPack opp : orderProdPacks) {
    		if (opp == null) {
    			continue;
    		}
    		opp.setId(null);
    		opp.setOrder_id(order.getId());
    		jdbcDao.insert(opp);

    		eventBO.saveOpenOrderEvents(order.getId(), opp, OrderProdPackEvent.STATUS_INIT);
    	}
    	
        return result;
    }
    
    public Order open(Order order, List<OrderProdPack> orderProdPacks) {
    	
    	order.setOrder_cur_status(Order.STATUS_START);
    	order.setOrder_init_staff_id(SessionUser.get().getStaff().getId());
    	
    	Order newItem = this.save(order, orderProdPacks);
    	
    	Set<Long> productPackageIds = new HashSet<Long>();

    	OrderProdPackEventBO eventBO = OrderProdPackEventBO.getOrderProdPackEventBO();
    	
    	for (OrderProdPack opp : orderProdPacks) {
    		productPackageIds.add(opp.getProd_pack_id());
    		eventBO.saveOpenOrderEvents(order.getId(), opp, OrderProdPackEvent.STATUS_READY);
    	}
    	
    	//this.getOrderProdPackEventBO().saveOpenOrderEvents(productPackageIds, order.getId());
    	return newItem;
    }
    
    public Order getOrderInfo(Long orderId) {
    	
    	Order result = this.get(orderId);
    	result.setCustomer(CustomerBO.getCustomerBO().get(result.getPsdo_cust_id()));
    	
    	OrderProdPackBO orderProdPackBO = OrderProdPackBO.getOrderProdPackBO();
    	
    	result.setOrderProdPacks(orderProdPackBO.queryByOrderId(orderId));
    	result.setProducts(orderProdPackBO.queryOrderProdByOrderId(orderId));
    	
    	result.setOrderProdPackEvents(OrderProdPackEventBO.getOrderProdPackEventBO().queryByOrderId(orderId));
    	
    	
    	return result;
    }

    public OrderProdPackEvent followUp(OrderProdPackEvent event) {
    	OrderProdPackEvent result = OrderProdPackEventBO.getOrderProdPackEventBO().followUp(event);
    	
    	if (result == null) {
    		Long orderId = event.getOrder_id();
    	    
    	    String sql = "select count(1) from order_prod_pack_event_rt where order_id=? and event_status='R'";
    	    if (jdbcDao.getLong(sql, new Object[]{orderId}) <= 0) {
        	    Order order = new Order();
        	    order.setId(orderId);
        	    order.setOrder_cur_status(Order.STATUS_END);
        	    order.addInclusions("order_cur_status");
        	    
        	    jdbcDao.update(order);
    	    }
    	}
    	
    	return result ;
    }

    public void pickUp(OrderProdPackEvent event) {
    	event.setEvent_staff_id(SessionUser.get().getStaff().getId());
    	OrderProdPackEventBO.getOrderProdPackEventBO().pickUp(event);
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
    /*
    
    public void addOrderProdPackEvent(OrderProdPackEvent orderProdPackEvent) {
    	
        
        
    }

    public ArrayPageList<Order> query(OrderSO orderSO) {

        if (orderSO == null) {
        	orderSO = new OrderSO() ;
        }
        orderSO.addDesc("order_timestamp") ;
        
        return (ArrayPageList<Order>)jdbcDao.query(orderSO, Order.class);
    }*/
    
    public ArrayPageList<HashMap> queryOrders(OrderSO orderSO) {

        if (orderSO == null) {
        	orderSO = new OrderSO() ;
        }
        
        SessionUser sessionUser = SessionUser.get();
        
        if (!sessionUser.isManager()) {
            orderSO.setOrder_init_staff_id(sessionUser.getStaff().getId());
        }
        
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:queryOrders", orderSO, HashMap.class);
    }



    public Order get(Long id) {  
    	Order order = new Order() ;
    	order.setId(id) ;
        order = (Order) jdbcDao.get(order) ;
        
        
        return order;
    }
    
    
    
    
}
