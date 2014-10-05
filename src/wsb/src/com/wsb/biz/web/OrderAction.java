package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.Order;
import com.wsb.biz.entity.OrderSO;
import com.wsb.biz.service.OrderBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_orderAction")
@Scope("prototype")
public class OrderAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private OrderBO orderBO ;
    private Order order ;
    private OrderSO orderSO ; 
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    
    public String orderOpenView() throws Exception {
    	return "jsp";
    }
    
    public String orderFollowUpView() throws Exception {
    	return "jsp";
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

}
