package com.wsb.biz.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globalwave.base.BaseServiceImpl;
import com.globalwave.common.ArrayPageList;
import com.globalwave.common.cache.CodeHelper;
import com.globalwave.common.exception.BusinessException;
import com.wsb.biz.entity.Car;
import com.wsb.biz.entity.CarSO;
import com.wsb.biz.entity.Customer;
import com.wsb.biz.entity.CustomerSO;
import com.wsb.biz.entity.MemberSO;
import com.wsb.biz.entity.Member;


@Service("customerBO")
@Scope("prototype")
@Transactional
public class CustomerBO extends BaseServiceImpl {

    public Customer create(Customer customer, List<Car> cars) {  
    	
    	Customer customerSO = new Customer();
    	customerSO.setCust_code((customer.getCust_code()));
		if (this.jdbcDao.find(customerSO) != null) {
			throw new BusinessException(1110L);//1110', '会员登录账号已经被使用，请使用其它登录账号
		}
		
    	Customer newItem = (Customer) jdbcDao.insert(customer) ;
    	if(cars != null){
    		for(int i=0;i<cars.size();i++){
    			cars.get(i).setPsdo_cust_id(newItem.getId());
    			jdbcDao.insert(cars.get(i));
        	}
    	}
    	
        
        return newItem;
    }
    
    public void update(Customer customer, List<Car> cars) {
    	CarSO carSO = new CarSO();
    	carSO.setPsdo_cust_id(customer.getId());
    	jdbcDao.delete(Car.class, carSO);
    	if(cars != null){
	        jdbcDao.update(customer) ;
	        for(int i=0;i<cars.size();i++){
	        	cars.get(i).setPsdo_cust_id(customer.getId());
	    		jdbcDao.insert(cars.get(i));
	    	}
    	}
    }
    
    public void update(Customer customer) {
    	jdbcDao.update(customer) ;
    }
    

    public void delete(Customer customer) {
    	CarSO carSO = new CarSO();
    	carSO.setPsdo_cust_id(customer.getId());
    	jdbcDao.delete(Car.class, carSO);
    	
    	MemberSO memberSO = new MemberSO();
		memberSO.setPsdo_cust_id(customer.getId().toString());
		jdbcDao.delete(Member.class, memberSO);
    	
        jdbcDao.delete(customer) ;
        
    }

    public void deleteAll(Long[] customerIds) {
    	for(int i=0; i<customerIds.length; i++){
    		CarSO carSO = new CarSO();
        	carSO.setPsdo_cust_id(customerIds[i]);
        	jdbcDao.delete(Car.class, carSO);
        	
        	MemberSO memberSO = new MemberSO();
    		memberSO.setPsdo_cust_id(customerIds[i].toString());
    		jdbcDao.delete(Member.class, memberSO);
    	}
    	
    	
        CustomerSO customer = new CustomerSO() ;
        customer.setIds(customerIds) ;
        jdbcDao.delete(Customer.class, customer) ;
        
    }
    
    
    public ArrayPageList<Customer> query(CustomerSO customerSO) {

        if (customerSO == null) {
        	customerSO = new CustomerSO() ;
        }
        //customerSO.addDesc("customer_timestamp") ;
        
        return (ArrayPageList<Customer>)jdbcDao.query(customerSO, Customer.class);
    }



    public Customer get(Long id) {  
    	Customer customer = new Customer() ;
    	customer.setId(id) ;
    	customer = (Customer) jdbcDao.get(customer) ;
        
        
        return customer;
    }
    

    public static CustomerBO getCustomerBO() {
    	return (CustomerBO)CodeHelper.getAppContext().getBean("customerBO");
    }
}
