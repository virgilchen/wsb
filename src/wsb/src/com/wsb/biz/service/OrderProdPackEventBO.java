package com.wsb.biz.service;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.OrderProcess;
import com.wsb.biz.entity.OrderProdPack;
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

	public void saveOpenOrderEvents(Long orderId, OrderProdPack orderProdPack) {
		OrderProdPackEventSO so = new OrderProdPackEventSO();
		so.setOrder_id(orderId);
		so.setProd_pack_id(orderProdPack.getProd_pack_id());
		
		jdbcDao.delete(OrderProdPackEvent.class, so);
		
		for (int i = 0 ; i < orderProdPack.getBusiness_ids().length ; i ++) {
			OrderProdPackEvent event  = new OrderProdPackEvent() ;
			
			event.setOrder_id(orderId);
			event.setProcs_step_no(0);
			event.setProd_pack_id(orderProdPack.getProd_pack_id());
			event.setBusiness_id(orderProdPack.getBusiness_ids()[i]);
			event.setEvent_staff_id(orderProdPack.getEvent_staff_ids()[i]);
			event.setEvent_status(OrderProdPackEvent.STATUS_READY);
			
			jdbcDao.insert(event);
		}
		
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
    
    public OrderProdPackEvent followUp(OrderProdPackEvent event) {
    	
    	OrderProdPackEvent oldEvent = this.get(event.getId());
    	oldEvent.setEvent_status(event.getEvent_status());
    	oldEvent.addInclusions("event_status");
    	oldEvent.setEvent_remark(event.getEvent_remark());
    	oldEvent.addInclusions("event_remark");
    	
    	jdbcDao.update(oldEvent);
    	
    	OrderProcess so = new OrderProcess();
    	so.setBusiness_id(event.getBusiness_id());
    	
    	if (OrderProdPackEvent.STATUS_CONTINUE.equals(event.getEvent_status())) {
    	    so.setProcs_step_no(oldEvent.getProcs_step_no());
    	} else if (OrderProdPackEvent.STATUS_BACK.equals(event.getEvent_status())) {
    		so.setProcs_step_no(oldEvent.getProcs_step_no() - 1);
    	} else {
    		so.setProcs_step_no(oldEvent.getProcs_step_no() + 1);
    	}
    	
    	OrderProcess process = (OrderProcess)jdbcDao.find(so);
    	if (process == null) {
    		throw new BusinessException("");
    	}
    	event.setProcs_step_no(process.getProcs_step_no());
    	
    	event.setId(null);
    	event.setEvent_remark("");
    	
    	event.setOrder_id(oldEvent.getOrder_id());
    	event.setProd_pack_id(oldEvent.getProd_pack_id());
    	event.setBusiness_id(oldEvent.getBusiness_id());
    	event.setEvent_status(OrderProdPackEvent.STATUS_READY);
    	jdbcDao.insert(event);
    	
    	return event;
    }
}
