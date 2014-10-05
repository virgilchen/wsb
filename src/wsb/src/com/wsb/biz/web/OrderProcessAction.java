package com.wsb.biz.web;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.OrderProcess;
import com.wsb.biz.entity.OrderProcessSO;
import com.wsb.biz.service.OrderProcessBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_orderProcessAction")
@Scope("prototype")
public class OrderProcessAction extends BaseAction implements Preparable {

    private static final long serialVersionUID = 7244882365197775441L;
    
    private OrderProcessBO orderProcessBO ;
    private OrderProcess orderProcess ;
    private Long business_id;
    private OrderProcessSO orderProcessSO ; 
    
    private List<OrderProcess> orderProcesses;
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<OrderProcess> pageList = orderProcessBO.query(orderProcessSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }
/*
    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	OrderProcess org = orderProcessBO.get(this.id) ;

    	renderObject(org, null) ; 
        return null ;  
    }*/

    @Pid(value=Pid.DO_NOT_CHECK)
    public String save()  throws Exception {        

        orderProcessBO.save(orderProcesses, business_id) ;

        renderObject(null, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }
/*
    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        orderProcessBO.update(orderProcess) ;

        renderObject(orderProcess, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }
*/
    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            orderProcessBO.delete(orderProcess) ;
        }/* else {
            orderProcessBO.deleteAll(ids) ;
        }*/

        
        renderObject(orderProcess, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


    public void setOrderProcessBO(OrderProcessBO orderProcessBO) {
		this.orderProcessBO = orderProcessBO;
	}

    public OrderProcess getOrderProcess() {
        return orderProcess;
    }

    public void setOrderProcess(OrderProcess orderProcess) {
        this.orderProcess = orderProcess;
    }

    public OrderProcessSO getOrderProcessSO() {
        return orderProcessSO;
    }

    public void setOrderProcessSO(OrderProcessSO orderProcessSO) {
        this.orderProcessSO = orderProcessSO;
    }


	public List<OrderProcess> getOrderProcesses() {
		return orderProcesses;
	}


	public void setOrderProcesses(List<OrderProcess> orderProcesses) {
		this.orderProcesses = orderProcesses;
	}


	public Long getBusiness_id() {
		return business_id;
	}


	public void setBusiness_id(Long business_id) {
		this.business_id = business_id;
	}

}
