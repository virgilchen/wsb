package com.wsb.biz.service;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.OrderProdPackEvent;
import com.wsb.biz.entity.OrderProdPackEventSO;



@Service("orderProdPackEventBO")
@Scope("prototype")
@Transactional
public class OrderProdPackEventBO extends BaseServiceImpl {

	public void saveOpenOrderEvents(Set<Long> prod_pack_ids, Long orderId) {
		OrderProdPackEventSO so = new OrderProdPackEventSO();
		so.setProd_pack_ids(prod_pack_ids);
		so.setOrder_id(orderId);
		
		jdbcDao.executeName("bizSQLs:saveOpenOrderEvents", so);
		
	}
	
    public OrderProdPackEvent create(OrderProdPackEvent orderProdPackEvent) {  

    	OrderProdPackEvent newItem = (OrderProdPackEvent) jdbcDao.insert(orderProdPackEvent) ;
        
        return newItem;
    }
    
    public void update(OrderProdPackEvent orderProdPackEvent) {
    	
        jdbcDao.update(orderProdPackEvent) ;
    }
    

    public void delete(OrderProdPackEvent orderProdPackEvent) {
    	
        jdbcDao.delete(orderProdPackEvent) ;
        
    }

    public void deleteAll(Long[] orderProdPackEventIds) {
    	
        OrderProdPackEventSO criterion = new OrderProdPackEventSO() ;
        criterion.setIds(orderProdPackEventIds) ;
        jdbcDao.delete(OrderProdPackEvent.class, criterion) ;
        
    }
    
    
    public ArrayPageList<OrderProdPackEvent> query(OrderProdPackEventSO orderProdPackEventSO) {

        if (orderProdPackEventSO == null) {
        	orderProdPackEventSO = new OrderProdPackEventSO() ;
        }
        orderProdPackEventSO.addDesc("orderProdPackEvent_timestamp") ;
        
        return (ArrayPageList<OrderProdPackEvent>)jdbcDao.query(orderProdPackEventSO, OrderProdPackEvent.class);
    }
    
    
    public ArrayPageList<OrderProdPackEvent> queryByOrderId(Long order_id) {

    	OrderProdPackEventSO orderProdPackEventSO = new OrderProdPackEventSO() ;

        orderProdPackEventSO.setOrder_id(order_id);
        
        return (ArrayPageList<OrderProdPackEvent>)jdbcDao.query(orderProdPackEventSO, OrderProdPackEvent.class);
    }



    public OrderProdPackEvent get(Long id) {  
    	OrderProdPackEvent org = new OrderProdPackEvent() ;
    	org.setId(id) ;
        org = (OrderProdPackEvent) jdbcDao.get(org) ;
        
        
        return org;
    }
    
}
