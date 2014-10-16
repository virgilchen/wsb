package com.wsb.biz.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globalwave.base.BaseAction;
import com.globalwave.base.web.ResponseMessage;
import com.globalwave.common.ArrayPageList;
import com.globalwave.system.web.annotations.Pid;
import com.wsb.biz.entity.Car;
import com.wsb.biz.entity.CarSO;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.CustomerSO;
import com.wsb.biz.service.CarBO;
import com.wsb.biz.service.CustomerBO;
import com.opensymphony.xwork2.Preparable;

@Service("biz_customerAction")
@Scope("prototype")
public class CustomerAction extends BaseAction implements Preparable {
	
private static final long serialVersionUID = 7244882365197775441L;
    
    private CustomerBO customerBO ;
    private Customer customer ;
    private CustomerSO customerSO ; 
    
    private CarBO carBO ;
    private Car car ;
    private CarSO carSO ; 
    
    public String execute() throws Exception { 
        return this.list(); 
    }
    

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String list() throws Exception {  

        ArrayPageList<Customer> pageList = customerBO.query(customerSO) ;

        renderList(pageList) ; 
        
        return null ;  
        
    }

    @Pid(value=Pid.DO_NOT_CHECK,log=false)
    public String get() throws Exception {  

    	Customer customer = customerBO.get(this.id) ;

    	renderObject(customer, null) ; 
        return null ;  
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String create()  throws Exception {        

        Object newCustomer = customerBO.create(customer) ;

        renderObject(newCustomer, ResponseMessage.KEY_CREATE_OK) ;
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String update()  throws Exception {     

            	
        customerBO.update(customer) ;

        renderObject(customer, ResponseMessage.KEY_UPDATE_OK) ;
        
        return null;    
        
    }

    @Pid(value=Pid.DO_NOT_CHECK)
    public String delete()  throws Exception {

        if (this.ids == null) { 
            customerBO.delete(customer) ;
        } else {
            customerBO.deleteAll(ids) ;
        }

        
        renderObject(customer, ResponseMessage.KEY_DELETE_OK) ;
        
        return null;    
        
    }


    public void setCustomerBO(CustomerBO customerBO) {
		this.customerBO = customerBO;
	}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerSO getCustomerSO() {
        return customerSO;
    }

    public void setCustomerSO(CustomerSO customerSO) {
        this.customerSO = customerSO;
    }

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CarSO getCarSO() {
		return carSO;
	}

	public void setCarSO(CarSO carSO) {
		this.carSO = carSO;
	}

	public void setCarBO(CarBO carBO) {
		this.carBO = carBO;
	}

}
