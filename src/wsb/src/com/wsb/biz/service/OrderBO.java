package com.wsb.biz.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.U;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.system.entity.SessionUser;
import com.globalwave.system.service.SequenceBO;
import com.wsb.biz.entity.Order;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.entity.OrderProdPackEvent;
import com.wsb.biz.entity.OrderProdPackSO;
import com.wsb.biz.entity.OrderSO;
import com.wsb.biz.entity.ProductSO;
import com.wsb.biz.entity.Staff;
import com.wsb.biz.entity.WfKeyInfo;
import com.wsb.biz.entity.WfKeyInfoDetails;
import com.wsb.biz.entity.WfKeyInfoDetailsSO;
import com.wsb.biz.entity.WfKeyInfoRel;
import com.wsb.biz.entity.WfKeyInfoRelSO;
import com.wsb.biz.entity.WfKeyInfoResult;
import com.wsb.biz.entity.WfKeyInfoResultSO;


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
    		
    		result.setOrderProdPackEvents(
    				OrderProdPackEventBO.getOrderProdPackEventBO().queryWithProductByOrderId(order.getId(), customerId)) ;
    	}
    	
    	return result;
    }

    public Order save(Order order, List<OrderProdPack> orderProdPacks) {
    	order.setOrder_cur_status(Order.STATUS_INIT);
    	return this.save(order, orderProdPacks, OrderProdPackEvent.STATUS_INIT);
    }
    
    public Order save(Order order, List<OrderProdPack> orderProdPacks, String eventStaus) {

    	Order result = null;
    	order.setOrder_init_staff_id(SessionUser.get().getStaff().getId());
        
    	if (order.getId() == null) {
    		result = (Order) jdbcDao.insert(order) ;
    	} else {
    		jdbcDao.update(order) ;
    		result = order;
    		
    		OrderProdPackSO orderProdPackSO = new OrderProdPackSO();
    		orderProdPackSO.setOrder_id(order.getId());
    		jdbcDao.delete(OrderProdPack.class, orderProdPackSO);
    	}


    	if (Order.ORDER_TYPE_BUSINESS.equals(order.getOrder_type())) {
	    	OrderProdPackEventBO eventBO = OrderProdPackEventBO.getOrderProdPackEventBO();
	    	AssetsHoldingBO assetsHoldingBO = AssetsHoldingBO.getAssetsHoldingBO();
	    	
	    	for (OrderProdPack opp : orderProdPacks) {
	    		if (opp == null) {
	    			continue;
	    		}
	    		opp.setId(null);
	    		opp.setOrder_id(order.getId());
        		if (opp.getOrder_prod_pack_purchase_date() == null) {
        			opp.setOrder_prod_pack_purchase_date(new Date(U.currentDate().getTime()));
        		}
	    		
	    		setPackProductNames(opp);
	    		
	    		jdbcDao.insert(opp);
	
	    		eventBO.saveOpenOrderEvents(order.getId(), opp, eventStaus);
	    		
	    		if (OrderProdPackEvent.STATUS_READY.equals(eventStaus)) {
	    		    assetsHoldingBO.pendingProductAmount(order.getId(), order.getPsdo_cust_id(), opp);
	    		}
	    	}
    	} else if (Order.ORDER_TYPE_PURCHASE.equals(order.getOrder_type())) {

    		AssetsHoldingBO assetsHoldingBO = AssetsHoldingBO.getAssetsHoldingBO(); 
        	for (OrderProdPack opp : orderProdPacks) {
        		if (opp == null) {
        			continue;
        		}
        		opp.setId(null);
        		opp.setOrder_id(order.getId());
        		if (opp.getOrder_prod_pack_purchase_date() == null) {
        			opp.setOrder_prod_pack_purchase_date(new Date(U.currentDate().getTime()));
        		}
        		jdbcDao.insert(opp);
        		
        		assetsHoldingBO.add(order, opp);
        	}
        	
    		
    	}
    	
        return result;
    }
    
    private void setPackProductNames(OrderProdPack opp) {
    	if (opp.getProduct_ids() == null) {
    		return ;
    	}
    	
    	List<Long> ids = new ArrayList<Long>();
    	for (int i = 0 ; i < opp.getProduct_ids().length ; i ++) {
    		Double amount = opp.getAmounts()[i];
    		
    		if (amount != null && amount != 0D) {
    			ids.add(opp.getProduct_ids()[i]) ;
    		}
    	}
    	
    	ProductSO so = new ProductSO() ;
    	so.setProd_pack_id(opp.getProd_pack_id());
    	so.setProd_ids(ids.toArray(new Long[]{}));
    	
    	ArrayPageList<OrderProdPack> names = 
    			(ArrayPageList<OrderProdPack>)this.jdbcDao.queryName("bizSQLs:queryPackProductNames", so, OrderProdPack.class);
    	
    	if (names.size() > 0) {
    		opp.setProd_names(names.get(0).getProd_names());
    	}
    }
    
    public Order open(Order order, List<OrderProdPack> orderProdPacks) {
    	/*
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
    	*/

    	order.setOrder_cur_status(Order.STATUS_START);
    	return this.save(order, orderProdPacks, OrderProdPackEvent.STATUS_READY);
    }
    
    public Order getOrderInfo(Long orderId) {
    	
    	Order result = this.get(orderId);
    	result.setOrder_init_staff_name(StaffBO.getStaffBO().get(result.getOrder_init_staff_id()).getStaff_name());
    	result.setCustomer(CustomerBO.getCustomerBO().get(result.getPsdo_cust_id()));
    	
    	OrderProdPackBO orderProdPackBO = OrderProdPackBO.getOrderProdPackBO();
    	
    	result.setOrderProdPacks(orderProdPackBO.queryByOrderId(orderId));
    	result.setProducts(orderProdPackBO.queryOrderProdByOrderId(orderId));
    	
    	result.setOrderProdPackEvents(OrderProdPackEventBO.getOrderProdPackEventBO().queryByOrderId(orderId));
    	result.setDocuments(DocumentBO.getDocumentBO().query(orderId, "O"));
    	
    	ArrayPageList<HashMap> eventList = new ArrayPageList<HashMap>();
    	eventList = OrderProdPackEventBO.getOrderProdPackEventBO().queryByOrderId(orderId);
    	if(eventList.size()>0){
    		ArrayList<WfKeyInfoRel> relList = new ArrayList<WfKeyInfoRel>();
    		ArrayPageList<WfKeyInfo> wfKeyInfos = new ArrayPageList<WfKeyInfo>();
    		for(int i=0; i<eventList.size(); i++){
    			if(eventList.get(i).get("event_status").equals("R")){
    				WfKeyInfoRelSO wfKeyInfoRelSO = new WfKeyInfoRelSO();
    				wfKeyInfoRelSO.setBusiness_id(Long.parseLong(eventList.get(i).get("business_id").toString()));
    				wfKeyInfoRelSO.setProcs_step_no(Integer.parseInt(eventList.get(i).get("procs_step_no").toString()));
    				relList = WfKeyInfoRelBO.getWfKeyInfoRelBO().query(wfKeyInfoRelSO);
    				break;
    			}
    		}
    		if(relList.size()>0){
    			for(WfKeyInfoRel wfKeyInfoRelVo : relList){
    				WfKeyInfo wfKeyInfoVo = WfKeyInfoBO.getWfKeyInfoBO().get(wfKeyInfoRelVo.getWf_key_info_id());
    				WfKeyInfoDetailsSO wfKeyInfoDetailsSO = new WfKeyInfoDetailsSO();
    				wfKeyInfoDetailsSO.setWf_key_info_id(wfKeyInfoRelVo.getWf_key_info_id());
    				ArrayPageList<WfKeyInfoDetails> wfKeyInfoDetailsList = WfKeyInfoDetailsBO.getWfKeyInfoDetailsBO().query(wfKeyInfoDetailsSO);
    				wfKeyInfoVo.setWfKeyInfoDetailsList(wfKeyInfoDetailsList);
    				wfKeyInfos.add(wfKeyInfoVo);
    			}
    		}
    		result.setWfKeyInfos(wfKeyInfos);
    	}
    	WfKeyInfoResultSO wfKeyInfoResultSO = new WfKeyInfoResultSO();
    	wfKeyInfoResultSO.setOrder_id(orderId);
    	ArrayPageList<WfKeyInfoResult> wfKeyInfoResults =  WfKeyInfoResultBO.getWfKeyInfoResultBO().query(wfKeyInfoResultSO);
    	result.setWfKeyInfoResults(wfKeyInfoResults);
    	return result;
    }

    public OrderProdPackEvent followUp(OrderProdPackEvent event,List<WfKeyInfoResult> wfKeyInfoResults) {
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
        	    
        	    AssetsHoldingBO.getAssetsHoldingBO().confirmProductAmount(orderId);
    	    }
    	}
    	
    	if(wfKeyInfoResults != null && wfKeyInfoResults.size()>0){
    		for(WfKeyInfoResult wfKeyInfoResult : wfKeyInfoResults){
    			if(wfKeyInfoResult.getIsChk() != null && wfKeyInfoResult.getIsChk().equals("1")){
    				wfKeyInfoResult.setOrder_id(event.getOrder_id());
    				WfKeyInfoResultBO.getWfKeyInfoResultBO().create(wfKeyInfoResult);
    			}
    		}
    	}
    	
    	return result ;
    }

    public void pickUp(OrderProdPackEvent event) {
    	event.setEvent_staff_id(SessionUser.get().getStaff().getId());
    	OrderProdPackEventBO.getOrderProdPackEventBO().pickUp(event);
    }

    
    public ArrayPageList<HashMap> getMyTasks(OrderSO orderSO) {
    	Staff staff = SessionUser.get().getStaff();
    	
    	orderSO.setEvent_staff_id(staff.getId());
    	orderSO.setProcs_staff_role_id(staff.getStaff_role_id());
        
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
        
        //if (!sessionUser.isManager()) {
        Long roleId = sessionUser.getStaff().getStaff_role_id();
        if (CodeHelper.getString("Order.Permission.H", "name_", String.valueOf(roleId)) == null){
            orderSO.setOrder_init_staff_id(sessionUser.getStaff().getId());
        } else {
        	orderSO.setOrder_by(" o.order_id desc ");
        }
        
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:queryOrders", orderSO, HashMap.class);
    }

    public ArrayPageList<HashMap> query(OrderSO orderSO) {

        if (orderSO == null) {
        	orderSO = new OrderSO() ;
        }
        
        SessionUser sessionUser = SessionUser.get();
        
        //if (!sessionUser.isManager()) {
        Long roleId = sessionUser.getStaff().getStaff_role_id();
        if (CodeHelper.getString("Order.Permission.H", "desc_", String.valueOf(roleId)) != null){
            orderSO.setOrder_init_staff_id(sessionUser.getStaff().getId());
        } else {
        	orderSO.setOrder_by(" o.order_id desc ");
        }
        
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:advanceSearchOrders", orderSO, HashMap.class);
    }

    public ArrayPageList<HashMap> remindSearchOrders(OrderSO orderSO) {

        if (orderSO == null) {
        	orderSO = new OrderSO() ;
        }
        
        SessionUser sessionUser = SessionUser.get();
        
        //if (!sessionUser.isManager()) {
        Long roleId = sessionUser.getStaff().getStaff_role_id();
        if (CodeHelper.getString("Order.Permission.H", "desc_", String.valueOf(roleId)) != null){
            orderSO.setOrder_init_staff_id(sessionUser.getStaff().getId());
        } else {
        	orderSO.setOrder_by(" o.order_id desc ");
        }
        
        return (ArrayPageList<HashMap>)jdbcDao.queryName("bizSQLs:remindSearchOrders", orderSO, HashMap.class);
    }
    

    public ArrayPageList<Order> queryOrderHistories(OrderSO orderSO) {
    	ArrayPageList<Order> result = (ArrayPageList<Order>)jdbcDao.queryName("bizSQLs:queryOrderHistories", orderSO, Order.class);
    	
    	OrderProdPackBO orderProdPackBO = OrderProdPackBO.getOrderProdPackBO();
    	
    	for (Order order : result) {
    		order.setOrderProdPacks(orderProdPackBO.queryByOrderId(order.getId()));
    	}
    	
        return result;
    }

    public void updateUrgentLevel(Order order) {
    	order.addInclusions("urgent_levent");
    	//order.setUrgent_levent(level);
    	jdbcDao.update(order);
    }

    public Order get(Long id) {  
    	Order order = new Order() ;
    	order.setId(id) ;
        order = (Order) jdbcDao.get(order) ;
        
        
        return order;
    }
    
    public static OrderBO getOrderBO() {
    	return (OrderBO) CodeHelper.getAppContext().getBean("orderBO");
    }
    
    
}
