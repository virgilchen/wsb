package com.wsb.biz.service;

import java.util.HashMap;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.globalwave.system.entity.SessionUser;
import com.wsb.biz.entity.OrderProcess;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.entity.OrderProdPackEvent;
import com.wsb.biz.entity.OrderProdPackEventSO;
import com.wsb.biz.entity.Staff;



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

	public void saveOpenOrderEvents(Long orderId, OrderProdPack orderProdPack, String status) {
		OrderProdPackEventSO so = new OrderProdPackEventSO();
		so.setOrder_id(orderId);
		so.setProd_pack_id(orderProdPack.getProd_pack_id());
		
		jdbcDao.delete(OrderProdPackEvent.class, so);
		
		for (int i = 0 ; i < orderProdPack.getBusiness_ids().length ; i ++) {
			OrderProdPackEvent event  = new OrderProdPackEvent() ;
			
			Long business_id = orderProdPack.getBusiness_ids()[i];
			Long staff_id = orderProdPack.getEvent_staff_ids()[i];
			
			event.setOrder_id(orderId);
			event.setProcs_step_no(0);
			event.setProd_pack_id(orderProdPack.getProd_pack_id());
			event.setBusiness_id(business_id);
			event.setEvent_staff_id(staff_id);
			event.setEvent_status(status);
			
			OrderProcess processSo  = new OrderProcess() ;
			processSo.setBusiness_id(business_id);
			processSo.setProcs_step_no(0);
	    	OrderProcess process = (OrderProcess)jdbcDao.find(processSo);
	    	checkProcessRole(process, staff_id);
	    	
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

    
    public ArrayPageList<HashMap> queryByOrderId(Long order_id) {

    	OrderProdPackEventSO orderProdPackEventSO = new OrderProdPackEventSO() ;

        orderProdPackEventSO.setOrder_id(order_id);
        orderProdPackEventSO.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
        
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:queryOrderEvents", orderProdPackEventSO, HashMap.class);
    }
    
    public ArrayPageList<HashMap> queryWithProductByOrderId(Long order_id, Long customer_id) {

    	OrderProdPackEventSO orderProdPackEventSO = new OrderProdPackEventSO() ;

        orderProdPackEventSO.setOrder_id(order_id);
        orderProdPackEventSO.setCustomer_id(customer_id);
        orderProdPackEventSO.setPageIndex(ArrayPageList.PAGEINDEX_NO_PAGE);
        
        ArrayPageList<HashMap> result = 
        		(ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:queryOrderEventsWithProduct", orderProdPackEventSO, HashMap.class);
        

    	HashMap preElem = null ;
    	int rowSpan = 1 ;
    	for (HashMap elem : result) {
    		if (preElem == null) {
    			elem.put("rowSpan", rowSpan);
    			preElem = elem ;
    			continue ;
    		}
    		
    		if (preElem.get("id").equals(elem.get("id"))) {
    			rowSpan ++ ;
    			preElem.put("rowSpan", rowSpan);
    			elem.remove("id");
    			continue ;
    		}
    		
    		rowSpan = 1 ;
			elem.put("rowSpan", rowSpan);
			preElem = elem ;
    	}
    	
    	return result;
    }

    

    public OrderProdPackEvent get(Long id) {  
    	OrderProdPackEvent org = new OrderProdPackEvent() ;
    	org.setId(id) ;
        org = (OrderProdPackEvent) jdbcDao.get(org) ;
        
        
        return org;
    }
    
    /**
     * 
     * @param event
     * @return next event, if null(current step successfully), then no next step
     */
    public OrderProdPackEvent followUp(OrderProdPackEvent event) {
    	
    	Long staffId = SessionUser.get().getStaff().getId();
    	
    	OrderProdPackEvent oldEvent = this.get(event.getId());
    	
    	if (!staffId.equals(oldEvent.getEvent_staff_id())) {
    		throw new BusinessException(11003L);//11003 当前环节由其业务员处理
    	}
    	
    	event.setOrder_id(oldEvent.getOrder_id());
    	
    	String event_status = event.getEvent_status();
    	
		oldEvent.setEvent_status(event_status);
    	oldEvent.addInclusions("event_status");
    	oldEvent.setEvent_remark(event.getEvent_remark());
    	oldEvent.addInclusions("event_remark");
    	oldEvent.setEvent_duration(U.currentTimestamp());
    	oldEvent.addInclusions("event_duration");
    	
    	jdbcDao.update(oldEvent);
    	
    	
    	
    	OrderProcess so = new OrderProcess();
    	so.setBusiness_id(oldEvent.getBusiness_id());
    	
    	if (OrderProdPackEvent.STATUS_CONTINUE.equals(event_status)) {
    	    so.setProcs_step_no(oldEvent.getProcs_step_no());
    	} else if (OrderProdPackEvent.STATUS_BACK.equals(event_status)) {
    		so.setProcs_step_no(oldEvent.getProcs_step_no() - 1);
    	} else if (OrderProdPackEvent.STATUS_SUCCESSFULLY.equals(event_status)) {
    		so.setProcs_step_no(oldEvent.getProcs_step_no() + 1);
    	} else {// if (OrderProdPackEvent.STATUS_FAIL.equals(event_status)){
    		so.setProcs_step_no(oldEvent.getProcs_step_no());
    	}
    	
    	OrderProcess process = (OrderProcess)jdbcDao.find(so);
    	if (process == null) {
    		if (OrderProdPackEvent.STATUS_SUCCESSFULLY.equals(event_status)) {
    			return null;// successfully exists
    		}
    		
    		if (OrderProdPackEvent.STATUS_BACK.equals(event_status)) {
    			throw new BusinessException(11005L);//11001 本环节为业务首环节，未能可回退一环节
    		} else {    			
    			throw new BusinessException(11001L);//11001 本环节为业务最终环节，未能找到下一环节
    		}
    	}
    	
    	Long newEventStaffId = event.getEvent_staff_id() ;
    	
    	if (!OrderProdPackEvent.STATUS_FAIL.equals(event_status)){
    	    checkProcessRole(process, newEventStaffId);
    	}
    	
    	event.setProcs_step_no(process.getProcs_step_no());
    	
    	event.setId(null);
    	event.setEvent_remark("");
    	
    	event.setProd_pack_id(oldEvent.getProd_pack_id());
    	event.setBusiness_id(oldEvent.getBusiness_id());
    	event.setEvent_status(OrderProdPackEvent.STATUS_READY);
    	jdbcDao.insert(event);
    	
    	return event;
    }
    
    
    

    public OrderProcess getStaffRoleId4Event(OrderProdPackEvent event) {
    	
    	OrderProdPackEvent oldEvent = this.get(event.getId());
    	
    	String event_status = event.getEvent_status();
    	
    	
    	OrderProcess so = new OrderProcess();
    	so.setBusiness_id(event.getBusiness_id());
    	
    	if (OrderProdPackEvent.STATUS_CONTINUE.equals(event_status)) {
    	    so.setProcs_step_no(oldEvent.getProcs_step_no());
    	} else if (OrderProdPackEvent.STATUS_BACK.equals(event_status)) {
    		so.setProcs_step_no(oldEvent.getProcs_step_no() - 1);
    	} else if (OrderProdPackEvent.STATUS_SUCCESSFULLY.equals(event_status)) {
    		so.setProcs_step_no(oldEvent.getProcs_step_no() + 1);
    	} else {// if (OrderProdPackEvent.STATUS_FAIL.equals(event_status)){
    		so.setProcs_step_no(oldEvent.getProcs_step_no());
    	}
    	
    	OrderProcess process = (OrderProcess)jdbcDao.find(so);
    	if (process == null) {
    		if (OrderProdPackEvent.STATUS_SUCCESSFULLY.equals(event_status)) {
    			return null;// successfully exists
    		}
    		
    		if (OrderProdPackEvent.STATUS_BACK.equals(event_status)) {
    			throw new BusinessException(11005L);//11001 本环节为业务首环节，未能可回退一环节
    		} else {    			
    			throw new BusinessException(11001L);//11001 本环节为业务最终环节，未能找到下一环节
    		}
    	}
    	
    	return process;
    }

	private void checkProcessRole(OrderProcess process, Long newEventStaffId) {
		if (newEventStaffId != null) {    	
    		Staff newStaff = StaffBO.getStaffBO().get(newEventStaffId);
    		Long roleId = newStaff.getStaff_role_id();
    		if (!process.getProcs_staff_role_id().equals(roleId)) {
    			String roleName = StaffRoleBO.getStaffRoleBO().get(process.getProcs_staff_role_id()).getStaff_role_name();
    			throw new BusinessException(11004L, newStaff.getStaff_name(), roleName);//11004 业务员[{0}]没有业务环节所需要的角色权限[{1}]!
    		}
    	}
	}

    public void pickUp(OrderProdPackEvent event) {
    	OrderProdPackEvent oldEvent = (OrderProdPackEvent)this.jdbcDao.get(event); 

    	OrderProcess so = new OrderProcess() ;
    	so.setBusiness_id(oldEvent.getBusiness_id());
    	so.setProcs_step_no(oldEvent.getProcs_step_no());
    	
    	OrderProcess process = (OrderProcess)jdbcDao.find(so);
    	
    	Staff staff = SessionUser.get().getStaff();
    	if (!process.getProcs_staff_role_id().equals(staff.getStaff_role_id())) {
    		String roleName = CodeHelper.getString("Role", "staff_role_name", Integer.valueOf(process.getProcs_staff_role_id().intValue()));
    		throw new BusinessException(11004L, staff.getStaff_name(), roleName);// '11004', '11004', '业务员[{0}]没有业务环节所需要的角色权限[{1}]！'
    	}
    	
        int count = jdbcDao.executeName("bizSQLs:pickUpTask", event) ;
        if (count == 0) {
        	throw new BusinessException(11002L);// 11002Ltask has been picked up by other people.
        }
    }
    

    public static OrderProdPackEventBO getOrderProdPackEventBO() {
    	return (OrderProdPackEventBO)CodeHelper.getAppContext().getBean("orderProdPackEventBO");
    }
}
