package com.wsb.biz.web;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.globalwave.util.GsonUtil;
import com.wsb.biz.entity.Order;
import com.wsb.biz.entity.OrderProdPack;
import com.wsb.biz.entity.OrderProdPackEvent;
import com.wsb.biz.entity.OrderSO;
import com.wsb.biz.service.OrderBO;
import com.wsb.biz.service.StaffBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_orderAction")
@Scope("prototype")
public class OrderAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private OrderBO orderBO ;
    private Order order ;
    private List<OrderProdPack> orderProdPacks ;
    private OrderSO orderSO ; 
    private Long customer_id;
    private OrderProdPackEvent orderProdPackEvent;
   
    
    public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public String execute() throws Exception { 
        return this.list(); 
    }
    
    public String openView() throws Exception {

    	this.getRequest().setAttribute(
    			"staffsJson", 
    			GsonUtil.getGson().toJson(StaffBO.getStaffBO().query(null)));
    	
    	return "jsp";
    }
    
    public String followUpView() throws Exception {
    	this.getRequest().setAttribute(
    			"staffsJson", 
    			GsonUtil.getGson().toJson(StaffBO.getStaffBO().query(null)));
    	
    	return "jsp";
    }
    
    public String myTasksView() throws Exception {
    	return "jsp";
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String getOpenInfo() throws Exception {

        Object newOrder = orderBO.getOpenInfo(customer_id) ;

        renderObject(newOrder, null) ;
        return null;  
    }
    
    public String save() throws Exception {


        Object newOrder = orderBO.save(order, orderProdPacks) ;

        renderObject(newOrder, ResponseMessage.KEY_UPDATE_OK) ;
        return null;  
    }
    
    public String open() throws Exception {


        Object newOrder = orderBO.open(order, orderProdPacks) ;

        renderObject(newOrder, ResponseMessage.KEY_UPDATE_OK) ;
        return null;  
    }
    

    public String getOrderInfo() throws Exception {

        Object newOrder = orderBO.getOrderInfo(order.getId()) ;

        renderObject(newOrder, null) ;
        return null;  
    }


    public String followUp() throws Exception {


    	OrderProdPackEvent newOrder = orderBO.followUp(orderProdPackEvent) ;

        renderObject(newOrder, ResponseMessage.KEY_UPDATE_OK) ;
        return null;  
    }
    
    public String pickUp() throws Exception {


    	orderBO.pickUp(orderProdPackEvent) ;

        renderObject(null, ResponseMessage.KEY_UPDATE_OK) ;
        return null;  
    }

    public String getMyTasks() throws Exception {

        renderList(orderBO.getMyTasks()) ;
        
        return null;  
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Order> pageList = orderBO.query(orderSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Order org = orderBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newOrder = orderBO.create(order) ;

        renderObject(newOrder, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        orderBO.update(order) ;

        renderObject(order, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            orderBO.delete(order) ;
        } else {
            orderBO.deleteAll(ids) ;
        }

        
        renderObject(order, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String addOrderProdPackEvent() throws Exception {
    	
        orderBO.addOrderProdPackEvent(orderProdPackEvent) ;
        
        renderObject(order, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
    }


    public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderSO getOrderSO() {
        return orderSO;
    }

    public void setOrderSO(OrderSO orderSO) {
        this.orderSO = orderSO;
    }

	public List<OrderProdPack> getOrderProdPacks() {
		return orderProdPacks;
	}

	public void setOrderProdPacks(List<OrderProdPack> orderProdPacks) {
		this.orderProdPacks = orderProdPacks;
	}

	public OrderProdPackEvent getOrderProdPackEvent() {
		return orderProdPackEvent;
	}

	public void setOrderProdPackEvent(OrderProdPackEvent orderProdPackEvent) {
		this.orderProdPackEvent = orderProdPackEvent;
	}

}
